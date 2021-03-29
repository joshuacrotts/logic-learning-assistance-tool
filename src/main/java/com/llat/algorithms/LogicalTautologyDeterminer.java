package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicalTautologyDeterminer {

    private WffTree combinedTree;

    public LogicalTautologyDeterminer(WffTree _wffTreeOne) {
        NegNode negNode = new NegNode();

        this.combinedTree = new WffTree();
        this.combinedTree.addChild(new NegNode());
        this.combinedTree.getChild(0).addChild(combinedTree);
    }

    /**
     * @return
     */
    public boolean get() {
        return false;
    }
}
