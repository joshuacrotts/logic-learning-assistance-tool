package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class GroundSentenceDetector {

    /**
     *
     * @param tree
     * @return
     */
    public static boolean get(WffTree tree) {
        Queue<WffTree> queue = new LinkedList<>();
        queue.add(tree);

        while (!queue.isEmpty()) {
            WffTree t = queue.poll();
            for (WffTree ch : t.getChildren()) {
                if (ch.isVariable()) {
                    return false;
                }

                queue.add(ch);
            }
        }

        return true;
    }
}
