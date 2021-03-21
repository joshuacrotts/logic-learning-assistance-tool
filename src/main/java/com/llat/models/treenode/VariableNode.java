package com.llat.models.treenode;

/**
 *
 */
public class VariableNode extends WffTree {

    public VariableNode(String _symbol) {
        super(_symbol, NodeType.VARIABLE);
    }

    @Override
    public String getStringRep() {
        return this.getSymbol();
    }

    @Override
    public WffTree copy() {
        VariableNode or = new VariableNode(this.getSymbol());
        return or;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + super.getSymbol();
    }
}
