package com.llat.models.treenode;

/**
 *
 */
public class ExistentialQuantifierNode extends QuantifierNode {

    /** */
    private static final String DEFAULT_SYMBOL = "∃";

    public ExistentialQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, _variableSymbol, NodeType.EXISTENTIAL);
    }

    public ExistentialQuantifierNode(String _variableSymbol) {
        super("(" + DEFAULT_SYMBOL + _variableSymbol + ")", _variableSymbol, NodeType.EXISTENTIAL);
    }

    @Override
    public WffTree copy() {
        ExistentialQuantifierNode existentialCopy = new ExistentialQuantifierNode(this.getSymbol(), this.getVariableSymbol());
        for (WffTree ch : this.getChildren()) {
            existentialCopy.addChild(ch.copy());
        }

        return existentialCopy;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        return this.getSymbol() +  ch1.getStringRep();
    }

    public ExistentialQuantifierNode() {
        this(null, null);
    }
}
