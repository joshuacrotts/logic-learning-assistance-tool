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
    protected static final int APPEND_CHILD_TIMEOUT = 4;

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

    protected boolean satisfy(WffTree _tree, NDWffTree _parent) {
        System.out.println("Goal: " + _tree.getStringRep());
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return true;
            }
        }

        // If it's an implication node then we need to try to construct it if it's unsatisfied.
        if (_tree.isImp()) {
            boolean lhs = this.satisfy(_tree.getChild(0), _parent);
            boolean rhs = this.satisfy(_tree.getChild(1), _parent);
            if (lhs && rhs) {
                ImpNode impNode = new ImpNode();
                impNode.addChild(_tree.getChild(0));
                impNode.addChild(_tree.getChild(1));
                this.addPremise(new NDWffTree(impNode, NDFlag.II, NDStep.II,
                                                        this.getPremiseNDWffTree(_tree.getChild(0)),
                                                        this.getPremiseNDWffTree(_tree.getChild(1))));
                return true;
            }
            // If the parent is not the conclusion then we can attempt to do other rules on it.
            else if (!this.isConclusion(_parent)) {
                System.out.println("Obv here");
                boolean mp = this.findMP(_tree, _parent);
                boolean mt = this.findMT(_tree, _parent);
                return mp || mt;
            }
        }

        return false;
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
     * @param _parent
     * @return
     */
    protected boolean findMP(WffTree _tree, NDWffTree _parent) {
        if (!_parent.isMPActive()) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
                // Check to see if we have the antecedent satisfied.
                if (ndWffTree.getWffTree().stringEquals(_tree.getChild(0))) {
                    NDWffTree consequentNode = new NDWffTree(_tree.getChild(0), NDStep.MP, _parent, ndWffTree);
                    this.PREMISES_LIST.add(consequentNode);
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean findMT(WffTree _tree, NDWffTree _parent) {
        if (!_parent.isMTActive()) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
                // Check to see if we have the negated consequent satisfied.
                if (_tree.getChild(1).stringEquals(BaseTruthTreeGenerator.getFlippedNode(ndWffTree.getWffTree()))) {
                    WffTree flippedWff = BaseTruthTreeGenerator.getFlippedNode(ndWffTree.getWffTree());
                    NDWffTree flippedNode = new NDWffTree(flippedWff, NDStep.MT, _parent, ndWffTree);
                    this.PREMISES_LIST.add(flippedNode);
                    return true;
                }
            }
        }

        return false;
    }

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

    protected NDWffTree getPremiseNDWffTree(WffTree _tree) {
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return ndWffTree;
            }
        }
        return null;
    }

    protected boolean isPremise(NDWffTree _ndWffTree) {
        return this.PREMISES_LIST.contains(_ndWffTree);
    }

    protected boolean isConclusion(NDWffTree _ndWffTree) {
        return this.CONCLUSION_WFF.getWffTree().stringEquals(_ndWffTree.getWffTree())
                || this.CONCLUSION_WFF == _ndWffTree;
    }
}
//A,B=>(A -> B)