package com.llat.algorithms.propositional;

import com.llat.algorithms.ArgumentTruthTreeValidator;
import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.LinkedList;

/**
 *
 */
public final class PropositionalNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    protected static final int TIMEOUT = 100;

    public PropositionalNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
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
    public LinkedList<NDWffTree> getNaturalDeductionProof() {
        ArgumentTruthTreeValidator truthTreeValidator = new ArgumentTruthTreeValidator(this.ORIGINAL_WFFTREE_LIST);
        if (!truthTreeValidator.isValid()) { return null; }

        // We'll either find the conclusion or time out first.
        int currIteration = 0;
        boolean foundConclusion = false;
        boolean foundContradiction = false;
        for (currIteration = 0; currIteration <= PropositionalNaturalDeductionValidator.TIMEOUT;
             currIteration++) {
            boolean mod1 = false;
            // If any of these return true we won't do the extra steps.
            mod1 = this.findSimplifications();
            mod1 = this.findModusPonens() || mod1;
            mod1 = this.findModusTollens() || mod1;
            mod1 = this.findDisjunctiveSyllogisms() || mod1;
            mod1 = this.findHypotheticalSyllogisms() || mod1;
            mod1 = this.findDoubleNegations() || mod1;
            mod1 = this.findBiconditionalEquivalences() || mod1;

            this.appendDisjunctions();
            if (!mod1) { this.appendConjunctions(); }
            if (!mod1) { mod1 = this.findMaterialImplicationEquivalences(); }
            if (!mod1) { this.findDeMorganEquivalences(); }

            // If we found a conclusion or contradiction, quit.
            if ((foundConclusion = this.findConclusion())) break;
            if ((foundContradiction = this.findContradictions())) break;
        }

        // If we timed out, just return null.
        if (currIteration > PropositionalNaturalDeductionValidator.TIMEOUT) { return null; }

        // Backtrack from the conclusion to mark all those nodes that were used in the proof.
        this.activateLinks(this.CONCLUSION_WFF);

        // Add the premises that were actually used in the argument.
        LinkedList<NDWffTree> args = new LinkedList<>();
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.isActive()) {
                args.add(ndWffTree);
            }
        }

        // Finally, add the conclusion IF it wasn't derived through a contradiction.
        if (!foundContradiction) { args.add(this.CONCLUSION_WFF); }
        return args;
    }
}