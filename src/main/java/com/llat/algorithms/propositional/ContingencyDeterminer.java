package com.llat.algorithms.propositional;

import com.llat.algorithms.MainOperatorDetector;
import com.llat.models.treenode.WffTree;

public class ContingencyDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public ContingencyDeterminer(WffTree _wffTree) {
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
        boolean hasTrue = false;
        boolean hasFalse = false;
        for (Boolean b : mainOp.getTruthValues()) {
            if (hasTrue && hasFalse) {
                return true;
            }

            if (!b) {
                hasFalse = true;
            } else {
                hasTrue = true;
            }
        }

        return hasTrue && hasFalse;
    }
}
