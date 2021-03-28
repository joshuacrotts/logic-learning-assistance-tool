package com.llat.models.treenode;

/**
 *
 */
public class IdentityNode extends WffTree {

    private static final String DEFAULT_SYMBOL = "=";

    private static final String DEFAULT_TEX_SYMBOL = "\\,\\mathbb{=}\\,";

    public IdentityNode() {
        super(DEFAULT_SYMBOL, NodeType.IDENTITY);
    }

    @Override
    public IdentityNode copy() {
        IdentityNode identity = new IdentityNode();
        for (WffTree ch : this.getChildren()) {
            identity.addChild(ch.copy());
        }
        return identity;
    }

    @Override
    public String getStringRep() {
        return this.getChild(0).getStringRep() + " " + this.getSymbol() + " " + this.getChild(1).getStringRep();
    }

    @Override
    public String getTexCommand() {
        return this.getChild(0).getStringRep() + " " + DEFAULT_TEX_SYMBOL + " " + this.getChild(1).getStringRep();
    }

}
