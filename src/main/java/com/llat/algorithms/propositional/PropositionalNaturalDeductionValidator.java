package com.llat.algorithms.propositional;

import com.llat.algorithms.ArgumentTruthTreeValidator;
import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        while (!this.findConclusion() && !this.findContradictions()) {
            for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
                NDWffTree premise = this.PREMISES_LIST.get(i);
                if (this.satisfy(premise.getWffTree(), premise)) {
                    premise.setFlags(NDFlag.SAT);
                }
            }

            this.satisfy(this.CONCLUSION_WFF.getWffTree(), this.CONCLUSION_WFF);
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
}

