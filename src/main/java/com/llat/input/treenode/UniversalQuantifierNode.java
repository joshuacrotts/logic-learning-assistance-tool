package com.llat.input.treenode;

/**
 *
 */
public class UniversalQuantifierNode extends WffTree {

    /**
     *
     */
    private final String VARIABLE_SYMBOL;

    public UniversalQuantifierNode(String _variableSymbol, WffTree _left, WffTree _right) {
        super(NodeType.UNIVERSAL, _left, _right);
        this.VARIABLE_SYMBOL = _variableSymbol;
    }

    public UniversalQuantifierNode(String _variableSymbol) {
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
