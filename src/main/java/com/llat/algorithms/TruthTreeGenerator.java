package com.llat.algorithms;

import com.llat.models.treenode.*;
import org.antlr.v4.runtime.tree.Tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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
        this.buildTreeHelper(ttn);
        this.print(ttn,0);
        return ttn;
    }

    /**
     *
     * @param _tree
     */
    public void print(TruthTree _tree, int x) {
        if (_tree == null) {
            return;
        }

        for (int i = 0; i < x; i++) {
            System.out.print("\t");
        }

        System.out.println(_tree);
        print(_tree.getLeft(), x + 2);
        print(_tree.getRight(), x + 2);
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
            leaves = this.getLeaves(queue);
            TruthTree tree = queue.poll();
            if (tree.getWff().isNegation() && !tree.getWff().getChild(0).isAtom()) {
                // Do something if the tree has a negation and it's not a
                // simple neg (~A)
                // Flip everything
                distributeNegation(tree, leaves, queue);
            } else if (tree.getWff().isAtom()) {
                // Do nothing...
            } else if (tree.getWff().isAnd()) {
                stackConjunction(tree, queue);
            } else if (tree.getWff().isOr()) {
                branchDisjunction(tree, leaves, queue);
            } else if (tree.getWff().isImp()) {
                branchImplication(tree, leaves, queue);
            } else if (tree.getWff().isNegation() && tree.getWff().getChild(0).isBicond()) {
                branchNegationBiconditional(tree, queue);
            }
        }
    }

    /**
     *
     */
    private void stackConjunction(TruthTree conj, PriorityQueue<TruthTree> q) {
        if (!(conj.getWff() instanceof AndNode)) {
            throw new IllegalArgumentException("Error: conjunction expects conjunction node but got " + conj.getClass());
        }

        // TODO comment later...
        conj.addCenter(new TruthTree(conj.getWff().getChild(0)));
        conj.getCenter().addCenter(new TruthTree(conj.getWff().getChild(1)));
        q.add(conj.getCenter());
        q.add(conj.getCenter().getCenter());
    }

    /**
     *
     */
    private void branchDisjunction(TruthTree disj, LinkedList<TruthTree> leaves, PriorityQueue<TruthTree> q) {
        TruthTree l = new TruthTree(disj.getWff().getChild(0));
        TruthTree r = new TruthTree(disj.getWff().getChild(1));

        for (TruthTree t : leaves) {
            t.addLeft(l);
            t.addRight(r);
            q.add(t.getLeft());
            q.add(t.getRight());
        }
    }

    /**
     *
     */
    private void branchImplication(TruthTree imp, LinkedList<TruthTree> leaves, PriorityQueue<TruthTree> q) {
        NegNode neg = new NegNode("~");
        neg.addChild(imp.getWff().getChild(0));

        TruthTree l = new TruthTree(neg);
        TruthTree r = new TruthTree(imp.getWff().getChild(1));

        for (TruthTree t : leaves) {
            t.addLeft(l);
            t.addRight(r);
            q.add(t.getLeft());
            q.add(t.getRight());
        }
    }

    /**
     *
     */
    private void branchNegationBiconditional(TruthTree bicond, PriorityQueue<TruthTree> q) {
        if (!(bicond.getWff().getChild(0) instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch negation biconditional expects biconditional node but got " + bicond.getClass());
        }
        WffTree w = bicond.getWff().getChild(0);
        // Left subtree.
        bicond.addLeft(new TruthTree(w.getChild(0)));
        NegNode neg = new NegNode("~");
        neg.addChild(w.getChild(1));
        bicond.getLeft().addCenter(new TruthTree(neg));

        // Right subtree
        bicond.addRight(new TruthTree(w.getChild(1)));
        NegNode neg2 = new NegNode("~");
        neg2.addChild(w.getChild(0));
        bicond.getRight().addCenter(new TruthTree(neg2));

        // Add them to the queue.
        q.add(bicond.getLeft());
        q.add(bicond.getLeft().getLeft());
        q.add(bicond.getRight());
        q.add(bicond.getRight().getLeft());
    }

    /**
     *
     * @param neg - negation node itself.
     */
    private void distributeNegation(TruthTree neg, LinkedList<TruthTree> leaves, PriorityQueue<TruthTree> q) {
        // ~(A -> B) = ~~A & ~B
        // ~(A & B) = (~A V ~B)
        // ~(A V B) = (~A & ~B)
        // ~~A = A
        WffTree child = neg.getWff();
        if (child.isNegation()) {
            TruthTree doubleNeg = new TruthTree(child.getChild(0).getChild(0));
            neg.addCenter(doubleNeg);
            q.add(doubleNeg);
        }
    }

    /**
     *
     * @param queue
     * @return
     */
    private LinkedList<TruthTree> getLeaves(PriorityQueue<TruthTree> queue) {
        LinkedList<TruthTree> leaves = new LinkedList<>();

        for (TruthTree tt : queue) {
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

        /** */
        private WffTree node;

        /** */
        private TruthTree left;

        /** */
        private TruthTree right;

        /** */
        private int value = -1;

        public TruthTree(WffTree _node) {
            // This is kind of ugly, I know...
            this.node = _node;
            if (_node.isNegation()) {
                this.value = 5;
            } else if (_node.isAnd()) {
                this.value = 4;
            } else if (_node.isOr()) {
                this.value = 3;
            } else if (_node.isImp()) {
                this.value = 2;
            } else if (_node.isBicond()) {
                this.value = 1;
            } else {
                this.value = 0;
            }
        }

//        /**
//         *
//         * @param _node
//         */
//        public void stack(WffTree _node) {
//            this.left = new TruthTree(_node);
//        }
//
//        /**
//         *
//         * @param _left
//         * @param _right
//         */
//        public void branch(WffTree _left, WffTree _right) {
//            this.left = new TruthTree(_left);
//            this.right = new TruthTree(_right);
//        }

        @Override
        public int compareTo(TruthTree o) {
            return this.value - o.value;
        }

        public boolean isLeafNode() {
            return this.left == null && this.right == null;
        }

        public WffTree getWff() {
            return this.node;
        }

        public void addLeft(TruthTree _left) {
            this.left = _left;
        }

        public void addRight(TruthTree _right) {
            this.right = _right;
        }

        public void addCenter(TruthTree _c) {
            if (this.right != null) {
                throw new IllegalArgumentException("Cannot add truth tree to right child - add it to the left only.");
            }

            this.left = _c;
            this.right = null;
        }

        public TruthTree getLeft() {
            return this.left;
        }

        public TruthTree getRight() {
            return this.right;
        }

        public TruthTree getCenter() {
            if (this.right != null) {
                throw new IllegalCallerException("Cannot return center if right child is null.");
            }

            return this.left;
        }

        @Override
        public String toString() {
            // Temporary...
            this.node.printSyntaxTree();
            return "";
        }
    }
}
