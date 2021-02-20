package com.llat.input.treenode;

/**
 *
 */
public class BicondNode extends WffTree {

    public BicondNode(WffTree _left, WffTree _right) {
        super(NodeType.BICOND, _left, _right);
    }

    public BicondNode() {
        this(null, null);
    }
}
