package com.llat.models.treenode;

/**
 *
 */
public class NegNode extends WffTree {

    /**
     * Uses the tilde (~) as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "~";

    /**
     *
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\varlnot";

    public NegNode(String _symbol) {
        super(_symbol, NodeType.NEG);
    }

    public NegNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        NegNode negNode = new NegNode(this.getSymbol());
        negNode.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            negNode.addChild(ch.copy());
        }
        return negNode;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        return this.getSymbol() + ch1.getStringRep();
    }

    @Override
    public String getTexCommand() {
        WffTree ch1 = this.getChild(0);
        return DEFAULT_TEX_SYMBOL + " " + ch1.getTexCommand();
    }
}
