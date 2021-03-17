package com.llat.models.treenode;

/**
 *
 */
public class BicondNode extends WffTree {

    /**
     * Uses the double arrow less than, dash, greater than (<->)
     * as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "<->";

    public BicondNode(String _symbol) {
        super(_symbol, NodeType.BICOND);
    }

    public BicondNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        WffTree ch2 = this.getChild(1);

        return "(" + ch1.getStringRep() + " " + this.getSymbol() + " " + ch2.getStringRep() + ")";
    }
}
