package com.llat.input.treenode;

import java.util.LinkedList;

/**
 *
 */
public class PredicateNode extends WffTree {

    /** */
    private final String PREDICATE_LETTER;

    /** */
    private final LinkedList<String> PARAMS;

    public PredicateNode(String _predicateLetter, LinkedList<String> _params, WffTree _left, WffTree _right) {
        super(NodeType.PREDICATE, _left, _right);
        this.PREDICATE_LETTER = _predicateLetter;
        this.PARAMS = _params;
    }

    public PredicateNode(String _predicateLetter, LinkedList<String> _params) {
        this(_predicateLetter, _params, null, null);
    }

    public String getPredicateLetter() {
        return this.PREDICATE_LETTER;
    }

    public LinkedList<String> getParameters() {
        return this.PARAMS;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
