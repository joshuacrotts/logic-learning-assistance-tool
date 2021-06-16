package com.llat.models.treenode;

public abstract class QuantifierNode extends WffTree {

    /**
     * Symbol used to define the variable that is being quantified by the universal quantifier.
     */
    private String variableSymbol;

    public QuantifierNode(String _symbol, String _variableSymbol, NodeType _nodeType) {
        super(_symbol, _nodeType);
        if (_variableSymbol.length() != 1) {
            throw new IllegalArgumentException("Variable for quantifier can only be one character long.");
        }
        this.variableSymbol = _variableSymbol;
    }

    public String getVariableSymbol() {
        return this.variableSymbol;
    }

    public char getVariableSymbolChar() { return this.variableSymbol.charAt(0); }

    public void setVariableSymbol(String _s) {
        this.variableSymbol = _s;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.variableSymbol;
    }
}
