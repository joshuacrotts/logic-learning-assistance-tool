package com.llat.algorithms.predicate;

import com.llat.models.treenode.WffTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class GroundSentenceDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    public GroundSentenceDeterminer(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public boolean get() {
        Queue<WffTree> queue = new LinkedList<>();
        queue.add(this.wffTree);

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
