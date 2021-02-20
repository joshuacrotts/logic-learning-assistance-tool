package com.llat.input.treenode;

/**
 *
 */
public class OrNode extends WffTree {

    public OrNode(WffTree _left, WffTree _right) {
        super(NodeType.OR, _left, _right);
    }

    public OrNode() {
        this(null, null);
    }
}
