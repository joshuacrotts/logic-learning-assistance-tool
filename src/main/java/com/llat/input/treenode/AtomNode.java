package com.llat.input.treenode;

/**
 *
 */
public class AtomNode extends WffNode {

    public AtomNode(WffNode _left, WffNode _right) {
        super(NodeType.ATOM, _left, _right);
    }
}
