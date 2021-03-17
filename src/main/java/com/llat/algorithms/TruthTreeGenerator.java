package com.llat.algorithms;

import com.llat.models.treenode.*;

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
        this.buildTreeHelper(ttn);
        System.out.println(print(ttn));
        return ttn;
    }

    /**
     * https://www.baeldung.com/java-print-binary-tree-diagram
     * @param root
     */
    public String print(TruthTree root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root);

        String pointerRight = "└──";
        String pointerLeft = (root.getRight() != null) ? "├──" : "└──";

        printHelper(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
        printHelper(sb, "", pointerRight, root.getRight(), false);

        return sb.toString();
    }

    /**
     * https://www.baeldung.com/java-print-binary-tree-diagram
     * @param sb
     * @param padding
     * @param pointer
     * @param node
     * @param hasRightSibling
     */
    private void printHelper(StringBuilder sb, String padding, String pointer, TruthTree node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node);

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            printHelper(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            printHelper(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
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
            System.out.println("Heap:");
            for (TruthTree tt : queue) {
                System.out.println(tt);
            }
            TruthTree tree = queue.poll();
            leaves = this.getLeaves(tree);
            // If the node is not a simple negation (~A), negate it.
            if (tree.getWff().isNegation() && !tree.getWff().getChild(0).isAtom()) {
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
        // Create a new node to negate the lhs and branch.
        NegNode neg = new NegNode();
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
        WffTree child = neg.getWff().getChild(0);
        WffTree negatedAtom;
        // Double negations are simple - just remove the negations altogether.
        if (child.isNegation()) {
            // Add to all leaves in this tree.
            TruthTree doubleNeg = new TruthTree(child.getChild(0));
            for (TruthTree l : leaves) {
                l.addCenter(doubleNeg);
                q.add(l);
            }
        } else {
            // | turns to &, & turns to |, -> turns to ~A & B...
            negatedAtom = this.getNegatedNode(child);
            // Create the negation nodes for the children.
            NegNode n1 = new NegNode("~");
            NegNode n2 = new NegNode("~");
            NegNode n3 = new NegNode("~");

            // Add the two wffs that are going to be flipped to the negations.
            n2.addChild(child.getChild(1));
            // If we're negating an implication, it stacks a double negated A and ~B.
            if (child.isImp()) {
                n3.addChild(child.getChild(0));
                n1.addChild(n3);
            } else {
                n1.addChild(child.getChild(0));
            }

            negatedAtom.addChild(n1);
            negatedAtom.addChild(n2);
            TruthTree negatedTruthTree = new TruthTree(negatedAtom);
            for (TruthTree l : leaves) {
                l.addCenter(negatedTruthTree);
                q.add(l);
            }
//            neg.addCenter(negatedTruthTree);
//            // TODO fix biconditional
//            q.add(negatedTruthTree);
        }
    }

    /**
     *
     * @param truthTree
     * @return
     */
    private LinkedList<TruthTree> getLeaves(TruthTree truthTree) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        this.getLeavesHelper(truthTree, leaves);
        return leaves;
    }

    /**
     *
     * @param truthTree
     * @param leaves
     * @return
     */
    private void getLeavesHelper(TruthTree truthTree, LinkedList<TruthTree> leaves) {
        // If both left and right nodes are null then it's a leaf by def.
        if (truthTree.getLeft() == null && truthTree.getRight() == null) {
            leaves.add(truthTree);
        }

        if (truthTree.getLeft() != null)
            getLeavesHelper(truthTree.getLeft(), leaves);

        if (truthTree.getRight() != null)
            getLeavesHelper(truthTree.getRight(), leaves);
    }

    /**
     * Returns the negated version of the provided tree type.
     *
     * @param tree
     * @return
     */
    private WffTree getNegatedNode(WffTree tree) {
        if (tree.isOr() || tree.isImp()) {
            return new AndNode("&");
        } else if (tree.isAnd()) {
            return new OrNode("|");
        }

        throw new IllegalArgumentException("Cannot negated node of type " + tree);
    }

    /**
     *
     */
    private static class TruthTree implements Comparable<TruthTree> {

        /** */
        private WffTree node;

        /** */
        private TruthTree left;

        /** */
        private TruthTree right;

        /** */
        private int value;

        public TruthTree(WffTree _node) {
            // This is kind of ugly, I know...
            this.node = _node;
            if (_node.isAtom()) {
                this.value = 0;
            } else if (_node.isNegation()) {
                this.value = 1;
            } else if (_node.isAnd()) {
                this.value = 2;
            } else if (_node.isOr()) {
                this.value = 3;
            } else if (_node.isImp()) {
                this.value = 4;
            } else if (_node.isBicond()) {
                this.value = 5;
            } else {
                this.value = 6;
            }
        }

        @Override
        public int compareTo(TruthTree o) {
            System.out.printf("Comparing %d to %d\n",this.value, o.value );
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
            return this.getWff().getStringRep();
        }
    }
}
