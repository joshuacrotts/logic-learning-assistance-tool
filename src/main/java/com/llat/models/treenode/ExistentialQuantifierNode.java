package com.llat.models.treenode;

import com.llat.algorithms.TexPrinter;

/**
 *
 */
public class ExistentialQuantifierNode extends QuantifierNode {

    /**
     * The existential operator is the same across the board.
     */
    private static final String DEFAULT_SYMBOL = "∃";

    /**
     * The default TeX symbol is just the standard existential symbol.
     */
    private static final String DEFAULT_TEX_SYMBOL = "\\exists";

    public ExistentialQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, _variableSymbol, NodeType.EXISTENTIAL);
    }

    public ExistentialQuantifierNode(String _variableSymbol) {
        super("(" + DEFAULT_SYMBOL + _variableSymbol + ")", _variableSymbol, NodeType.EXISTENTIAL);
    }

    public ExistentialQuantifierNode() {
        this(null, null);
    }

    @Override
    public WffTree copy() {
        ExistentialQuantifierNode existentialCopy = new ExistentialQuantifierNode(this.getSymbol(), this.getVariableSymbol());
        existentialCopy.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            existentialCopy.addChild(ch.copy());
        }

        return existentialCopy;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        return this.getSymbol() + ch1.getStringRep();
    }

    @Override
    public String getTexCommand() {
        WffTree ch1 = this.getChild(0);
        return "(" + DEFAULT_TEX_SYMBOL + " " + TexPrinter.removeMathMode(this.getVariableSymbol()) + ")" + ch1.getTexCommand();
    }
}
