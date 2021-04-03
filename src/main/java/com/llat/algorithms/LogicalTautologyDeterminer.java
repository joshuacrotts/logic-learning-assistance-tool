package com.llat.algorithms;

import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicalTautologyDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public LogicalTautologyDeterminer(WffTree _wffTreeOne) {
        this.wffTree = new WffTree();
        this.wffTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.wffTree.addChild(new NegNode());
        this.wffTree.getChild(0).addChild(_wffTreeOne.getChild(0));
    }

    /**
     * @return
     */
    public boolean isTautology() {
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
