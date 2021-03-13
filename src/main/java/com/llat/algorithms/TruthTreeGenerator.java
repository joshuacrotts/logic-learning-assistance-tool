package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

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
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        LinkedList<TruthTree> leaves = new LinkedList<>();
        buildTreeHelper(ttn, queue, leaves);
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
     * @param queue
     * @param leaves
     */
    private void buildTreeHelper(TruthTree _node, PriorityQueue<TruthTree> queue, LinkedList<TruthTree> leaves) {

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
     */
    private class TruthTree implements Comparable<TruthTree> {

        private WffTree node;
        private TruthTree left;
        private TruthTree right;

        public TruthTree(WffTree _node) {
            this.node = _node;
        }

        @Override
        public int compareTo(TruthTree o) {
            return 0;
        }

        @Override
        public String toString() {
            // Temporary...
            return node.toString();
        }
    }
}
