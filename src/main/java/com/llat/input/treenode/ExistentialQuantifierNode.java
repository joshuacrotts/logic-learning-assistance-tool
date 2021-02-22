package com.llat.input.treenode;

/**
 *
 */
public class ExistentialQuantifierNode extends WffTree {

    /**
     *
     */
    private final String VARIABLE_SYMBOL;

    public ExistentialQuantifierNode(String _variableSymbol, WffTree _left, WffTree _right) {
        super(NodeType.EXISTENTIAL, _left, _right);
        this.VARIABLE_SYMBOL = _variableSymbol;
    }

    public ExistentialQuantifierNode(String _variableSymbol) {
        this(_variableSymbol, null, null);
    }

    public String getVariableSymbol() {
        return this.VARIABLE_SYMBOL;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.VARIABLE_SYMBOL;
    }
}
