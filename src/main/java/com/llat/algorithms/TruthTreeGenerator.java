package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 */
public final class TruthTreeGenerator {

    /**
     *
     */
    private WffTree tree;

    public TruthTreeGenerator(WffTree _tree) {
        this.tree = _tree;
    }

    /**
     *
     * @return
     */
    public TruthTree get() {
        TruthTree ttn = new TruthTree(this.tree.getChild(0));
        buildTreeHelper(ttn);
        return ttn;
    }

    /**
     *
     * @param _tree
     */
    public void print(TruthTree _tree) {

    }

    /**
     *
     * @param _node
     */
    private void buildTreeHelper(TruthTree _node) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        queue.add(_node);

        while (!queue.isEmpty()) {
            TruthTree tree = queue.poll();

            //
            // ...
            //

            leaves = this.getLeaves(queue);
        }
    }

    /**
     *
     */
    private void stackConjunction() {

    }

    /**
     *
     */
    private void branchDisjunction() {

    }

    /**
     *
     */
    private void distributeNegation() {

    }

    /**
     *
     * @param queue
     * @return
     */
    private LinkedList<TruthTree> getLeaves(PriorityQueue<TruthTree> queue) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        Iterator<TruthTree> it = queue.iterator();

        while (it.hasNext()) {
            TruthTree tt = it.next();
            if (tt.isLeafNode()) {
                leaves.add(tt);
            }
        }

        return leaves;
    }

    /**
     *
     */
    private class TruthTree implements Comparable<TruthTree> {

        private WffTree node;
        private TruthTree left;
        private TruthTree right;

        public TruthTree(WffTree _node) {
            this.node = _node;
        }

        /**
         *
         * @param _node
         */
        public void stack(WffTree _node) {
            this.left = new TruthTree(_node);
        }

        /**
         *
         * @param _left
         * @param _right
         */
        public void branch(WffTree _left, WffTree _right) {
            this.left = new TruthTree(_left);
            this.right = new TruthTree(_right);
        }

        @Override
        public int compareTo(TruthTree o) {
            if (this.node.isNegation()) {
                return 3;
            } else if (this.node.isAnd()) {
                return 2;
            } else if (this.node.isOr()) {
                return 1;
            } else if (this.node.isImp()) {
                return 0;
            } else if (this.node.isBicond()) {
                return -1;
            }
            return -2;
        }

        public boolean isLeafNode() {
            return this.left == null && this.right == null;
        }

        @Override
        public String toString() {
            // Temporary...
            return node.toString();
        }
    }
}
