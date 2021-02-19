package com.llat.input.treenode;

/**
 *
 */
public class OrNode extends WffNode {

    public OrNode(WffNode _left, WffNode _right) {
        super(NodeType.OR, _left, _right);
    }
}
