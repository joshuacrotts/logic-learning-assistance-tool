package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.WffTree;

import java.util.LinkedList;
import java.util.Queue;

public class ClosedTreeDeterminer {

    /**
     *
     */
    private TruthTree tree;

    public ClosedTreeDeterminer(TruthTree _tree) {
        this.tree = _tree;
    }

    public ClosedTreeDeterminer(WffTree _tree) {
        if (_tree.isPropositionalWff()) {
            this.tree = new PropositionalTruthTreeGenerator(_tree).getTruthTree();
        } else {
            this.tree = new PredicateTruthTreeGenerator(_tree).getTruthTree();
        }
    }

    /**
     * @return
     */
    public boolean hasAllClosed() {
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

    /**
     * @return
     */
    public boolean hasSomeClosed() {
        Queue<TruthTree> queue = new LinkedList<>();
        queue.add(this.tree);

        while (!queue.isEmpty()) {
            TruthTree t = queue.poll();
            if (t.isLeafNode() && t.isClosed()) {
                return true;
            }
            if (t.getLeft() != null) {
                queue.add(t.getLeft());
            }
            if (t.getRight() != null) {
                queue.add(t.getRight());
            }
        }

        return false;
    }
}
