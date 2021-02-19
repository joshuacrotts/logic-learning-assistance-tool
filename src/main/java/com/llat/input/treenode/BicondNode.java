package com.llat.input.treenode;

/**
 *
 */
public class BicondNode extends WffNode {

    public BicondNode(WffNode _left, WffNode _right) {
        super(NodeType.BICOND, _left, _right);
    }
}
