package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicalFalsehoodDeterminer {

    /**
     *
     */
    private final TruthTree truthTree;

    /**
     *
     */
    private final WffTree wffTree;

    public LogicalFalsehoodDeterminer(WffTree _wffTreeOne) {
        this.wffTree = _wffTreeOne.copy();
        BaseTruthTreeGenerator treeGenerator;
        if (this.wffTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.wffTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.wffTree);
        }

        this.truthTree = treeGenerator.getTruthTree();
    }

    /**
     * @return
     */
    public boolean isFalsehood() {
        return new ClosedTreeDeterminer(this.truthTree).hasAllClosed();
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public TruthTree getTruthTree() {
        return this.truthTree;
    }
}
