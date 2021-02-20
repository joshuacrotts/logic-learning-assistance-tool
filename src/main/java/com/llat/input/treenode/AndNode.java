package com.llat.input.treenode;

/**
 *
 */
public class AndNode extends WffTree {

    public AndNode(WffTree _left, WffTree _right) {
        super(NodeType.AND, _left, _right);
    }

    public AndNode() {
        this(null, null);
    }
}
