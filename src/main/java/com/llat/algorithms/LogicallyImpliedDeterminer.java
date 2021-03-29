package com.llat.algorithms;

import com.llat.models.treenode.ImpNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicallyImpliedDeterminer {

    private WffTree combinedTree;

    public LogicallyImpliedDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        ImpNode impNode = new ImpNode();
        impNode.addChild(_wffTreeOne);
        impNode.addChild(_wffTreeTwo);

        this.combinedTree = new WffTree();
        this.combinedTree.addChild(new NegNode());
        this.combinedTree.getChild(0).addChild(impNode);
    }

    /**
     * @return
     */
    public boolean get() {
        return false;
    }
}
