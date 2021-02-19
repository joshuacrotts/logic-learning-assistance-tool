package com.llat.input.treenode;

/**
 *
 */
public class AndNode extends WffNode {

    public AndNode(WffNode _left, WffNode _right) {
        super(NodeType.AND, _left, _right);
    }
}
