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

    /**
     * The default TeX symbol is a custom symbol I made. It's just
     * the double arrow but it looks a little cleaner.
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\varliff";

    public BicondNode(String _symbol) {
        super(_symbol, NodeType.BICOND);
    }

    public BicondNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        BicondNode bicondNode = new BicondNode(this.getSymbol());
        bicondNode.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            bicondNode.addChild(ch.copy());
        }
        return bicondNode;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        WffTree ch2 = this.getChild(1);

        return "(" + ch1.getStringRep() + " " + this.getSymbol() + " " + ch2.getStringRep() + ")";
    }

    @Override
    public String getTexCommand() {
        WffTree ch1 = this.getChild(0);
        WffTree ch2 = this.getChild(1);

        return "(" + ch1.getTexCommand() + " " + DEFAULT_TEX_SYMBOL + " " + ch2.getTexCommand() + ")";
    }

    @Override
    public String getTexParseCommand() {
        return DEFAULT_TEX_SYMBOL;
    }
}
