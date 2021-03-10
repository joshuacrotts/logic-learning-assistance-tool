package com.llat.models.treenode;

/**
 *
 */
public class IdentityNode extends WffTree {

    public IdentityNode() {
        super("=", NodeType.IDENTITY);
    }
}
