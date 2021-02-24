package com.llat.models.treenode;

/**
 *
 */
public class NegNode extends WffTree {

    public NegNode(String _symbol) {
        super(_symbol, NodeType.NEG);
    }
}
