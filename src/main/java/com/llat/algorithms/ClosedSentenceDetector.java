package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

/**
 *
 */
public class ClosedSentenceDetector {

    /**
     *
     * @param tree
     * @return
     */
    public static boolean get(WffTree tree) {
        return FreeVariableDetector.get(tree).isEmpty() && !GroundSentenceDetector.get(tree);
    }
}
