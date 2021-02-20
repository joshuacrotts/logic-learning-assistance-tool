package com.llat.input.treenode;

/**
 *
 */
public class NegNode extends WffTree {

    public NegNode(WffTree _child) {
        super(NodeType.NEG, _child, null);
    }

    public NegNode() {
        this(null);
    }
}
