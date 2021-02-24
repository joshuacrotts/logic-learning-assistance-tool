package com.llat.models.treenode;

/**
 *
 */
public class UniversalQuantifierNode extends WffTree {

    /**
     * Symbol used to define the variable that is being quantified by the universal quantifier.
     */
    private final String VARIABLE_SYMBOL;

    public UniversalQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, NodeType.UNIVERSAL);
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
