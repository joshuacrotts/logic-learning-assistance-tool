package com.llat.models.treenode;

/**
 *
 */
public class ConstantNode extends WffTree {

    public ConstantNode(String _symbol) {
        super(_symbol, NodeType.CONSTANT);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + super.getSymbol();
    }
}
