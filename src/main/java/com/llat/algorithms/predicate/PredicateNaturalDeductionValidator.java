package com.llat.algorithms.predicate;

import com.llat.algorithms.ArgumentTruthTreeValidator;
import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 */
public final class PredicateNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    private static final int TIMEOUT = 100;

    /**
     *
     */
    private final HashSet<Character> CONSTANTS;

    /**
     *
     */
    private final HashSet<Character> CONCLUSION_CONSTANTS;

    /**
     *
     */
    private int containsFlags;

    public PredicateNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        super(_wffTreeList);
        // Get all constants and conclusion constants...
        this.CONSTANTS = new HashSet<>();
        this.CONCLUSION_CONSTANTS = new HashSet<>();
        this.containsFlags = 0;
//
//        for (int i = 0; i < _wffTreeList.size() - 1; i++)
//            this.addAllConstantsToSet(_wffTreeList.get(i), this.CONSTANTS);
//        this.addAllConstantsToSet(_wffTreeList.get(_wffTreeList.size() - 1), this.CONCLUSION_CONSTANTS);
//
//        // Since a lot of these operations are computationally expensive, it's worthwhile to see
//        // if we even need to do some of them.
//        this.computeFlags();
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
        return null;
    }
}