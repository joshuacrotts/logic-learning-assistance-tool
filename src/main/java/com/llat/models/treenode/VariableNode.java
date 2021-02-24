package com.llat.models.treenode;

/**
 *
 */
public class VariableNode extends WffTree {

    public VariableNode(String _symbol) {
        super(_symbol, NodeType.VARIABLE);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + super.getSymbol();
    }
}
