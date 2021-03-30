package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;

import java.util.LinkedList;
import java.util.Queue;

public class ClosedTreeDeterminer {

    private TruthTree tree;

    public ClosedTreeDeterminer(TruthTree _tree) {
        this.tree = _tree;
    }

    public boolean get() {
        Queue<TruthTree> queue = new LinkedList<>();
        queue.add(this.tree);

        while (!queue.isEmpty()) {
            TruthTree t = queue.poll();
            if (t.isLeafNode() && !t.isClosed()) {
                return false;
            }
            if (t.getLeft() != null) {
                queue.add(t.getLeft());
            }
            if (t.getRight() != null) {
                queue.add(t.getRight());
            }
        }

        return true;
    }
}
