package com.llat.algorithms;

import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicalEquivalenceDeterminer {

    /**
     *
     */
    private WffTree wffTreeOne;

    /**
     *
     */
    private WffTree wffTreeTwo;

    public LogicalEquivalenceDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        this.wffTreeOne = _wffTreeOne;
        this.wffTreeTwo = _wffTreeTwo;
    }

    /**
     *
     * @return
     */
    public boolean get() {
        if (this.wffTreeOne.getTruthValues().isEmpty()) {
            TruthTableGenerator t1 = new TruthTableGenerator(this.wffTreeOne);
            t1.get();
        }

        if (this.wffTreeTwo.getTruthValues().isEmpty()) {
            TruthTableGenerator t2 = new TruthTableGenerator(this.wffTreeTwo);
            t2.get();
        }

        // If the two have a different # of atoms, then it's automatically not equivalent.
        if (this.wffTreeOne.getTruthValues().size() != this.wffTreeTwo.getTruthValues().size()) {
            return false;
        }

        for (int i = 0; i < this.wffTreeOne.getTruthValues().size(); i++) {
            boolean b1 = this.wffTreeOne.getTruthValues().get(i);
            boolean b2 = this.wffTreeTwo.getTruthValues().get(i);

            // If they're the same then they aren't equivalent.
            if (b1 != b2) {
                return false;
            }
        }

        return true;
    }
}
