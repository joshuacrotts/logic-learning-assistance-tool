package com.llat.algorithms;

import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.LinkedList;

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
    protected final LinkedList<NDWffTree> PREMISES_LIST;

    /**
     *
     */
    protected final NDWffTree CONCLUSION_WFF;

    public BaseNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        this.ORIGINAL_WFFTREE_LIST = _wffTreeList;
        this.PREMISES_LIST = new LinkedList<>();
        this.CONCLUSION_WFF = new NDWffTree(_wffTreeList.getLast().getChild(0), NDStep.C);

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
     * TODO implementation.
     * @return
     */
    protected boolean findIndirectProof() {
        return false;
    }

    /**
     * TODO implementation.
     * @return
     */
    protected boolean findConditionalProof() {
        return false;
    }

    /**
     * Computes all DeMorgan equivalences found through AND, OR, and IMP nodes. The DeMorgan rule goes both ways -
     * meaning the equivalence is applied on both occurrences e.g.,
     *
     * (P BINOP Q) "factors" the negation out, even if there isn't one such as ~(~P ~BINOP ~Q)
     * ~(P BINOP Q) distributes the negation inward as (~P ~BINOP ~Q).
     *
     * Once a DeMorgan rule is applied to a well-formed formula, it is impossible to re-apply this rule to that wff or
     * its child.
     *
     * @return true if a DeMorgan's equivalence was found, false otherwise.
     */
    protected boolean findDeMorganEquivalences() {
        int sz = this.PREMISES_LIST.size();
        boolean found = false;
        for (int i = 0; i < sz; i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            // We need to make sure we haven't tried to reduce it before.
            if (!ndWffTree.isDEMActive()) {
                // Two types: one is ~(X B Y) => (~X ~B ~Y)
                WffTree wff = ndWffTree.getWffTree();
                WffTree deMorganNode = null;
                if (wff.isNegation() && (wff.getChild(0).isOr() || wff.getChild(0).isAnd() || wff.getChild(0).isImp())) {
                    deMorganNode = BaseTruthTreeGenerator.getNegatedBinaryNode(wff.getChild(0)); // B
                    deMorganNode.addChild(wff.getChild(0).isImp() ? wff.getChild(0).getChild(0)
                            : BaseTruthTreeGenerator.getFlippedNode(wff.getChild(0).getChild(0))); // LHS X
                    deMorganNode.addChild(BaseTruthTreeGenerator.getFlippedNode(wff.getChild(0).getChild(1))); // RHS Y
                }
                // Other is (X B Y) => ~(~X ~B ~Y)
                else if ((wff.isOr() || wff.isAnd() || wff.isImp())) {
                    WffTree negBinaryNode = BaseTruthTreeGenerator.getNegatedBinaryNode(wff); // B
                    negBinaryNode.addChild(BaseTruthTreeGenerator.getFlippedNode(wff.getChild(0))); // LHS X
                    negBinaryNode.addChild(BaseTruthTreeGenerator.getFlippedNode(wff.getChild(1))); // RHS Y
                    deMorganNode = new NegNode();
                    deMorganNode.addChild(negBinaryNode);
                }
                // If we found a node, then it'll be applied/inserted here.
                if (deMorganNode != null) {
                    found = true;
                    ndWffTree.setFlags(NDFlag.DEM);
                    this.addPremise(new NDWffTree(deMorganNode, NDFlag.DEM, NDStep.DEM, ndWffTree));
                }
            }
        }
        return found;
    }

    /**
     * Finds an equivalent biconditional elimination or introduction for implications. There are two forms:
     * <->E is (A <-> B) === (A -> B) & (B -> A), and
     * <->I is (A -> B), (B -> A) === (A <-> B).
     *
     * @return true if a biconditional manipulation rule was used, false otherwise.
     */
    protected boolean findBiconditionalEquivalences() {
        int sz = this.PREMISES_LIST.size();
        boolean found = false;
        // First try to find (A <-> B).
        for (int i = 0; i < sz; i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().isBicond() && !ndWffTree.isBCActive()) {
                found = true;
                ImpNode impOne = new ImpNode();
                ImpNode impTwo = new ImpNode();
                impOne.addChild(ndWffTree.getWffTree().getChild(0));
                impOne.addChild(ndWffTree.getWffTree().getChild(1));
                impTwo.addChild(ndWffTree.getWffTree().getChild(1));
                impTwo.addChild(ndWffTree.getWffTree().getChild(0));
                ndWffTree.setFlags(NDFlag.BC);
                this.addPremise(new NDWffTree(impOne, NDFlag.BC, NDStep.BCE, ndWffTree));
                this.addPremise(new NDWffTree(impTwo, NDFlag.BC, NDStep.BCE, ndWffTree));
            }
        }

        // Now try to find (A -> B) and (B -> A) to get (A <-> B).
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                if (i != j) {
                    NDWffTree ndWffTreeOne = this.PREMISES_LIST.get(i);
                    NDWffTree ndWffTreeTwo = this.PREMISES_LIST.get(j);

                    // First check to see if they're implicatons (and haven't been previously used).
                    if (ndWffTreeOne.getWffTree().isImp() && ndWffTreeTwo.getWffTree().isImp()
                            && !ndWffTreeOne.isBCActive() && !ndWffTreeTwo.isBCActive()) {
                        // Now check to see that their children match.
                        if (ndWffTreeOne.getWffTree().getChild(0).stringEquals(ndWffTreeTwo.getWffTree().getChild(1))
                                && ndWffTreeTwo.getWffTree().getChild(0).stringEquals(ndWffTreeOne.getWffTree().getChild(1))) {
                            found = true;
                            BicondNode bicondNode = new BicondNode();
                            bicondNode.addChild(ndWffTreeOne.getWffTree().getChild(0));
                            bicondNode.addChild(ndWffTreeOne.getWffTree().getChild(1));
                            ndWffTreeOne.setFlags(NDFlag.BC);
                            ndWffTreeTwo.setFlags(NDFlag.BC);
                            this.addPremise(new NDWffTree(bicondNode, NDFlag.BC, NDStep.BCI, ndWffTreeOne, ndWffTreeTwo));
                        }
                    }
                }
            }
        }

        return found;
    }

    /**
     * Finds instances of material implication. Material implication occurs with an implication of the form
     * (A -> B) === (~A V B), and the other way around.
     *
     * @return true if a material implication equivalence was found, false otherwise.
     */
    protected boolean findMaterialImplicationEquivalences() {
        int sz = this.PREMISES_LIST.size();
        boolean found = false;
        for (int i = 0; i < sz; i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (!ndWffTree.isMIActive() && !ndWffTree.isDEMActive()) {
                WffTree newNode = null;
                // First find (A -> B)
                if (ndWffTree.getWffTree().isImp()) {
                    newNode = new OrNode();
                    newNode.addChild(BaseTruthTreeGenerator.getFlippedNode(ndWffTree.getWffTree().getChild(0)));
                    newNode.addChild(ndWffTree.getWffTree().getChild(1));
                }
                // Now try (A V B) === (~A => B)
                else if (ndWffTree.getWffTree().isOr()) {
                    newNode = new ImpNode();
                    newNode.addChild(BaseTruthTreeGenerator.getFlippedNode(ndWffTree.getWffTree().getChild(0)));
                    newNode.addChild(ndWffTree.getWffTree().getChild(1));
                }

                // If we found one, add it here.
                if (newNode != null) {
                    found = true;
                    ndWffTree.setFlags(NDFlag.MI);
                    this.addPremise(new NDWffTree(newNode, NDFlag.MI, NDStep.MI, ndWffTree));
                }
            }
        }

        return found;
    }

    /**
     * Finds &E rules, or otherwise called simplifications. The idea is that a conjunction is reducible to its
     * two children. For instance, (A & B) === A, B. Once a conjunction rule is broken down, it cannot be reconstructed
     * or broken down again.
     *
     * @return true if a &E rule was found and used, false otherwise.
     */
    protected boolean findSimplifications() {
        boolean changed = false;
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().isAnd() && !ndWffTree.isAndEActive() && !ndWffTree.isAndIActive()) {
                // Break the conjunction down if it hasn't already been.
                changed = true;
                ndWffTree.setFlags(NDFlag.AND_E);
                WffTree lhs = ndWffTree.getWffTree().getChild(0);
                WffTree rhs = ndWffTree.getWffTree().getChild(1);
                this.addPremise(new NDWffTree(lhs, NDStep.AND_E, ndWffTree));
                this.addPremise(new NDWffTree(rhs, NDStep.AND_E, ndWffTree));
            }
        }
        return changed;
    }

    /**
     * Finds instances of modus ponens. Modus ponens takes the form (A -> B), A, deducing B in the result.
     * Once a MP rule is applied to two formulas, they cannot be used in conjunction again. We check to see
     * if wff1 NAND wff2 have their MP flag active (if they both have, then don't apply it, but if neither or only
     * one does, then it's acceptable.
     *
     * @return true if a modus ponens rule was found, false otherwise.
     */
    protected boolean findModusPonens() {
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
                            wffOne.setFlags(NDFlag.MP);
                            wffTwo.setFlags(NDFlag.MP);
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
     * Finds instances of modus tollens. Modus tollens takes the form (A -> B), ~B, deducing ~A in the result.
     * Once a MT rule is applied to two formulas, they cannot be used in conjunction again. We check to see
     * if wff1 NAND wff2 have their MT flag active (if they both have, then don't apply it, but if neither or only
     * one does, then it's acceptable.
     *
     * @return true if a modus tollens rule was found, false otherwise.
     */
    protected boolean findModusTollens() {
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
                            wffOne.setFlags(NDFlag.MT);
                            wffTwo.setFlags(NDFlag.MT);
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
     * Finds examples of disjunctive syllogism. DS is applied with a disjunctive clause (A V B) and a premise
     * either ~A or ~B but not both. Whichever one is not negated is deduced. This rule cannot be applied more than
     * once in conjunction (same as MP and MT).
     *
     * @return true if we found an example of DS, false otherwise.
     */
    protected boolean findDisjunctiveSyllogisms() {
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
                            disjNode.setFlags(NDFlag.DS);
                            othNode.setFlags(NDFlag.DS);
                            this.addPremise(new NDWffTree(dsNode, NDStep.DS, disjNode, othNode));
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * Finds examples of hypothetical syllogism. HS is the transitive property when applied to implications e.g.,
     * (A -> B), (B -> C) deduces (A -> C). This rule cannot be applied more than once in conjunction (same as MP,
     * MT, and DS).
     *
     * @return true if we found an example of HS, false otherwise.
     */
    protected boolean findHypotheticalSyllogisms() {
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
                            wffOne.setFlags(NDFlag.HS);
                            wffTwo.setFlags(NDFlag.HS);
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
    protected boolean findDoubleNegations() {
        return false;
    }

//    /**
//     *
//     * @return
//     */
//    protected boolean appendImplications() {
//        int sz = this.PREMISES_LIST.size();
//        boolean changed = false;
//        for (int i = 0; i < sz; i++) {
//            for (int j = i + 1; j < sz; j++) {
//                if (i != j) {
//                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
//                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
//                    // Heuristic.
//                    if (wffOne.getWffTree().allChildSizeCount() > BaseNaturalDeductionValidator.CONJ_CHILD_TIMEOUT
//                    && wffTwo.getWffTree().allChildSizeCount() > BaseNaturalDeductionValidator.CONJ_CHILD_TIMEOUT) { continue; }
//                    if (!wffOne.isImpIActive() || !wffTwo.isImpIActive()) {
//                        changed = true;
//                        ImpNode impNode = new ImpNode();
//                        impNode.addChild(wffOne.getWffTree());
//                        impNode.addChild(wffTwo.getWffTree());
//
//                        // Conjunction is communative, so we have to add both.
//                        wffOne.setFlags(NDFlag.IMP_I);
//                        wffTwo.setFlags(NDFlag.IMP_I);
//                        this.addPremise(new NDWffTree(impNode, NDFlag.IMP_I | NDFlag.MP, NDStep.AND_I, wffOne, wffTwo));
//                    }
//                }
//            }
//        }
//        return changed;
//    }

    /**
     * Appends paired premises together to form conjunctions. If the two attempted premises have been deduced from
     * another conjunction through an &E rule, it cannot be formed. Since the conjunction rule is a communiative operation,
     * both premises are appended.
     *
     * This method is very volatile, and can blow up the running time, so its use is very sparingly applied!
     *
     * @return true if a conjunction was found, and false otherwise.
     */
    protected boolean appendConjunctions() {
        int sz = this.PREMISES_LIST.size();
        boolean changed = false;
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    // Heuristic.
                    if (wffOne.getWffTree().allChildSizeCount() > BaseNaturalDeductionValidator.APPEND_CHILD_TIMEOUT
                            && wffTwo.getWffTree().allChildSizeCount() > BaseNaturalDeductionValidator.APPEND_CHILD_TIMEOUT) { break; }
                    if ((!wffOne.isAndEActive() && !wffTwo.isAndEActive())) {
                        changed = true;
                        AndNode andNodeOne = new AndNode();
                        AndNode andNodeTwo = new AndNode();
                        andNodeOne.addChild(wffOne.getWffTree());
                        andNodeOne.addChild(wffTwo.getWffTree());

                        // Add them in the reverse order.
                        andNodeTwo.addChild(wffTwo.getWffTree());
                        andNodeTwo.addChild(wffOne.getWffTree());

                        // Conjunction is communative, so we have to add both.
                        wffOne.setFlags(NDFlag.AND_I);
                        wffTwo.setFlags(NDFlag.AND_I);
                        this.addPremise(new NDWffTree(andNodeOne, NDFlag.AND_I, NDStep.AND_I, wffOne, wffTwo));
                        this.addPremise(new NDWffTree(andNodeTwo, NDFlag.AND_I, NDStep.AND_I, wffOne, wffTwo));
                    }
                }
            }
        }
        return changed;
    }

    /**
     * Appends a disjunction rule. The disjunction rule is a little special in that we check to see if we have satisfied
     * 50% of the conclusion, and if so, we can just use the disjunction introduction rule since only 1/2 has to be
     * satisfied for an OR. This will change later to include ANY premise, which could potentially blow up the running time...
     *
     * @return true if there is a disjunction introduction that finds the conclusion, false otherwise.
     */
    protected boolean appendDisjunctions() {
        if (!this.CONCLUSION_WFF.getWffTree().isOr()) { return false; }
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree wff = this.PREMISES_LIST.get(i);
            int idx = -1;
            // Check to see if we can add a disjunction between a node of the conclusion and a current premise.
            if (wff.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree().getChild(0))) { idx = 1; }
            else if (wff.getWffTree().stringEquals(this.CONCLUSION_WFF.getWffTree().getChild(1))) { idx = 0; }

            // If we matched 50% of the conclusion, then we're done.
            if (idx != -1) {
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
     * Finds contradictions in the premises. A contradiction, like with truth trees, occurs when we have a wff w1, and
     * somewhere in the premises, ~w1 also exists. This derives a contradiction and then concludes our, well, conclusion!
     * This is used in indirect proofs.
     *
     * @return true if a contradiction is found (and thus the proof is complete), false otherwise.
     */
    protected boolean findContradictions() {
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            for (int j = i + 1; j < this.PREMISES_LIST.size(); j++) {
                if (i != j) {
                    NDWffTree wffOne = this.PREMISES_LIST.get(i);
                    NDWffTree wffTwo = this.PREMISES_LIST.get(j);
                    // Compute the negated of one of the nodes and see if they're equivalent.
                    if (BaseTruthTreeGenerator.getFlippedNode(wffOne.getWffTree()).stringEquals(wffTwo.getWffTree())) {
                        NDWffTree falseNode = new NDWffTree(new FalseNode(), NDFlag.ACTIVE, NDStep.FALSE_I, wffOne, wffTwo);
                        NDWffTree conclusionNode = new NDWffTree(this.CONCLUSION_WFF.getWffTree(), NDFlag.ACTIVE, NDStep.FALSE_E, falseNode);
                        // Assign this as the conclusion node.
                        this.addPremise(falseNode);
                        this.addPremise(conclusionNode);
                        this.CONCLUSION_WFF.setDerivationStep(conclusionNode.getDerivationStep());
                        this.CONCLUSION_WFF.setDerivedParents(conclusionNode.getDerivedParents());
                        return true;
                    }
                }
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
                this.CONCLUSION_WFF.setDerivedParents(ndWffTree.getDerivedParents());
                this.CONCLUSION_WFF.setDerivationStep(ndWffTree.getDerivationStep());
                return true;
            }
        }
        return false;
    }

    /**
     * From the conclusion up, we activate all premises that were used in the derivation of this conclusion. Since
     * there are plenty of nodes that were generated, we only want to show the successful ones. We traverse from the
     * conclusion, through its parents until it is null or has no parents.
     *
     * @param _conclusionNode - conclusion WFF.
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
}
