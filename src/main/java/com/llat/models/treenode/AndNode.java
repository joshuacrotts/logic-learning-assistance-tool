package com.llat.models.treenode;

/**
 * Conjunction node.
 *
 * @author Joshua Crotts
 * @date 2/20/2021
 */
public class AndNode extends WffTree {

    public AndNode(String _symbol) {
        super(_symbol, NodeType.AND);
    }

}
