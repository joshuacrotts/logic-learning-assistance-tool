package com.llat.algorithms.predicate;

import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.symbols.Universal;
import com.llat.models.treenode.*;

import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 */
public final class PredicateNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    private static final int TIMEOUT = 1000;

    /**
     *
     */
    private final HashSet<Character> CONSTANTS;

    /**
     *
     */
    private final HashSet<Character> CONCLUSION_CONSTANTS;

    public PredicateNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        super(_wffTreeList);
        // Get all constants and conclusion constants...
        this.CONSTANTS = new HashSet<>();
        this.CONCLUSION_CONSTANTS = new HashSet<>();
        for (int i = 0; i < _wffTreeList.size() - 1; i++)
            this.addAllConstantsToSet(_wffTreeList.get(i), this.CONSTANTS);
        this.addAllConstantsToSet(_wffTreeList.getLast(), this.CONCLUSION_CONSTANTS);
    }

    /**
     * Computes a natural deduction proof for a predicate logic formula. We use a couple of heuristics to ensure
     * the runtime/search space isn't COMPLETELY insane (those being that we only apply certain rules if others fail
     * to produce meaningful results, etc.).
     *
     * @return list of NDWffTree "args". These serve as the premises, with the last element in the list being
     * the conclusion.
     */
    @Override
    public LinkedList<NDWffTree> getNaturalDeductionProof() {
        int cycles = 0;
        while (!this.findConclusion() && !this.findContradictions()
                && cycles++ <= PredicateNaturalDeductionValidator.TIMEOUT) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree premise = this.PREMISES_LIST.get(i);
                this.satisfy(premise.getWffTree(), premise);
            }

            this.satisfy(this.CONCLUSION_WFF.getWffTree(), this.CONCLUSION_WFF);
        }

        // The timeout is there to prevent completely insane proofs from never ending.
        if (cycles > TIMEOUT) {
            return null;
        }

        // Backtrack from the conclusion to mark all those nodes that were used in the proof.
        this.activateLinks(this.CONCLUSION_WFF);

        // Add the premises that were actually used in the argument.
        LinkedList<NDWffTree> args = new LinkedList<>();
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.isActive()) {
                args.add(ndWffTree);
            }
        }

        // Finally, add the conclusion.
        args.add(this.CONCLUSION_WFF);
        return args;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfy(WffTree _tree, NDWffTree _parent) {
        NDWffTree satisfiedNDWffTree = null;
        if (_tree.isPredicate() || _tree.isNegPredicate()) {
            satisfiedNDWffTree = this.satisfyPredicate(_tree, _parent);
        } else if (_tree.isImp()) {
            satisfiedNDWffTree = this.satisfyImplication(_tree, _parent);
        } else if (_tree.isAnd()) {
            satisfiedNDWffTree = this.satisfyConjunction(_tree, _parent);
        } else if (_tree.isOr()) {
            satisfiedNDWffTree = this.satisfyDisjunction(_tree, _parent);
        } else if (_tree.isBicond()) {
            satisfiedNDWffTree = this.satisfyBiconditional(_tree, _parent);
        } else if (_tree.isExistential()) {
            satisfiedNDWffTree = this.satisfyExistential(_tree, _parent);
        } else if (_tree.isUniversal()) {
            satisfiedNDWffTree = this.satisfyUniversal(_tree, _parent);
        } else {
            satisfiedNDWffTree = this.satisfyDeMorganFOPL(_tree, _parent);
        }

        // If we couldn't find anything to deduce/reduce the proposition with,
        // try to search for it in the premises list.
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return ndWffTree;
            }
        }

        return satisfiedNDWffTree;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyPredicate(WffTree _tree, NDWffTree _parent) {
        // First, check to see if the premise is a satisfied goal or not.
        if (this.isGoal(_tree)) {
            return this.getPremiseNDWffTree(_tree);
        }

        // Loop through each other premise.
        for (NDWffTree othNDWffTree : this.PREMISES_LIST) {
            if (_tree.getNodeType() == othNDWffTree.getWffTree().getNodeType()
                && _tree.getSymbol().equals(othNDWffTree.getWffTree().getSymbol())) {
                // If the two are negated, then we need to truncate it.
                WffTree w1 = _tree.isNegation() ? _tree.getChild(0) : _tree;
                WffTree w2 = othNDWffTree.getWffTree().isNegation() ? othNDWffTree.getWffTree().getChild(0)
                        : othNDWffTree.getWffTree();
                if (w1.getChildrenSize() == w2.getChildrenSize()) {
                    // Now check to see if the children are the same OR
                    // if the former is a matching variable while the latter is a constant.
                    boolean found = true;
                    for (int i = 0; i < w1.getChildrenSize() && found; i++) {
                        WffTree ch1 = w1.getChild(i);
                        WffTree ch2 = w2.getChild(i);
                        if (ch1.stringEquals(ch2) || (ch1.isVariable() && ch2.isConstant())) {
                            continue;
                        } else {
                            found = false;
                        }
                    }
                    if (found) {
                        return othNDWffTree;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param _impTree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyImplication(WffTree _impTree, NDWffTree _parent) {
        // If the parent is not the conclusion then we can attempt to do other rules on it.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isImp()) {
            boolean mt = this.findModusTollens(_impTree, _parent);
            boolean mp = this.findModusPonens(_impTree, _parent);
            boolean hs = this.findHypotheticalSyllogism(_impTree, _parent);
            if (mt || mp || hs) return null;
        }

        // Otherwise, try to construct an implication node - see if both sides are satisfiable.
        NDWffTree lhsNDWffTree = this.satisfy(_impTree.getChild(0), _parent);
        NDWffTree rhsNDWffTree = this.satisfy(_impTree.getChild(1), _parent);
        if (lhsNDWffTree != null && rhsNDWffTree != null) {
            ImpNode impNode = new ImpNode();
            impNode.addChild(_impTree.getChild(0));
            impNode.addChild(_impTree.getChild(1));
            NDWffTree impNDWffTree = new NDWffTree(impNode, NDFlag.II, NDStep.II,
                    this.getPremiseNDWffTree(_impTree.getChild(0)),
                    this.getPremiseNDWffTree(_impTree.getChild(1)));
            this.addPremise(impNDWffTree);
            return impNDWffTree;
        }

        // Finally, check to see if this wff is a premise somewhere.
        return null;
    }

    /**
     * @param _conjTree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyConjunction(WffTree _conjTree, NDWffTree _parent) {
        if (!_parent.isAndEActive() && !_parent.isAndIActive() && !this.isConclusion(_parent)) {
            boolean simp = this.findSimplification(_conjTree, _parent);
            if (simp && _conjTree.stringEquals(_parent.getWffTree())) return null;
        }

        NDWffTree lhs = this.satisfy(_conjTree.getChild(0), _parent);
        NDWffTree rhs = this.satisfy(_conjTree.getChild(1), _parent);
        if (lhs != null && rhs != null) {
            AndNode andNode = new AndNode();
            andNode.addChild(lhs.getWffTree());
            andNode.addChild(rhs.getWffTree());
            NDWffTree andNDWffTree = new NDWffTree(andNode, NDFlag.AI, NDStep.AI, lhs, rhs);
            this.addPremise(andNDWffTree);
            return andNDWffTree;
        }

        return null;
    }

    /**
     * @param _disjTree
     * @param _parent
     * @return true if we can satisfy the disjunction goal, false otherwise.
     */
    private NDWffTree satisfyDisjunction(WffTree _disjTree, NDWffTree _parent) {
        // First try to perform DS if the root is a disjunction.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isOr()) {
            boolean ds = this.findDisjunctiveSyllogism(_disjTree, _parent);
            if (ds && _disjTree.stringEquals(_parent.getWffTree())) return null;
        }

        // Then try to create a disjunction if it's a goal and one of the two children are satisfied.
        NDWffTree lhsNDWffTree = this.satisfy(_disjTree.getChild(0), _parent);
        NDWffTree rhsNDWffTree = this.satisfy(_disjTree.getChild(1), _parent);
        if (lhsNDWffTree != null || rhsNDWffTree != null) {
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

            NDWffTree orNDWffTree = new NDWffTree(orNode, NDFlag.OI, NDStep.OI, lhsDisj, rhsDisj);
            this.addPremise(orNDWffTree);
            return orNDWffTree;
        }
        return null;
    }

    /**
     * @param _bicondTree
     * @param _parent
     * @return true if we can satisfy the biconditional goal, false otherwise.
     */
    private NDWffTree satisfyBiconditional(WffTree _bicondTree, NDWffTree _parent) {
        // First check to see if we can break any biconditionals down.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isBicond()) {
            boolean bce = this.findBiconditionalElimination(_bicondTree, _parent);
            if (bce && _bicondTree.stringEquals(_parent.getWffTree())) return null;
        }
        // We first have a subgoal of X -> Y and Y -> X.
        ImpNode impLhs = new ImpNode();
        ImpNode impRhs = new ImpNode();
        impLhs.addChild(_bicondTree.getChild(0));
        impLhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(0));

        // Check to see if both implications are satisfied.
        NDWffTree lhsNDWffTree = this.satisfy(impLhs, _parent);
        NDWffTree rhsNDWffTree = this.satisfy(impRhs, _parent);
        if (lhsNDWffTree != null && rhsNDWffTree != null) {
            BicondNode bicondNode = new BicondNode();
            bicondNode.addChild(_bicondTree.getChild(0));
            bicondNode.addChild(_bicondTree.getChild(1));
            NDWffTree bicondNDWffTree = new NDWffTree(bicondNode, NDFlag.BC, NDStep.BCI,
                    this.getPremiseNDWffTree(impLhs),
                    this.getPremiseNDWffTree(impRhs));
            this.addPremise(bicondNDWffTree);
            return bicondNDWffTree;
        }
        return null;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyExistential(WffTree _tree, NDWffTree _parent) {
        // Try to eliminate the existential if it's not a conclusion.
        if (!_parent.isExisActive() && !this.isConclusion(_parent)) {
            this.addExistentialConstant(_parent, ((ExistentialQuantifierNode) _parent.getWffTree()).getVariableSymbolChar());
            return null;
        }

        // If we find a satisfiable NDWffTree, then we can add it.
        NDWffTree child = this.satisfy(_tree.getChild(0), _parent);
        if (child != null) {
            String v = ((ExistentialQuantifierNode) _tree).getVariableSymbol();
            ExistentialQuantifierNode existentialQuantifierNode = new ExistentialQuantifierNode(v);
            existentialQuantifierNode.addChild(_tree.getChild(0));
            NDWffTree existentialNDWffTree = new NDWffTree(existentialQuantifierNode, NDFlag.EX, NDStep.EI, child);
            this.addPremise(existentialNDWffTree);
            return existentialNDWffTree;
        }

        return null;
    }

    /**
     *
     * @param _univTree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyUniversal(WffTree _univTree, NDWffTree _parent) {
        // Try to eliminate the universal if it's not a conclusion.
        if (!_parent.isUnivActive() && !this.isConclusion(_parent)) {
            this.addUniversalConstants(_parent, ((UniversalQuantifierNode) _parent.getWffTree()).getVariableSymbolChar());
            return null;
        }

        // If we find a satisfiable NDWffTree, then we can add it.
        NDWffTree child = this.satisfy(_univTree.getChild(0), _parent);
        if (child != null) {
            String v = ((UniversalQuantifierNode) _univTree).getVariableSymbol();
            UniversalQuantifierNode universalQuantifierNode = new UniversalQuantifierNode(v);
            universalQuantifierNode.addChild(_univTree.getChild(0));
            NDWffTree universalNDWffTree = new NDWffTree(universalQuantifierNode, NDFlag.UN, NDStep.UI, child);
            this.addPremise(universalNDWffTree);
            return universalNDWffTree;
        }

        return null;
    }

    /**
     *
     * @param _binopTree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyDeMorganFOPL(WffTree _binopTree, NDWffTree _parent) {
        if (!_parent.isDEMActive() && !this.isConclusion(_parent)) {
            // Four types: one is ~(X B Y) => (~X ~B ~Y)
            WffTree deMorganNode = null;
            if (_binopTree.isNegation() && (_binopTree.getChild(0).isOr() || _binopTree.getChild(0).isAnd() || _binopTree.getChild(0).isImp())) {
                deMorganNode = BaseTruthTreeGenerator.getNegatedBinaryNode(_binopTree.getChild(0)); // B
                deMorganNode.addChild(_binopTree.getChild(0).isImp() ? _binopTree.getChild(0).getChild(0)
                        : BaseTruthTreeGenerator.getFlippedNode(_binopTree.getChild(0).getChild(0))); // LHS X
                deMorganNode.addChild(BaseTruthTreeGenerator.getFlippedNode(_binopTree.getChild(0).getChild(1))); // RHS Y
            }
            // Other is (X B Y) => ~(~X ~B ~Y)
            else if ((_binopTree.isOr() || _binopTree.isAnd() || _binopTree.isImp())) {
                WffTree negBinaryNode = BaseTruthTreeGenerator.getNegatedBinaryNode(_binopTree); // B
                negBinaryNode.addChild(BaseTruthTreeGenerator.getFlippedNode(_binopTree.getChild(0))); // LHS X
                negBinaryNode.addChild(BaseTruthTreeGenerator.getFlippedNode(_binopTree.getChild(1))); // RHS Y
                deMorganNode = new NegNode();
                deMorganNode.addChild(negBinaryNode);
            }
            // Other turns ~(x)W to (Ex)~W
            else if (_binopTree.isNegation() && _binopTree.getChild(0).isUniversal()) {
                String v = ((UniversalQuantifierNode) _binopTree.getChild(0)).getVariableSymbol();
                ExistentialQuantifierNode existentialQuantifierNode = new ExistentialQuantifierNode(v);
                NegNode neg = new NegNode();
                neg.addChild(_binopTree.getChild(0).getChild(0));
                existentialQuantifierNode.addChild(neg);
                deMorganNode = existentialQuantifierNode;
            }
            // Last turns ~(Ex)W to (x)~W
            else if (_binopTree.isNegation() && _binopTree.getChild(0).isExistential()) {
                String v = ((ExistentialQuantifierNode) _binopTree.getChild(0)).getVariableSymbol();
                UniversalQuantifierNode universalQuantifierNode = new UniversalQuantifierNode(v);
                NegNode neg = new NegNode();
                neg.addChild(_binopTree.getChild(0).getChild(0));
                universalQuantifierNode.addChild(neg);
                deMorganNode = universalQuantifierNode;
            }
            // If we found a node, then it'll be applied/inserted here.
            if (deMorganNode != null) {
                _parent.setFlags(NDFlag.DEM);
                NDWffTree deMorganNDWffTree = new NDWffTree(deMorganNode, NDFlag.DEM, NDStep.DEM, _parent);
                this.addPremise(deMorganNDWffTree);
                return deMorganNDWffTree;
            }
        }
        return null;
    }

    /**
     * @param _existentialNDWffTree
     * @param _variableToReplace
     */
    private void addExistentialConstant(NDWffTree _existentialNDWffTree, char _variableToReplace) {
        // Find the next available constant to use.
        char constant = 'a';
        while (this.CONSTANTS.contains(constant) || this.CONCLUSION_CONSTANTS.contains(constant)) {
            // This could wrap around...
            constant++;
        }

        // Replace all variables found with the constant.
        WffTree _newRoot = _existentialNDWffTree.getWffTree().getChild(0).copy();
        this.replaceSymbol(_newRoot, _variableToReplace, constant, ReplaceType.CONSTANT);
        this.addPremise(new NDWffTree(_newRoot, NDFlag.EX, NDStep.EE, _existentialNDWffTree));
        this.CONSTANTS.add(constant);
    }

    /**
     * @param _universalNDWffTree
     * @param _variableToReplace
     */
    private void addUniversalConstants(NDWffTree _universalNDWffTree, char _variableToReplace) {
        // Add a default constant if one is not available to the universal quantifier.
        if (this.CONSTANTS.isEmpty()) {
            return;
        }

        for (char c : this.CONSTANTS) {
            // Create a copy and replace the selected variable.
            WffTree _newRoot = _universalNDWffTree.getWffTree().getChild(0).copy();
            this.replaceSymbol(_newRoot, _variableToReplace, c, ReplaceType.CONSTANT);
            this.addPremise(new NDWffTree(_newRoot, NDStep.UE, _universalNDWffTree));
        }
    }

    /**
     * Replaces a variable or a constant with a constant node in a WffTree. This is used when performing
     * existential, universal decomposition, or identity decomposition.
     *
     * @param _newRoot         - root of WffTree to modify.
     * @param _symbolToReplace - constant or variable that we want to replace e.g. (x) = x
     * @param _symbol          - symbol to replace _symbolToReplace with.
     * @param _type            - type of node to insert to the tree. This should either be ReplaceType.CONSTANT or ReplaceType.VARIABLE.
     */
    private void replaceSymbol(WffTree _newRoot, char _symbolToReplace, char _symbol, ReplaceType _type) {
        for (int i = 0; i < _newRoot.getChildrenSize(); i++) {
            if (_newRoot.getChild(i).isVariable() || _newRoot.getChild(0).isConstant()) {
                char s = _newRoot.getChild(i).getSymbol().charAt(0);
                if (s == _symbolToReplace) {
                    if (_type == ReplaceType.CONSTANT) {
                        _newRoot.setChild(i, new ConstantNode("" + _symbol));
                    } else if (_type == ReplaceType.VARIABLE) {
                        _newRoot.setChild(i, new VariableNode("" + _symbol));
                    }
                }
            }
            this.replaceSymbol(_newRoot.getChild(i), _symbolToReplace, _symbol, _type);
        }
    }

    /**
     * Recursively adds all constants found in a WffTree to a HashSet. The constants
     * should be listed as a ConstantNode.
     *
     * @param _tree    - WffTree to recursively check.
     * @param _charSet - HashSet of characters to add the discovered constants to.
     */
    private void addAllConstantsToSet(WffTree _tree, HashSet<Character> _charSet) {
        if (_tree != null && _tree.isConstant()) {
            _charSet.add(_tree.getSymbol().charAt(0));
        }

        for (WffTree child : _tree.getChildren()) {
            this.addAllConstantsToSet(child, _charSet);
        }
    }

    /**
     * ReplaceType enum for the replaceSymbols method. More details are found there.
     */
    private enum ReplaceType {
        VARIABLE,
        CONSTANT
    }
}