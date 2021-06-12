package com.llat.algorithms;

import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.LinkedList;

/**
 *
 */
public final class PropositionalNaturalDeductionValidator {

    /**
     *
     */
    private static final int TIMEOUT = 100;

    /**
     *
     */
    private final LinkedList<WffTree> ORIGINAL_WFFTREE_LIST;

    /**
     *
     */
    private final LinkedList<NDWffTree> PREMISES_LIST;

    /**
     *
     */
    private final NDWffTree CONCLUSION_WFF;

    public PropositionalNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        this.ORIGINAL_WFFTREE_LIST = _wffTreeList;
        this.PREMISES_LIST = new LinkedList<>();
        this.CONCLUSION_WFF = new NDWffTree(_wffTreeList.getLast().getChild(0), NDStep.C);

        for (int i = 0; i < _wffTreeList.size() - 1; i++) {
            WffTree wff = _wffTreeList.get(i).getNodeType() == NodeType.ROOT ? _wffTreeList.get(i).getChild(0) : _wffTreeList.get(i);
            this.addPremise(new NDWffTree(wff, NDStep.P));
        }
    }

    /**
     * @return
     */
    public LinkedList<NDWffTree> getNaturalDeductionProof() {
        ArgumentTruthTreeValidator truthTreeValidator = new ArgumentTruthTreeValidator(this.ORIGINAL_WFFTREE_LIST);
        if (!truthTreeValidator.isValid()) {
            return null;
        }

        // We'll either find the conclusion or time out first.
        int currIteration = 0;
        for (currIteration = 0; currIteration <= PropositionalNaturalDeductionValidator.TIMEOUT
                && !this.findConclusion(); currIteration++) {
            boolean mod1 = false;
            // If any of these return true we won't do the extra steps.
            mod1 = mod1 || this.findSimplifications();
            mod1 = mod1 || this.findModusPonens();
            mod1 = mod1 || this.findModusTollens();
            mod1 = mod1 || this.findDisjunctiveSyllogisms();
            mod1 = mod1 || this.findHypotheticalSyllogisms();
            mod1 = mod1 || this.findDoubleNegations();
            mod1 = mod1 || this.findContradictions();
            if (!mod1) {
                mod1 = mod1 || this.findEquivalences();
            }
            if (!mod1) {
                mod1 = mod1 || this.appendDisjunctions();
            }
            if (!mod1) {
                mod1 = mod1 || this.appendConjunctions();
            }
        }

        // If we timed out, just return null.
        if (currIteration > PropositionalNaturalDeductionValidator.TIMEOUT) {
            return null;
        }

        // Deactivate steps that weren't used in the derivation.
        this.deactivateLinks();

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
     * @return
     */
    private boolean findEquivalences() {

        return false;
    }

    /**
     * @return
     */
    private boolean findSimplifications() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().isAnd() && !ndWffTree.isActive() && !ndWffTree.isAndIActive()) {
                // Break the conjunction down if it hasn't already been.
                changed = true;
                ndWffTree.setFlags(NDFlag.ACTIVE);
                WffTree lhs = ndWffTree.getWffTree().getChild(0);
                WffTree rhs = ndWffTree.getWffTree().getChild(1);
                this.addPremise(new NDWffTree(lhs, NDStep.AND_E, ndWffTree));
                this.addPremise(new NDWffTree(rhs, NDStep.AND_E, ndWffTree));
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean findModusPonens() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    // First check to see if the first wff is actually an implication.
                    if ((wffOne.getWffTree().isImp() || wffTwo.getWffTree().isImp())
                            && (!wffOne.isMPActive() || !wffTwo.isMPActive())) {
                        NDWffTree impNode = wffOne.getWffTree().isImp() ? wffOne : wffTwo;
                        NDWffTree othNode = wffOne.getWffTree().isImp() ? wffTwo : wffOne;
                        // Now check to see if the antecedents match.
                        if (impNode.getWffTree().getChild(0).stringEquals(othNode.getWffTree())) {
                            changed = true;
                            wffOne.setFlags(NDFlag.ACTIVE | NDFlag.MP);
                            wffTwo.setFlags(NDFlag.ACTIVE | NDFlag.MP);
                            WffTree consequent = impNode.getWffTree().getChild(1);
                            this.addPremise(new NDWffTree(consequent, NDStep.MP, impNode, othNode));
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean findModusTollens() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    // First check to see if the first wff is actually an implication.
                    if ((wffOne.getWffTree().isImp() || wffTwo.getWffTree().isImp())
                            && (!wffOne.isMTActive() || !wffTwo.isMTActive())) {
                        NDWffTree impNode = wffOne.getWffTree().isImp() ? wffOne : wffTwo;
                        NDWffTree othNode = wffOne.getWffTree().isImp() ? wffTwo : wffOne;
                        // Now check to see if the antecedents match.
                        if (impNode.getWffTree().getChild(1).stringEquals(BaseTruthTreeGenerator.getFlippedNode(othNode.getWffTree()))) {
                            changed = true;
                            wffOne.setFlags(NDFlag.ACTIVE | NDFlag.MT);
                            wffTwo.setFlags(NDFlag.ACTIVE | NDFlag.MT);
                            WffTree antecedent = BaseTruthTreeGenerator.getFlippedNode(impNode.getWffTree().getChild(0));
                            this.addPremise(new NDWffTree(antecedent, NDStep.MT, impNode, othNode));
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean findDisjunctiveSyllogisms() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);

                    if ((wffOne.getWffTree().isOr() || wffTwo.getWffTree().isOr())
                            && (!wffOne.isDSActive() || !wffTwo.isDSActive())) {
                        NDWffTree disjNode = wffOne.getWffTree().isOr() ? wffOne : wffTwo;
                        NDWffTree othNode = wffOne.getWffTree().isOr() ? wffTwo : wffOne;

                        // Now see if the othNode is the negation of either disj child.
                        // If so, append the OPPOSITE child.
                        WffTree negDisjNode = BaseTruthTreeGenerator.getFlippedNode(othNode.getWffTree());
                        WffTree dsNode = null;
                        if (negDisjNode.stringEquals(disjNode.getWffTree().getChild(0))) {
                            dsNode = disjNode.getWffTree().getChild(1);
                        } else if (negDisjNode.stringEquals(disjNode.getWffTree().getChild(1))) {
                            dsNode = disjNode.getWffTree().getChild(0);
                        }

                        if (dsNode != null) {
                            changed = true;
                            disjNode.setFlags(NDFlag.ACTIVE | NDFlag.DS);
                            othNode.setFlags(NDFlag.ACTIVE | NDFlag.DS);
                            this.addPremise(new NDWffTree(dsNode, NDStep.DS, disjNode, othNode));
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean findHypotheticalSyllogisms() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    // The formulas need to be implications and they can't have already done a HS step.
                    if ((wffOne.getWffTree().isImp() && wffTwo.getWffTree().isImp())
                            && (!wffOne.isHSActive() || !wffTwo.isHSActive())) {
                        // X == Y && Y == Z OR Y == Z && X == Y check to see if the antecedent of one
                        // is equal to the consequent of the other.
                        ImpNode impNode = null;
                        if (wffOne.getWffTree().getChild(1).stringEquals(wffTwo.getWffTree().getChild(0))) {
                            impNode = new ImpNode();
                            impNode.addChild(wffOne.getWffTree().getChild(0));
                            impNode.addChild(wffTwo.getWffTree().getChild(1));
                        } else if (wffTwo.getWffTree().getChild(1).stringEquals(wffOne.getWffTree().getChild(0))) {
                            impNode = new ImpNode();
                            impNode.addChild(wffTwo.getWffTree().getChild(0));
                            impNode.addChild(wffOne.getWffTree().getChild(1));
                        }

                        // If we found one, then we add it.
                        if (impNode != null) {
                            changed = true;
                            wffOne.setFlags(NDFlag.ACTIVE | NDFlag.HS);
                            wffTwo.setFlags(NDFlag.ACTIVE | NDFlag.HS);
                            this.addPremise(new NDWffTree(impNode, NDStep.HS, wffOne, wffTwo));
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean findDoubleNegations() {

        return false;
    }

    /**
     * @return
     */
    private boolean appendConjunctions() {
        int sz = this.PREMISES_LIST.size();
        boolean changed = false;
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                if (i != j) {
                    changed = true;
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    AndNode andNodeOne = new AndNode();
                    AndNode andNodeTwo = new AndNode();
                    andNodeOne.addChild(wffOne.getWffTree());
                    andNodeOne.addChild(wffTwo.getWffTree());

                    // Add them in the reverse order.
                    andNodeTwo.addChild(wffTwo.getWffTree());
                    andNodeTwo.addChild(wffOne.getWffTree());

                    // Conjunction is communative, so we have to add both.
                    this.addPremise(new NDWffTree(andNodeOne, NDFlag.AND_I, NDStep.AND_I, wffOne, wffTwo));
                    this.addPremise(new NDWffTree(andNodeTwo, NDFlag.AND_I, NDStep.AND_I, wffOne, wffTwo));
                }
            }
        }
        return changed;
    }

    /**
     * @return
     */
    private boolean appendDisjunctions() {
        if (!this.CONCLUSION_WFF.getWffTree().isOr()) { return false; }
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree wff = this.PREMISES_LIST.get(i);
            int idx = -1;
            // Check to see if we can add a disjunction between a node of the conclusion and a current premise.
            if (wff.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree().getChild(0))) { idx = 1; }
            else if (wff.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree().getChild(1))) { idx = 0; }

            // If we matched 50% of the conclusion, then we're done.
            if (idx != -1) {
                wff.setFlags(NDFlag.ACTIVE);
                OrNode orNodeOne = new OrNode();
                OrNode orNodeTwo = new OrNode();
                orNodeOne.addChild(wff.getWffTree());
                orNodeOne.addChild(this.CONCLUSION_WFF.getWffTree().getChild(idx));

                // Add them in the reverse order.
                orNodeTwo.addChild(this.CONCLUSION_WFF.getWffTree().getChild(idx));
                orNodeTwo.addChild(wff.getWffTree());

                // Disjunctions are communative so we add them both.
                this.addPremise(new NDWffTree(orNodeOne, NDFlag.OR_I, NDStep.OR_I, wff));
                this.addPremise(new NDWffTree(orNodeTwo, NDFlag.OR_I, NDStep.OR_I, wff));
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    private boolean findContradictions() {
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);

                    // Compute the negated of one of the nodes and see if they're equivalent.
                    if (BaseTruthTreeGenerator.getFlippedNode(wffOne.getWffTree()).stringEquals(wffTwo.getWffTree())) {
                        wffOne.setFlags(NDFlag.ACTIVE);
                        wffTwo.setFlags(NDFlag.ACTIVE);
                        NDWffTree falseNode = new NDWffTree(new FalseNode(), NDFlag.ACTIVE, NDStep.FALSE_I, wffOne, wffTwo);
                        NDWffTree conclusionNode = new NDWffTree(this.CONCLUSION_WFF.getWffTree(), NDFlag.ACTIVE, NDStep.FALSE_E, falseNode);
                        this.addPremise(falseNode);
                        this.addPremise(conclusionNode);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return
     */
    private boolean findConclusion() {
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree())) {
                // Make sure the conclusion and its parents are marked as active.
                this.activateLinks(ndWffTree);
                return true;
            }
        }
        return false;
    }

    /**
     * @param _conclusionNode
     */
    private void activateLinks(NDWffTree _conclusionNode) {
        if (_conclusionNode == null || _conclusionNode.getDerivedParents().isEmpty()) { return; }

        _conclusionNode.setFlags(NDFlag.ACTIVE);
        for (NDWffTree ndWffTree : _conclusionNode.getDerivedParents()) {
            this.activateLinks(ndWffTree);
        }
    }

    /**
     *
     */
    private void deactivateLinks() {
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (!ndWffTree.isActive()) {
                this.deactivateLinksHelper(ndWffTree);
            }
        }
    }

    /**
     * @param _ndWffTree
     */
    private void addPremise(NDWffTree _ndWffTree) {
        if (!this.PREMISES_LIST.contains(_ndWffTree)) {
            this.PREMISES_LIST.add(_ndWffTree);
        }
    }

    /**
     * @param _tree
     */
    private void deactivateLinksHelper(NDWffTree _tree) {
        // Base case: the tree is null, active already, or has no parents.
        if (_tree == null || _tree.isActive() || _tree.getDerivedParents().isEmpty()) { return; }

        for (NDWffTree recursiveTree : _tree.getDerivedParents()) {
            this.deactivateLinksHelper(recursiveTree);
        }
    }
}
