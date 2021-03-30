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

    /**
     * The default TeX symbol is just the simple right-arrow.
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\to";

    public ImpNode(String _symbol) {
        super(_symbol, NodeType.IMP);
    }

    public ImpNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        ImpNode impNode = new ImpNode(this.getSymbol());
        impNode.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            impNode.addChild(ch.copy());
        }
        return impNode;
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
