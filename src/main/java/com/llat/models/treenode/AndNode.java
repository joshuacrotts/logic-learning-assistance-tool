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
     * The default TeX symbol is the ampersand, but defined in a mathbin
     * environment so it can be read in the forest env.
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\mathbin{\\&}";

    /**
     * Keeps track of the symbol currently used. Whatever symbol is last used
     * is stored in this variable. This makes sure that, if the notation from
     * the user is consistent, there are no incidents like "~ not A" to represent
     * a double-negated atom.
     */
    private static String currentlyUsedSymbol;

    public AndNode(String _symbol) {
        super(_symbol, NodeType.AND);
        currentlyUsedSymbol = _symbol;
    }

    public AndNode() {
        this(currentlyUsedSymbol == null ? DEFAULT_SYMBOL : currentlyUsedSymbol);
    }

    @Override
    public WffTree copy() {
        AndNode and = new AndNode(this.getSymbol());
        and.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            and.addChild(ch.copy());
        }
        return and;
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
