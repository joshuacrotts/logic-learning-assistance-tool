package com.llat.input.treenode;

/**
 *
 */
public class ConstantNode extends WffTree {

    /** */
    private final String CONSTANT_SYMBOL;

    public ConstantNode(String _constantSymbol, WffTree _left, WffTree _right) {
        super(NodeType.CONSTANT, _left, _right);
        this.CONSTANT_SYMBOL = _constantSymbol;
    }

    public ConstantNode(String _constantSymbol) {
        this(_constantSymbol, null, null);
    }

    public String getConstantSymbol() {
        return this.CONSTANT_SYMBOL;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.CONSTANT_SYMBOL;
    }
}
