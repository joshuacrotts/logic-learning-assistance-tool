package com.llat.input.treenode;

/**
 *
 */
public class ImpNode extends WffTree {

    public ImpNode(WffTree _left, WffTree _right) {
        super(NodeType.IMP, _left, _right);
    }

    public ImpNode() {
        this(null, null);
    }
}
