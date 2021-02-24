package com.llat.models.treenode;

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
    private final String SYMBOL;

    /**
     *
     */
    private final LinkedList<WffTree> children;

    public WffTree(String _symbol, NodeType _nodeType) {
        this.SYMBOL = _symbol;
        this.NODE_TYPE = _nodeType;
        this.children = new LinkedList<>();
    }

    public WffTree(NodeType _nodeType) {
        this(null, _nodeType);
    }

    public WffTree() {
        this(null, NodeType.ROOT);
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
     * @author Steve Tate
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

    public WffTree getChild(int i) {
        try {
            return this.children.get(i);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public void addChild(WffTree _node) {
        this.children.add(_node);
    }

    public boolean isRoot() {
        return this.NODE_TYPE == NodeType.ROOT;
    }

    public boolean isAtom() {
        return this.NODE_TYPE == NodeType.ATOM;
    }

    public boolean isNegation() {
        return this.NODE_TYPE == NodeType.NEG;
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

    public boolean isIdentity() { return this.NODE_TYPE == NodeType.IDENTITY; };

    public boolean isQuantifier() { return this.NODE_TYPE == NodeType.EXISTENTIAL || this.NODE_TYPE == NodeType.UNIVERSAL;}

    public boolean isBinaryOp() {
        return this.isAnd() || this.isOr() || this.isImp() || this.isBicond() || this.isIdentity();
    }

    public boolean isPredicate() {
        return this.NODE_TYPE == NodeType.PREDICATE;
    }

    public boolean isConstant() {
        return this.NODE_TYPE == NodeType.CONSTANT;
    }

    public boolean isVariable() {
        return this.NODE_TYPE == NodeType.VARIABLE;
    }

    public NodeType getNodeType() {
        return this.NODE_TYPE;
    }

    public LinkedList<WffTree> getChildren() {
        return this.children;
    }

    public String getSymbol() {
        return this.SYMBOL;
    }

    @Override
    public String toString() {
        return this.NODE_TYPE.toString();
    }
}
