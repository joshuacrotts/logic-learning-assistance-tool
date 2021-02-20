package com.llat.input.treenode;

import java.util.LinkedList;

/**
 *
 */
public class WffTree {

    /**
     * Defines the type of node that we're using. There should be only one
     * ROOT node in the tree.
     */
    private final NodeType NODE_TYPE;

    /**
     *
     */
    private final LinkedList<WffTree> children;

    public WffTree(NodeType _nodeType, WffTree _left, WffTree _right) {
        this.NODE_TYPE = _nodeType;
        this.children = new LinkedList<>();

        if (_left != null) {
            this.children.add(_left);
        }

        if (_right != null) {
            this.children.add(_right);
        }
    }

    public WffTree(NodeType _nodeType) {
        this(_nodeType, null, null);
    }

    public WffTree() {
        this(NodeType.ROOT, null, null);
    }

    public void printSyntaxTree() {
        System.out.println(this.printSyntaxTreeHelper(0));
    }

    /**
     * Recursive function to print a syntax tree. The current depth is passed
     * as the "indent" parameter so that the output looks properly nested.
     * Each recursive call for a child is indented by two additional spaces.
     *
     * @param indent current indentation level
     * @return a string representation of this syntax tree node (and its descendants)
     */
    private StringBuilder printSyntaxTreeHelper(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(" ");
        }

        sb.append(this.toString());
        if (!this.children.isEmpty()) {
            sb.append(" (\n");
            boolean isFirstChild = true;
            for (WffTree child : this.children) {
                if (!isFirstChild) {
                    sb.append(",\n");
                }
                isFirstChild = false;
                sb.append(child.printSyntaxTreeHelper(indent + 2));
            }
            sb.append(")");
        }

        return sb;
    }

    public boolean isRoot() {
        return this.NODE_TYPE == NodeType.ROOT;
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

    public LinkedList<WffTree> getChildren() {
        return this.children;
    }

    public void addChild(WffTree _node) {
        this.children.add(_node);
    }

    @Override
    public String toString() {
        return this.NODE_TYPE.toString();
    }
}
