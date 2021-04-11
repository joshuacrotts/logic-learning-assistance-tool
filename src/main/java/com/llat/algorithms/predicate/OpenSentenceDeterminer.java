package com.llat.algorithms.predicate;

import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class OpenSentenceDeterminer {

    /**
     *
     */
    private final WffTree wffTree;

    public OpenSentenceDeterminer(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public boolean isOpenSentence() {
        FreeVariableDetector fvd = new FreeVariableDetector(this.wffTree);
        return !fvd.getFreeVariables().isEmpty();
    }
}
