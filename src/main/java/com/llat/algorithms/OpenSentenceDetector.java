package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

/**
 *
 */
public class OpenSentenceDetector {

    /**
     *
     * @param tree
     * @return
     */
    public static boolean get(WffTree tree) {
        return !FreeVariableDetector.get(tree).isEmpty();
    }
}
