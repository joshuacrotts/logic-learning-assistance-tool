package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

/**
 *
 */
public abstract class BaseTruthTreeGenerator {

    /**
     *
     */
    protected WffTree tree;

    public BaseTruthTreeGenerator(WffTree _tree) {
        this.tree = _tree;
    }
}
