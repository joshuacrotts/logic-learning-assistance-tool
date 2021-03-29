package com.llat.algorithms;

import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.ImpNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicallyContraryDeterminer {

    private WffTree combinedTree;

    public LogicallyContraryDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        BicondNode bicondNode = new BicondNode();
        bicondNode.addChild(_wffTreeOne);
        bicondNode.addChild(_wffTreeTwo);

        this.combinedTree = new WffTree();
        this.combinedTree.addChild(bicondNode);
    }

    /**
     * @return
     */
    public boolean get() {
        return false;
    }
}
