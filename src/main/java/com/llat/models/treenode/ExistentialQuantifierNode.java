package com.llat.models.treenode;

/**
 *
 */
public class ExistentialQuantifierNode extends QuantifierNode {

    public ExistentialQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, _variableSymbol, NodeType.EXISTENTIAL);
    }

    public ExistentialQuantifierNode() {
        this(null, null);
    }
}
