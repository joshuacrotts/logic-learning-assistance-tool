package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicalFalsehoodDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public LogicalFalsehoodDeterminer(WffTree _wffTreeOne) {
        this.wffTree = _wffTreeOne.copy();
    }

    /**
     * @return
     */
    public boolean isFalsehood() {
        BaseTruthTreeGenerator treeGenerator;
        if (this.wffTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.wffTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.wffTree);
        }
        return new ClosedTreeDeterminer(treeGenerator.get()).hasAllClosed();
    }

    public WffTree getTree() {
        return this.wffTree;
    }
}
