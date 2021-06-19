package com.llat.algorithms.propositional;

import com.llat.algorithms.ArgumentTruthTreeValidator;
import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.algorithms.predicate.PredicateNaturalDeductionValidator;
import com.llat.models.treenode.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public final class PropositionalNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    private static final int TIMEOUT = 1000;

    public PropositionalNaturalDeductionValidator(ArrayList<WffTree> _wffTreeList) {
        super(_wffTreeList);
    }

    /**
     * Computes a natural deduction proof for a propositional logic formula. We use a couple of heuristics to ensure
     * the runtime/search space isn't COMPLETELY insane (those being that we only apply certain rules if others fail
     * to produce meaningful results, etc.).
     *
     * @return list of NDWffTree "args". These serve as the premises, with the last element in the list being
     * the conclusion.
     */
    @Override
    public ArrayList<NDWffTree> getNaturalDeductionProof() {
        ArgumentTruthTreeValidator truthTreeValidator = new ArgumentTruthTreeValidator(this.ORIGINAL_WFFTREE_LIST);
        if (!truthTreeValidator.isValid()) { return null; }

        int cycles = 0;
        while (!this.findConclusion() && !this.findContradictions()
                && cycles++ <= PropositionalNaturalDeductionValidator.TIMEOUT) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree premise = this.PREMISES_LIST.get(i);
                if (this.satisfy(premise.getWffTree(), premise)) {
                    premise.setFlags(NDFlag.SAT);
                }
            }
            this.satisfy(this.CONCLUSION_WFF.getWffTree(), this.CONCLUSION_WFF);
        }

        // The timeout is there to prevent completely insane proofs from never ending.
        if (cycles > PropositionalNaturalDeductionValidator.TIMEOUT) {
            return null;
        }

        // Backtrack from the conclusion to mark all those nodes that were used in the proof.
        this.activateLinks(this.CONCLUSION_WFF);

        // Add the premises that were actually used in the argument.
        return this.assignParentIndices();
    }


    /**
     * Determines if we can "satisfy" a premise Wff W. W is satisfied when it is used in
     * the reduction or expansion of another Wff W'. In other words, if W is used to construct
     * W', W is satisfied. The idea is to recursively compute "goals" for a premise, then
     * by determining if those goals are satisfied, we can construct the overall goal.
     * <p>
     * For example, suppose the goal is (A & B). The subgoals are then A and B. If A and B are
     * premises, these goals are satisfied by default. Thus, the overarching goal is satisfied.
     * Different rules apply for different operators.
     * <p>
     * We pass both the parent NDWffTree as well as the WffTree since we cannot break down the
     * parent NDwffTree's WffTree - we can only recursively pass the children of _tree.
     *
     * @param _tree   - WffTree object to recursively check for satisfaction.
     * @param _parent - NDWffTree "parent" of _tree; any children of _tree will be temporary "children" of _parent.
     * @return
     */
    private boolean satisfy(WffTree _tree, NDWffTree _parent) {
        boolean satisfied = false;
        if (this.findDeMorganEquivalence(_tree, _parent)) {
            return true;
        } else if (this.findMaterialImplication(_tree, _parent)) {
            return true;
        } else if (_tree.isImp()) {
            satisfied = this.satisfyImplication(_tree, _parent);
        } else if (_tree.isAnd()) {
            satisfied = this.satisfyConjunction(_tree, _parent);
        } else if (_tree.isOr()) {
            satisfied = this.satisfyDisjunction(_tree, _parent);
        } else if (_tree.isBicond()) {
            satisfied = this.satisfyBiconditional(_tree, _parent);
        }

        // If we couldn't find anything to deduce/reduce the proposition with,
        // try to search for it in the premises list.
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return true;
            }
        }

        return satisfied;
    }

    /**
     * Satisfying an implication nodes comes through for primary methods:
     * <p>
     * 1. Modus Ponens (P -> Q), P therefore Q
     * 2. Modus Tollens (P -> Q), ~Q therefore ~P
     * 3. Hypothetical Syllogism (P->Q), (Q->R) therefore (P->R)
     * 4. Construction of (P -> Q) from P and Q.
     *
     * @param _impTree - implication node to check for satisfaction.
     * @param _parent  - parent NDWffTree.
     * @return true if we can satisfy the implication goal, false otherwise.
     */
    private boolean satisfyImplication(WffTree _impTree, NDWffTree _parent) {
        // If the parent is not the conclusion then we can attempt to do other rules on it.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isImp()) {
            boolean mt = this.findModusTollens(_impTree, _parent);
            boolean mp = this.findModusPonens(_impTree, _parent);
            boolean hs = this.findHypotheticalSyllogism(_impTree, _parent);
            if (mt || mp || hs) return true;
        }

        // Otherwise, try to construct an implication node - see if both sides are satisfiable.
        if (this.satisfy(_impTree.getChild(0), _parent) && this.satisfy(_impTree.getChild(1), _parent)) {
            ImpNode impNode = new ImpNode();
            impNode.addChild(_impTree.getChild(0));
            impNode.addChild(_impTree.getChild(1));
            this.addPremise(new NDWffTree(impNode, NDFlag.II, NDStep.II,
                    this.getPremiseNDWffTree(_impTree.getChild(0)),
                    this.getPremiseNDWffTree(_impTree.getChild(1))));
            return true;
        }

        // Finally, check to see if this wff is a premise somewhere.
        return false;
    }

    /**
     * Creates or eliminates a conjunction node. The creation comes through searching for
     * the two operands to see if they exist and if so, create the conjunction.
     *
     * Elimination is simply simplification - breaks the two operands down.
     *
     * @param _conjTree
     * @param _parent
     * @return true if we can satisfy the conjunction goal, false otherwise.
     */
    private boolean satisfyConjunction(WffTree _conjTree, NDWffTree _parent) {
        // First try to simplify if the root is a conjunction.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isAnd()) {
            boolean simp = this.findSimplification(_conjTree, _parent);
            if (simp && _conjTree.stringEquals(_parent.getWffTree())) return true;
        }

        // Then try to create a conjunction if it's a goal and satisfied on both sides.
        if (this.satisfy(_conjTree.getChild(0), _parent) && this.satisfy(_conjTree.getChild(1), _parent)) {
            AndNode andNode = new AndNode();
            andNode.addChild(_conjTree.getChild(0));
            andNode.addChild(_conjTree.getChild(1));
            this.addPremise(new NDWffTree(andNode, NDFlag.AI, NDStep.AI,
                    this.getPremiseNDWffTree(_conjTree.getChild(0)),
                    this.getPremiseNDWffTree(_conjTree.getChild(1))));
            return true;
        }
        return false;
    }

    /**
     * @param _disjTree
     * @param _parent
     * @return true if we can satisfy the disjunction goal, false otherwise.
     */
    private boolean satisfyDisjunction(WffTree _disjTree, NDWffTree _parent) {
        // First try to perform DS if the root is a disjunction.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isOr()) {
            boolean ds = this.findDisjunctiveSyllogism(_disjTree, _parent);
            if (ds && _disjTree.stringEquals(_parent.getWffTree())) return true;
        }

        // Then try to create a conjunction if it's a goal and one of the two children are satisfied.
        if (this.satisfy(_disjTree.getChild(0), _parent) || this.satisfy(_disjTree.getChild(1), _parent)) {
            // There's two conditions: we're either adding from the conclusion or from
            // another premise. If the parent is the conclusion, then we're adding from
            // that (obviously) and one of the nodes won't be retrievable via getPremise....
            OrNode orNode = new OrNode();
            orNode.addChild(_disjTree.getChild(0));
            orNode.addChild(_disjTree.getChild(1));

            // Find out which operand is null (if any).
            NDWffTree lhsDisj = this.getPremiseNDWffTree(_disjTree.getChild(0));
            NDWffTree rhsDisj = this.getPremiseNDWffTree(_disjTree.getChild(1));
            if (this.isConclusion(_parent)) {
                if (lhsDisj == null) {
                    lhsDisj = new NDWffTree(_disjTree.getChild(0), NDStep.OI);
                } else {
                    rhsDisj = new NDWffTree(_disjTree.getChild(1), NDStep.OI);
                }
            }
            this.addPremise(new NDWffTree(orNode, NDFlag.OI, NDStep.OI, lhsDisj, rhsDisj));
            return true;
        }
        return false;
    }

    /**
     * @param _bicondTree
     * @param _parent
     * @return true if we can satisfy the biconditional goal, false otherwise.
     */
    private boolean satisfyBiconditional(WffTree _bicondTree, NDWffTree _parent) {
        // First check to see if we can break any biconditionals down.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isBicond()) {
            boolean bce = this.findBiconditionalElimination(_bicondTree, _parent);
            if (bce && _bicondTree.stringEquals(_parent.getWffTree())) return true;
        }
        // We first have a subgoal of X -> Y and Y -> X.
        ImpNode impLhs = new ImpNode();
        ImpNode impRhs = new ImpNode();
        impLhs.addChild(_bicondTree.getChild(0));
        impLhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(0));

        // Check to see if both implications are satisfied.
        if (this.satisfy(impLhs, _parent) && this.satisfy(impRhs, _parent)) {
            BicondNode bicondNode = new BicondNode();
            bicondNode.addChild(_bicondTree.getChild(0));
            bicondNode.addChild(_bicondTree.getChild(1));
            this.addPremise(new NDWffTree(bicondNode, NDFlag.BC, NDStep.BCI,
                    this.getPremiseNDWffTree(impLhs),
                    this.getPremiseNDWffTree(impRhs)));
            return true;
        }
        return false;
    }
}
