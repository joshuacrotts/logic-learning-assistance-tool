package com.llat.models.treenode;

/**
 *
 */
public class ImpNode extends WffTree {

    /**
     * Uses the dash, greater than (->) as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "->";

    private static final String DEFAULT_TEX_SYMBOL = "\\to";

    public ImpNode(String _symbol) {
        super(_symbol, NodeType.IMP);
    }

    public ImpNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        ImpNode or = new ImpNode(this.getSymbol());
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
