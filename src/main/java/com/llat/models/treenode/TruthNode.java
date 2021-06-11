package com.llat.models.treenode;

import com.llat.algorithms.TexPrinter;

/**
 *
 */
public class TruthNode extends WffTree {

    /**
     *
     */
    private static final String DEFAULT_SYMBOL = "⊤";

    /**
     *
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\top";

    public TruthNode() {
        super(DEFAULT_SYMBOL, NodeType.TRUTH);
    }

    @Override
    public WffTree copy() {
        TruthNode truthNode = new TruthNode();
        truthNode.setFlags(this.getFlags());
        return truthNode;
    }

    @Override
    public String getStringRep() {
        return this.getSymbol();
    }

    @Override
    public String getTexCommand() {
        return TexPrinter.removeMathMode(this.getSymbol());
    }

    @Override
    public String getTexParseCommand() {
        return this.getTexCommand();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + super.getSymbol();
    }
}
