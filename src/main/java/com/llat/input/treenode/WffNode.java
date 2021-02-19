package com.llat.input.treenode;

/**
 *
 */
public class WffNode {

    /** */
    private final NodeType NODE_TYPE;

    /** */
    private final WffNode LEFT_CHILD;

    /** */
    private final WffNode RIGHT_CHILD;

    public WffNode(NodeType _nodeType, WffNode _left, WffNode _right) {
        this.NODE_TYPE = _nodeType;
        this.LEFT_CHILD = _left;
        this.RIGHT_CHILD = _right;
    }

    public boolean isAtom() {
        return this.NODE_TYPE == NodeType.ATOM;
    }

    public boolean isAnd() {
        return this.NODE_TYPE == NodeType.AND;
    }

    public boolean isOr() {
        return this.NODE_TYPE == NodeType.OR;
    }

    public boolean isImp() {
        return this.NODE_TYPE == NodeType.IMP;
    }

    public boolean isBicond() {
        return this.NODE_TYPE == NodeType.BICOND;
    }

    public NodeType getNodeType() {
        return this.NODE_TYPE;
    }

    public WffNode getLeftChild() {
        return this.LEFT_CHILD;
    }

    public WffNode getRightChild() {
        return this.RIGHT_CHILD;
    }

    @Override
    public String toString() {
        return this.NODE_TYPE.toString();
    }
}
