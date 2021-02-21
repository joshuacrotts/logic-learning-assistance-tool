package com.llat.input.treenode;

/**
 *
 */
public class VariableNode extends WffTree {

    /**
     *
     */
    private final String VARIABLE_SYMBOL;

    public VariableNode(String _variableSymbol, WffTree _left, WffTree _right) {
        super(NodeType.VARIABLE, _left, _right);
        this.VARIABLE_SYMBOL = _variableSymbol;
    }

    public VariableNode(String _variableSymbol) {
        this(_variableSymbol, null, null);
    }

    public String getVariableSymbol() {
        return this.VARIABLE_SYMBOL;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
