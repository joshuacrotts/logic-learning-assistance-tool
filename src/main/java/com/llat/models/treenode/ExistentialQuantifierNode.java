package com.llat.models.treenode;

/**
 *
 */
public class ExistentialQuantifierNode extends WffTree {

    /**
     * Symbol used to define the variable that is being quantified by the existential quantifier.
     */
    private final String VARIABLE_SYMBOL;

    public ExistentialQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, NodeType.EXISTENTIAL);
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
