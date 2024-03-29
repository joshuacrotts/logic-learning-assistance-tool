package com.llat.models.treenode;

public abstract class QuantifierNode extends WffTree {

    /**
     * Symbol used to define the variable that is being quantified by the universal quantifier.
     */
    private String variableSymbol;

    public QuantifierNode(String _symbol, String _variableSymbol, NodeType _nodeType) {
        super(_symbol, _nodeType);
        this.variableSymbol = _variableSymbol;
    }

    public String getVariableSymbol() {
        return this.variableSymbol;
    }

    public void setVariableSymbol(String _s) {
        this.variableSymbol = _s;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.variableSymbol;
    }
}
