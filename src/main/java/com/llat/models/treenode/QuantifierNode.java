package com.llat.models.treenode;

public abstract class QuantifierNode extends WffTree {

    /**
     * Symbol used to define the variable that is being quantified by the universal quantifier.
     */
    private final String VARIABLE_SYMBOL;

    public QuantifierNode(String _symbol, String _variableSymbol, NodeType _nodeType) {
        super(_symbol, _nodeType);
        this.VARIABLE_SYMBOL = _variableSymbol;
    }

    public String getVariableSymbol() {
        return this.VARIABLE_SYMBOL;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.VARIABLE_SYMBOL;
    }
}
