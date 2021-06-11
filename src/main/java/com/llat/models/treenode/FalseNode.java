package com.llat.models.treenode;

import com.llat.algorithms.TexPrinter;

/**
 *
 */
public class FalseNode extends WffTree {

    /**
     *
     */
    private static final String DEFAULT_SYMBOL = "⊥";

    /**
     *
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\perp";

    public FalseNode() {
        super(DEFAULT_SYMBOL, NodeType.FALSE);
    }

    @Override
    public WffTree copy() {
        FalseNode falseNode = new FalseNode();
        falseNode.setFlags(this.getFlags());
        return falseNode;
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
