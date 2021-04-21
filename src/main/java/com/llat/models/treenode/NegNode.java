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
     * The default TeX symbol is the tilde, but because it is difficult
     * to produce correctly, we had to make a custom definition for it.
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
        // If there's the literal word, then we want to return a space between.
        if (this.getSymbol().equalsIgnoreCase("not")) {
            return this.getSymbol() + " " + ch1.getStringRep();
        }
        return this.getSymbol() + ch1.getStringRep();
    }

    @Override
    public String getTexCommand() {
        WffTree ch1 = this.getChild(0);
        return DEFAULT_TEX_SYMBOL + " " + ch1.getTexCommand();
    }

    @Override
    public String getTexParseCommand() {
        return DEFAULT_TEX_SYMBOL;
    }
}
