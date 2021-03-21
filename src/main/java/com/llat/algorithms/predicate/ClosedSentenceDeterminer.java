package com.llat.algorithms.predicate;

import com.llat.models.treenode.WffTree;

/**
 *
 */
public class ClosedSentenceDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public ClosedSentenceDeterminer(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public boolean get() {
        FreeVariableDetector fvd = new FreeVariableDetector(this.wffTree);
        GroundSentenceDeterminer gsd = new GroundSentenceDeterminer(this.wffTree);
        return fvd.get().isEmpty() && !gsd.get();
    }
}
