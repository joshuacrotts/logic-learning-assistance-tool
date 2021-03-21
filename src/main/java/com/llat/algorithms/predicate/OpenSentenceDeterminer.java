package com.llat.algorithms.predicate;

import com.llat.algorithms.predicate.FreeVariableDetector;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class OpenSentenceDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public OpenSentenceDeterminer(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public boolean get() {
        FreeVariableDetector fvd = new FreeVariableDetector(this.wffTree);
        return !fvd.get().isEmpty();
    }
}
