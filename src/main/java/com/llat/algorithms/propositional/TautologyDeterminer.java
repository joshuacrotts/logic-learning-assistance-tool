package com.llat.algorithms.propositional;

import com.llat.algorithms.MainOperatorDetector;
import com.llat.models.treenode.WffTree;

public class TautologyDeterminer {

    /**
     * WffTree object used in the algorithm. This should be a propositional wff.
     */
    private WffTree wffTree;

    public TautologyDeterminer(WffTree _wffTree) {
        if (_wffTree.isPredicateWff()) {
            throw new IllegalArgumentException("WffTree must be a propositional formula to use this algorithm.");
        }
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public boolean get() {
        // If we haven't generated the truth table, go ahead and do it.
        if (this.wffTree.getTruthValues().isEmpty()) {
            TruthTableGenerator gen = new TruthTableGenerator(this.wffTree);
            gen.get();
        }

        WffTree mainOp = new MainOperatorDetector(this.wffTree).get();

        for (Boolean b : mainOp.getTruthValues()) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
