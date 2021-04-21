package com.llat.models.treenode;

/**
 *
 */
public class OrNode extends WffTree {

    /**
     * Uses the wedge (∨) as the default symbol if none is provided.
     * This should, ideally, only happen in internal algorithms.
     */
    private static final String DEFAULT_SYMBOL = "∨";

    /**
     * The default TeX symbol is just the standard wedge symbol.
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\lor";

    /**
     * Keeps track of the symbol currently used. Whatever symbol is last used
     * is stored in this variable. This makes sure that, if the notation from
     * the user is consistent, there are no incidents like "~ not A" to represent
     * a double-negated atom.
     */
    private static String currentlyUsedSymbol;

    public OrNode(String _symbol) {
        super(_symbol, NodeType.OR);
        currentlyUsedSymbol = _symbol;
    }

    public OrNode() {
        this(currentlyUsedSymbol == null ? DEFAULT_SYMBOL : currentlyUsedSymbol);
    }

    @Override
    public WffTree copy() {
        OrNode or = new OrNode(this.getSymbol());
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

    @Override
    public String getTexParseCommand() {
        return DEFAULT_TEX_SYMBOL;
    }
}
