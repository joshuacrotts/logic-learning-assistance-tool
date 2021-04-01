package com.llat.models.treenode;

import com.llat.algorithms.TexPrinter;

/**
 *
 */
public class UniversalQuantifierNode extends QuantifierNode {

    public UniversalQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, _variableSymbol, NodeType.UNIVERSAL);
    }

    public UniversalQuantifierNode(String _variableSymbol) {
        super("(" + _variableSymbol + ")", _variableSymbol, NodeType.UNIVERSAL);
    }

    public UniversalQuantifierNode() {
        this(null, null);
    }

    @Override
    public WffTree copy() {
        UniversalQuantifierNode universalCopy = new UniversalQuantifierNode(this.getSymbol(), this.getVariableSymbol());
        universalCopy.setFlags(this.getFlags());
        for (WffTree ch : this.getChildren()) {
            universalCopy.addChild(ch.copy());
        }

        return universalCopy;
    }

    @Override
    public String getStringRep() {
        WffTree ch1 = this.getChild(0);
        return this.getSymbol() + ch1.getStringRep();
    }

    @Override
    public String getTexCommand() {
        WffTree ch1 = this.getChild(0);
        return "(" + TexPrinter.removeMathMode(this.getVariableSymbol()) + ")" + ch1.getTexCommand();
    }

    @Override
    public String getTexParseCommand() {
        return "(" + TexPrinter.removeMathMode(this.getVariableSymbol()) + ")";
    }
}
