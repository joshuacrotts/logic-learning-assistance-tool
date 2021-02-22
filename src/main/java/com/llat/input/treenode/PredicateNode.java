package com.llat.input.treenode;

import java.util.LinkedList;

/**
 *
 */
public class PredicateNode extends WffTree {

    /** */
    private final String PREDICATE_LETTER;

    /** */
    private final LinkedList<WffTree> PARAMS;

    public PredicateNode(String _predicateLetter, LinkedList<WffTree> _params, WffTree _left, WffTree _right) {
        super(NodeType.PREDICATE, _left, _right);
        this.PREDICATE_LETTER = _predicateLetter;
        this.PARAMS = _params;

        for (WffTree tree : this.PARAMS) {
            if (tree != null)
                super.addChild(tree);
        }
    }

    public PredicateNode(String _predicateLetter, LinkedList<WffTree> _params) {
        this(_predicateLetter, _params, null, null);
    }

    public String getPredicateLetter() {
        return this.PREDICATE_LETTER;
    }

    public LinkedList<WffTree> getParameters() {
        return this.PARAMS;
    }

    @Override
    public String toString() {
        return this.getNodeType() + ": " + this.PREDICATE_LETTER;
    }
}
