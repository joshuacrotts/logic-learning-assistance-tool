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
     * Well-formed formula tree to check - also called the root.
     */
    private WffTree wffTree;

    public MainOperatorDetector(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * Returns the main operator of a supplied WffTree.
     *
     * @return String symbol of main operator.
     */
    public WffTree get() {
        return getMainOpHelper(this.wffTree);
    }

    /**
     * Recursive helper function.
     *
     * @param _tree
     * @return
     */
    private WffTree getMainOpHelper(WffTree _tree) {
        if (_tree.isRoot()) {
            return getMainOpHelper(_tree.getChild(0));
        } else if (_tree.isBinaryOp() || _tree.isQuantifier() || _tree.isNegation()) {
            return _tree;
        }

        return null;
    }
}
