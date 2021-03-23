package com.llat.models.treenode;

/**
 *
 */
public class ConstantNode extends WffTree {

    public ConstantNode(String _symbol) {
        super(_symbol, NodeType.CONSTANT);
    }

    @Override
    public WffTree copy() {
        ConstantNode or = new ConstantNode(this.getSymbol());
        return or;
    }

    @Override
    public String getStringRep() {
        return this.getSymbol();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + super.getSymbol();
    }
}
