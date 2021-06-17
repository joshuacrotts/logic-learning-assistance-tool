package com.llat.algorithms;

import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public abstract class BaseNaturalDeductionValidator {

    /**
     *
     */
    protected final LinkedList<WffTree> ORIGINAL_WFFTREE_LIST;

    /**
     *
     */
    protected final ArrayList<NDWffTree> PREMISES_LIST;

    /**
     *
     */
    protected final NDWffTree CONCLUSION_WFF;

    public BaseNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        this.ORIGINAL_WFFTREE_LIST = _wffTreeList;
        this.PREMISES_LIST = new ArrayList<>();
        this.CONCLUSION_WFF = new NDWffTree(_wffTreeList.get(_wffTreeList.size() - 1).getChild(0), NDStep.C);

        // Add all premises to the list. The invariant is that the last element is guaranteed
        // to be the conclusion.
        for (int i = 0; i < _wffTreeList.size() - 1; i++) {
            // Trim ROOT off the node if it's still there from ANTLR processing.
            WffTree wff = _wffTreeList.get(i).getNodeType() == NodeType.ROOT ? _wffTreeList.get(i).getChild(0) : _wffTreeList.get(i);
            this.addPremise(new NDWffTree(wff, NDFlag.ACTIVE, NDStep.P));
        }
    }

    /**
     * Computes a natural deduction proof for a logic formula. The details should be listed in the
     * subclasses for FOPL and PL respectively.
     *
     * @return list of NDWffTree "args". These serve as the premises, with the last element in the list being
     * the conclusion.
     */
    public abstract LinkedList<NDWffTree> getNaturalDeductionProof();

    /**
     *
     * @param _tree
     * @param _parent
     * @return
     */
    protected boolean satisfy(WffTree _tree, NDWffTree _parent) {
        //System.out.println("Goal: " + _tree.getStringRep());
        if (_tree.isImp()) { return this.satisfyImplication(_tree, _parent); }
        else if (_tree.isAnd()) { return this.satisfyConjunction(_tree, _parent); }
        else if (_tree.isOr()) { return this.satisfyDisjunction(_tree, _parent); }
        else if (_tree.isBicond()) { return this.satisfyBiconditional(_tree, _parent); }
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return true; }
        }

        return false;
    }

    /**
     *
     * @param _impNode
     * @param _parent
     * @return
     */
    protected boolean satisfyImplication(WffTree _impNode, NDWffTree _parent) {
        // If the parent is not the conclusion then we can attempt to do other rules on it.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isImp()) {
            boolean mp = this.findModusPonens(_impNode, _parent);
            boolean mt = this.findModusTollens(_impNode, _parent);
            boolean hs = this.findHypotheticalSyllogism(_impNode, _parent);
            if (mp || mt || hs) return true;
        }

        // Otherwise, try to construct an implication node.
        boolean lhs = this.satisfy(_impNode.getChild(0), _parent);
        boolean rhs = this.satisfy(_impNode.getChild(1), _parent);
        if (lhs && rhs) {
            ImpNode impNode = new ImpNode();
            impNode.addChild(_impNode.getChild(0));
            impNode.addChild(_impNode.getChild(1));
            this.addPremise(new NDWffTree(impNode, NDFlag.II, NDStep.II,
                    this.getPremiseNDWffTree(_impNode.getChild(0)),
                    this.getPremiseNDWffTree(_impNode.getChild(1))));
            return true;
        }

        // Finally, check to see if this wff is a premise somewhere.

        return this.isPremise(_impNode);
    }

    /**
     *
     * @param _conjTree
     * @param _parent
     * @return
     */
    protected boolean satisfyConjunction(WffTree _conjTree, NDWffTree _parent) {
        // First try to simplify if the root is a conjunction.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isAnd()) {
            boolean simp = this.findSimplification(_conjTree, _parent);
            if (simp) return true;
        }

        // Then try to create a conjunction if it's a goal.
        boolean lhs = this.satisfy(_conjTree.getChild(0), _parent);
        boolean rhs = this.satisfy(_conjTree.getChild(1), _parent);
        if (lhs && rhs) {
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
     *
     * @param _disjTree
     * @param _parent
     * @return
     */
    protected boolean satisfyDisjunction(WffTree _disjTree, NDWffTree _parent) {
        // First try to perform DS if the root is a disjunction.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isOr()) {
            boolean ds = this.findDisjunctiveSyllogism(_disjTree, _parent);
            if (ds) return true;
        }
        // Then try to create a conjunction if it's a goal.
        boolean lhs = this.satisfy(_disjTree.getChild(0), _parent);
        boolean rhs = this.satisfy(_disjTree.getChild(1), _parent);
        if (lhs || rhs) {
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
     *
     * @param _bicondTree
     * @param _parent
     * @return
     */
    protected boolean satisfyBiconditional(WffTree _bicondTree, NDWffTree _parent) {
        // First check to see if we can break any biconditionals down.
        if (!this.isConclusion(_parent) && _parent.getWffTree().isBicond()) {
            boolean bc = this.findBiconditionalElimination(_bicondTree, _parent);
            if (bc) return true;
        }

        // We first have a subgoal of X -> Y and Y -> X.
        ImpNode impLhs = new ImpNode();
        ImpNode impRhs = new ImpNode();
        impLhs.addChild(_bicondTree.getChild(0));
        impLhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(1));
        impRhs.addChild(_bicondTree.getChild(0));
        boolean lhs = this.satisfy(impLhs, _parent);
        boolean rhs = this.satisfy(impRhs, _parent);
        if (lhs && rhs) {
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

    /**
     *
     * @param _mpTree
     * @param _parent
     * @return
     */
    protected boolean findModusPonens(WffTree _mpTree, NDWffTree _parent) {
        if (!_parent.isMPActive()) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
                // Check to see if we have the antecedent satisfied.
                if (ndWffTree.getWffTree().stringEquals(_mpTree.getChild(0))) {
                    NDWffTree consequentNode = new NDWffTree(_mpTree.getChild(1), NDStep.MP, _parent, ndWffTree);
                    this.addPremise(consequentNode);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param _mtTree
     * @param _parent
     * @return
     */
    protected boolean findModusTollens(WffTree _mtTree, NDWffTree _parent) {
        if (!_parent.isMTActive()) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
                // Check to see if we have the negated consequent satisfied.
                if (_mtTree.getChild(1).stringEquals(BaseTruthTreeGenerator.getFlippedNode(ndWffTree.getWffTree()))) {
                    WffTree flippedWff = BaseTruthTreeGenerator.getFlippedNode(_mtTree.getChild(0));
                    NDWffTree flippedNode = new NDWffTree(flippedWff, NDStep.MT, _parent, ndWffTree);
                    this.addPremise(flippedNode);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param _andTree
     * @param _parent
     * @return
     */
    protected boolean findSimplification(WffTree _andTree, NDWffTree _parent) {
        if (!_parent.isAndEActive()) {
            _parent.setFlags(NDFlag.AE);
            NDWffTree andLhs = new NDWffTree(_andTree.getChild(0), NDStep.AE, _parent);
            NDWffTree andRhs = new NDWffTree(_andTree.getChild(1), NDStep.AE, _parent);
            this.addPremise(andLhs);
            this.addPremise(andRhs);
            return true;
        }
        return false;
    }

    /**
     *
     * @param _disjTree
     * @param _parent
     * @return
     */
    protected boolean findDisjunctiveSyllogism(WffTree _disjTree, NDWffTree _parent) {
        if (!_parent.isDSActive()) {
            WffTree flippedLhs = BaseTruthTreeGenerator.getFlippedNode(_disjTree.getChild(0));
            WffTree flippedRhs = BaseTruthTreeGenerator.getFlippedNode(_disjTree.getChild(1));
            boolean lhs = this.satisfy(flippedLhs, _parent);
            boolean rhs = this.satisfy(flippedRhs, _parent);

            // If we do not satisfy one of them but do satisfy the other, then we can perform DS.
            if (Boolean.logicalXor(lhs, rhs)) {
                if (lhs) {
                    NDWffTree w1 = new NDWffTree(_disjTree.getChild(1), NDStep.DS, _parent, this.getPremiseNDWffTree(flippedLhs));
                    this.addPremise(w1);
                }
                else {
                    NDWffTree w2 =new NDWffTree(_disjTree.getChild(0), NDStep.DS, _parent, this.getPremiseNDWffTree(flippedRhs));
                    this.addPremise(w2); }

                _parent.setFlags(NDFlag.DS);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _impNode
     * @param _parent
     * @return
     */
    protected boolean findHypotheticalSyllogism(WffTree _impNode, NDWffTree _parent) {
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree othNdWffTree = this.PREMISES_LIST.get(i);
            WffTree othImp = othNdWffTree.getWffTree();
            if (_parent != othNdWffTree && othImp.isImp()
                    && !_parent.isHSActive() && !othNdWffTree.isHSActive()) {
                // X == Y && Y == Z OR Y == Z && X == Y check to see if the antecedent of one
                // is equal to the consequent of the other.
                ImpNode impNode = null;
                if (_impNode.getChild(1).stringEquals(othImp.getChild(0))) {
                    impNode = new ImpNode();
                    impNode.addChild(_impNode.getChild(0));
                    impNode.addChild(othImp.getChild(1));
                } else if (othImp.getChild(1).stringEquals(_impNode.getChild(0))) {
                    impNode = new ImpNode();
                    impNode.addChild(othImp.getChild(0));
                    impNode.addChild(_impNode.getChild(1));
                }

                // If we found one, then we add it.
                if (impNode != null) {
                    _parent.setFlags(NDFlag.HS);
                    othNdWffTree.setFlags(NDFlag.HS);
                    this.addPremise(new NDWffTree(impNode, NDStep.HS, _parent, othNdWffTree));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param _bicondTree
     * @param _parent
     * @return
     */
    protected boolean findBiconditionalElimination(WffTree _bicondTree, NDWffTree _parent) {
        if (!_parent.isBCActive()) {
            ImpNode impLhs = new ImpNode();
            ImpNode impRhs = new ImpNode();
            impLhs.addChild(_bicondTree.getChild(0));
            impLhs.addChild(_bicondTree.getChild(1));
            impRhs.addChild(_bicondTree.getChild(1));
            impRhs.addChild(_bicondTree.getChild(0));
            this.addPremise(new NDWffTree(impLhs, NDStep.BCE, _parent));
            this.addPremise(new NDWffTree(impRhs, NDStep.BCE, _parent));
            _parent.setFlags(NDFlag.BC);
            return true;
        }

        return false;
    }

    /**
     *
     * @param _conclusionNode
     */
    protected void activateLinks(NDWffTree _conclusionNode) {
        if (_conclusionNode == null) { return; }
        _conclusionNode.setActive(true);
        for (NDWffTree ndWffTree : _conclusionNode.getDerivedParents()) {
            this.activateLinks(ndWffTree);
        }
    }

    /**
     * Attempts to add a premise (NDWffTree) to our running list of premises. A premise is NOT added if there is already
     * an identical premise in the tree OR it's a "redundant node". The definition for that is below.
     *
     * @param _ndWffTree NDWffTree to insert as a premise.
     */
    protected void addPremise(NDWffTree _ndWffTree) {
        // THIS NEEDS TO BE ADAPTED TO WORK WITH CONTRADICTIONS SINCE THOSE WILL FAIL!!!!!!!
        if (!this.PREMISES_LIST.contains(_ndWffTree) && !this.isRedundantTree(_ndWffTree)) {
            this.PREMISES_LIST.add(_ndWffTree);
        }
    }

    /**
     * A tree is "redundant" if it implies a tautology. For instance, (A & A), (A V A), (A -> A), (A <-> A), etc.
     *
     * @param _ndWffTree NDWffTree to check.
     *
     * @return true if the node is redundant, false otherwise.
     */
    protected boolean isRedundantTree(NDWffTree _ndWffTree) {
        return _ndWffTree.getWffTree().isBinaryOp() && _ndWffTree.getWffTree().getChild(0).stringEquals(_ndWffTree.getWffTree().getChild(1));
    }

    /**
     *
     * @param _tree
     * @return
     */
    protected NDWffTree getPremiseNDWffTree(WffTree _tree) {
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return ndWffTree;
            }
        }
        return null;
    }

    /**
     * Looks through our list of premises to determine if we have found the conclusion yet. We also assign the
     * derived parents of that node and the derivation step to the conclusion wff object (since they aren't the same
     * reference). This is used in the activateLinks method.
     *
     * @return true if the premise list has the conclusion, false otherwise.
     */
    protected boolean findConclusion() {
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree())) {
                this.CONCLUSION_WFF.setActive(true);
                this.CONCLUSION_WFF.setDerivedParents(ndWffTree.getDerivedParents());
                this.CONCLUSION_WFF.setDerivationStep(ndWffTree.getDerivationStep());
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _tree
     * @return
     */
    protected boolean isPremise(WffTree _tree) {
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param _ndWffTree
     * @return
     */
    protected boolean isConclusion(NDWffTree _ndWffTree) {
        return this.CONCLUSION_WFF.getWffTree().stringEquals(_ndWffTree.getWffTree())
                || this.CONCLUSION_WFF == _ndWffTree;
    }
}
