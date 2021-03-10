package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

/**
 * This class defines the algorithm for detecting the main operator of a propositional logic
 * or predicate logic sentence/well-formed formula. It's an extremely simple algorithm, and
 * is almost always going to run in constant time, no matter how big or complicated the
 * expression is thanks to the way the tree is configured.
 */
public final class MainOperatorDetector {

    /**
     * Returns the main operator of a supplied WffTree.
     *
     * @param WffTree tree structure.
     * @return String symbol of main operator.
     */
    public static WffTree get(WffTree tree) {
        if (tree.isRoot()) {
            return get(tree.getChild(0));
        } else if (tree.isBinaryOp() || tree.isQuantifier() || tree.isNegation()) {
            return tree;
        }

        return null;
    }
}
