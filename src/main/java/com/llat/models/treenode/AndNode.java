package com.llat.models.treenode;

/**
 * Conjunction node.
 *
 * @author Joshua Crotts
 * @date 2/20/2021
 */
public class AndNode extends WffTree {

    /**
     * Uses the ampersand (&) as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "&";

    /**
     *
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\mathbin{\\&}";

    public AndNode(String _symbol) {
        super(_symbol, NodeType.AND);
    }

    public AndNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        AndNode or = new AndNode(this.getSymbol());
        for (WffTree ch : this.getChildren()) {
            or.addChild(ch.copy());
        }
        return or;
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
}
