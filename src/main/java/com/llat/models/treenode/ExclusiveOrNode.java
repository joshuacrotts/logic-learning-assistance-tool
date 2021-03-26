package com.llat.models.treenode;

/**
 *
 */
public class ExclusiveOrNode extends WffTree {

    /**
     * Uses the crosshair (⊕) as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "⊕";

    public ExclusiveOrNode(String _symbol) {
        super(_symbol, NodeType.XOR);
    }

    public ExclusiveOrNode() {
        this(DEFAULT_SYMBOL);
    }

    @Override
    public WffTree copy() {
        ExclusiveOrNode xor = new ExclusiveOrNode(this.getSymbol());
        for (WffTree ch : this.getChildren()) {
            xor.addChild(ch.copy());
        }
        return xor;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        WffTree ch2 = this.getChild(1);

        return "(" + ch1.getStringRep() + " " + this.getSymbol() + " " + ch2.getStringRep() + ")";
    }
}
