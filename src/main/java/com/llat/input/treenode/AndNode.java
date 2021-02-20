package com.llat.input.treenode;

/**
 * Conjunction node.
 *
 * @author Joshua Crotts
 * @date 2/20/2021
 */
public class AndNode extends WffTree {

    public AndNode(WffTree _left, WffTree _right) {
        super(NodeType.AND, _left, _right);
    }

    public AndNode() {
        this(null, null);
    }
}
