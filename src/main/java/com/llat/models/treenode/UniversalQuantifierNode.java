package com.llat.models.treenode;

/**
 *
 */
public class UniversalQuantifierNode extends QuantifierNode {

    public UniversalQuantifierNode(String _symbol, String _variableSymbol) {
        super(_symbol, _variableSymbol, NodeType.UNIVERSAL);
    }

    public UniversalQuantifierNode() {
        this(null, null);
    }
}
