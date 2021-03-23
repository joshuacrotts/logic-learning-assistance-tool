package com.llat.models.treenode;

import java.util.LinkedList;

/**
 *
 */
public class PredicateNode extends WffTree {

    /**
     * Symbol to define the predicate letter.
     */
    private final String PREDICATE_LETTER;

    /**
     * WffTree children for this predicate node - each child should be a constant or variable node.
     */
    private final LinkedList<WffTree> PARAMS;

    public PredicateNode(String _predicateLetter, LinkedList<WffTree> _params) {
        super(NodeType.PREDICATE);
        this.PREDICATE_LETTER = _predicateLetter;
        this.PARAMS = _params;

        for (WffTree tree : this.PARAMS) {
            if (tree != null) {
                super.addChild(tree);
            }
        }
    }

    public PredicateNode(String _predicateLetter) {
        super(NodeType.PREDICATE);
        this.PREDICATE_LETTER = _predicateLetter;
        this.PARAMS = new LinkedList<>();
    }

    @Override
    public WffTree copy() {
        PredicateNode predicateCopy = new PredicateNode(this.PREDICATE_LETTER);
        for (WffTree ch : this.getChildren()) {
            predicateCopy.addChild(ch.copy());
        }

        return predicateCopy;
    }

    @Override
    public String getStringRep() {
        return this.PREDICATE_LETTER + this.getChild(0).getStringRep();
    }


    public String getPredicateLetter() {
        return this.PREDICATE_LETTER;
    }

    public LinkedList<WffTree> getParameters() {
        return this.PARAMS;
    }

    public int getArity() {
        return this.PARAMS.size();
    }

    @Override
    public String toString() {
        return this.getNodeType() + ": " + this.PREDICATE_LETTER;
    }
}
