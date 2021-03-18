package com.llat.algorithms.propositional;

import com.llat.models.treenode.*;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * TODO Document
 */
public final class PropositionalTruthTreeGenerator {

    /**
     * TODO Document
     */
    private WffTree tree;

    public PropositionalTruthTreeGenerator(WffTree _tree) {
        this.tree = _tree;
    }

    /**
     * TODO Document
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
     * TODO Document
     *
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
     * TODO Document
     *
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
     * TODO document
     *
     * @param _node
     */
    private void buildTreeHelper(TruthTree _node) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        queue.add(_node);

        while (!queue.isEmpty()) {
            TruthTree tree = queue.poll();
            leaves = this.getLeaves(tree);

            if (tree.getWff().isNegation() && tree.getWff().getChild(0).isBicond()) {
                // We handle negations differently since they're harder.
                branchNegationBiconditional(tree, queue);
            } else if (tree.getWff().isNegation() && !tree.getWff().getChild(0).isAtom()) {
                // If the node is not a simple negation (~A), negate it.
                distributeNegation(tree, leaves, queue);
            } else if (tree.getWff().isAtom()) {
                // Do nothing...
            } else if (tree.getWff().isAnd()) {
                stackConjunction(tree, leaves, queue);
            } else if (tree.getWff().isOr()) {
                branchDisjunction(tree, leaves, queue);
            } else if (tree.getWff().isImp()) {
                branchImplication(tree, leaves, queue);
            } else if (tree.getWff().isBicond()) {
                branchBiconditional(tree, leaves, queue);
            }
        }
    }

    /**
     * Stacks a conjunction node. The stack works as follows:
     *
     * If we have (A & B), then
     *
     * A
     * B
     *
     * is the resulting stack. All stacks should be added to the leaves.
     *
     * @param _conj - Conjunction node.
     * @param _leaves - list of leaves.
     * @param _queue - priority queue of nodes left to process.
     */
    private void stackConjunction(TruthTree _conj, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_conj.getWff() instanceof AndNode)) {
            throw new IllegalArgumentException("Error: conjunction expects conjunction node but got " + _conj.getClass());
        }

        // We need to stack on the leaf ONLY.
        for (TruthTree leaf : _leaves) {
            leaf.addCenter(new TruthTree(_conj.getWff().getChild(0)));
            leaf.getCenter().addCenter(new TruthTree(_conj.getWff().getChild(1)));
            _queue.add(leaf.getCenter());
            _queue.add(leaf.getCenter().getCenter());
        }
    }

    /**
     * Branches a disjunction node. The disjunction works as follows:
     *
     * If we have (A | B), then
     *
     * A OR B
     *
     * is the resulting branch. If there are multiple leaves in the current TruthTree
     * node, then this branch is applied to all of them.
     *
     * @param _disj - Disjunction node.
     * @param _leaves - list of leaves.
     * @param _queue - priority queue of nodes left to process.
     */
    private void branchDisjunction(TruthTree _disj, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        TruthTree l = new TruthTree(_disj.getWff().getChild(0));
        TruthTree r = new TruthTree(_disj.getWff().getChild(1));

        for (TruthTree leaf : _leaves) {
            leaf.addLeft(l);
            leaf.addRight(r);
            _queue.add(leaf.getLeft());
            _queue.add(leaf.getRight());
        }
    }

    /**
     * Branches an implication node in the truth tree.
     *
     * The implication branches as follows:
     *
     * If we have (A -> B), then
     * ~A OR B
     *
     * is the resulting branch. If there are multiple leaves in the current TruthTree
     * node, then this branch is applied to all of them.
     *
     * @param _imp - Implication node.
     * @param _leaves - list of leaves.
     * @param _queue - priority queue of nodes left to process.
     */
    private void branchImplication(TruthTree _imp, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        // Create a new node to negate the lhs and branch.
        NegNode neg = new NegNode();
        neg.addChild(_imp.getWff().getChild(0));
        TruthTree l = new TruthTree(neg);
        TruthTree r = new TruthTree(_imp.getWff().getChild(1));

        for (TruthTree leaf : _leaves) {
            leaf.addLeft(l);
            leaf.addRight(r);
            _queue.add(leaf.getLeft());
            _queue.add(leaf.getRight());
        }
    }

    /**
     * Branches a biconditional node in the form of
     * (A <-> B)
     *
     * The negation is applied such that it branches, with the lhs containing
     * A, B stacked, and the rhs contains ~A, ~B.
     *
     * @param _bicond - TruthTree node such that its WffTree instance is a BicondNode.
     * @param _leaves -
     * @param _queue - Priority queue to add the four constructed children to.
     */
    private void branchBiconditional(TruthTree _bicond, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_bicond.getWff() instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch biconditional expects biconditional node but got " + _bicond.getClass());
        }

        WffTree bicondNode = _bicond.getWff();

        // Left subtree.
        _bicond.addLeft(new TruthTree(bicondNode.getChild(0)));
        _bicond.getLeft().addCenter(new TruthTree(bicondNode.getChild(1)));

        // Right subtree.
        NegNode neg1 = new NegNode();
        neg1.addChild(bicondNode.getChild(0));
        _bicond.addRight(new TruthTree(neg1));

        NegNode neg2 = new NegNode();
        neg2.addChild(bicondNode.getChild(1));
        _bicond.getRight().addCenter(new TruthTree(neg2));

        // Add them to the queue.
        _queue.add(_bicond.getLeft());
        _queue.add(_bicond.getLeft().getCenter());
        _queue.add(_bicond.getRight());
        _queue.add(_bicond.getRight().getCenter());
    }

    /**
     * Distributes a negation to a biconditional node in the form of
     * ~(A <-> B)
     *
     * The negation is applied such that it branches, with the lhs containing
     * ~A, B stacked, and the rhs contains ~B, A.
     *
     * @param _negRoot - TruthTree node such that its WffTree instance is a negated node and its child is a bicond.
     * @param _queue - Priority queue to add the four constructed children to.
     */
    private void branchNegationBiconditional(TruthTree _negRoot, PriorityQueue<TruthTree> _queue) {
        if (!(_negRoot.getWff().getChild(0) instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch negation biconditional expects biconditional node but got " + _negRoot.getClass());
        }

        WffTree w = _negRoot.getWff().getChild(0);
        // Left subtree.
        _negRoot.addLeft(new TruthTree(w.getChild(0)));
        NegNode neg = new NegNode();
        neg.addChild(w.getChild(1));
        _negRoot.getLeft().addCenter(new TruthTree(neg));

        // Right subtree.
        _negRoot.addRight(new TruthTree(w.getChild(1)));
        NegNode neg2 = new NegNode();
        neg2.addChild(w.getChild(0));
        _negRoot.getRight().addCenter(new TruthTree(neg2));

        // Add them to the queue.
        System.out.println(_negRoot.getLeft());
        System.out.println(_negRoot.getLeft().getCenter());
        System.out.println(_negRoot.getRight());
        System.out.println(_negRoot.getRight().getCenter());
        _queue.add(_negRoot.getLeft());
        _queue.add(_negRoot.getLeft().getCenter());
        _queue.add(_negRoot.getRight());
        _queue.add(_negRoot.getRight().getCenter());
    }

    /**
     * Distributes a negation into the node.
     *
     * Negations are applied using De'Morgan's laws as follows:
     * ~(A | B) == (~A & ~B)
     * ~(A & B) == (~A | ~B)
     * ~(A -> B) == (~~A & ~B)
     *
     * Each of these equivalences on the rhs of the rule *is* applied when building
     * the tree. This means that there are some redundant steps added. These ought
     * be noted in the final version if time permits.
     *
     * @param _negRoot - negation node itself.
     * @param _leaves - linked list of leave nodes for this current TruthTree node.
     *                 This is computed before this method is called (and can
     *                 be abstracted to this method...)
     * @param _queue - priority queue of nodes - each child created in this method
     *                is added to this priority queue.
     */
    private void distributeNegation(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        WffTree child = _negRoot.getWff().getChild(0);
        WffTree negatedAtom;
        TruthTree enqueuedTTNode;

        // Double negations are simple - just remove the negations altogether.
        if (child.isNegation()) {
            // Add to all leaves in this tree.
            enqueuedTTNode = new TruthTree(child.getChild(0));
        } else {
            negatedAtom = this.getNegatedNode(child);

            // Create the negation nodes for the children.
            NegNode n1 = new NegNode();
            NegNode n2 = new NegNode();

            // Add the two wffs that are going to be flipped to the negations.
            // If we're negating an implication, it stacks a double negated A and ~B.
            if (child.isImp()) {
                NegNode n3 = new NegNode();
                n3.addChild(child.getChild(0));
                n1.addChild(n3);
            } else {
                n1.addChild(child.getChild(0));
            }

            n2.addChild(child.getChild(1));
            negatedAtom.addChild(n1);
            negatedAtom.addChild(n2);
            enqueuedTTNode = new TruthTree(negatedAtom);
        }

        // Now apply the new rule to every branch in this subTT.
        for (TruthTree leaf : _leaves) {
            leaf.addCenter(enqueuedTTNode);
            if (!enqueuedTTNode.getWff().isAtom()) {
                _queue.add(enqueuedTTNode);
            }
        }
    }

    /**
     * Computes a list of leaves for the current TruthTree. A leaf is a
     * node in the tree that contains no children (in other words, the
     * left and right pointers are null). We compute these to know which
     * nodes we have to add to when branching or stacking.
     *
     * @param _truthTree - TruthTree node to recursively search for leaves.
     *
     * @return LinkedList<TruthTree> of leaf nodes.
     */
    private LinkedList<TruthTree> getLeaves(TruthTree _truthTree) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        this.getLeavesHelper(_truthTree, leaves);
        return leaves;
    }

    /**
     * TODO Document
     *
     * @param _truthTree
     * @param _leaves
     * @return
     */
    private void getLeavesHelper(TruthTree _truthTree, LinkedList<TruthTree> _leaves) {
        // If both left and right nodes are null then it's a leaf by def.
        if (_truthTree.getLeft() == null && _truthTree.getRight() == null) {
            _leaves.add(_truthTree);
        }

        if (_truthTree.getLeft() != null) {
            getLeavesHelper(_truthTree.getLeft(), _leaves);
        }

        if (_truthTree.getRight() != null) {
            getLeavesHelper(_truthTree.getRight(), _leaves);
        }
    }

    /**
     * Returns the negated version of the provided tree type.
     *
     * The disjunction (OR) and implication operators return the ampersand (AND),
     * whereas the ampersand (AND) returns a disjunction (OR).
     *
     * @param tree - WffTree node to negate.
     * @return WffTree node instance of the corresponding negative type.
     * @throws IllegalArgumentException if tree is not an OrNode, ImpNode, or AndNode.
     */
    private WffTree getNegatedNode(WffTree tree) {
        if (tree.isOr() || tree.isImp()) {
            return new AndNode("&");
        } else if (tree.isAnd()) {
            return new OrNode("|");
        }

        throw new IllegalArgumentException("Cannot get negated node of type " + tree);
    }

    /**
     * Static class for constructing a TruthTree.
     *
     * TruthTrees contain three instance objects: a WffTree node, which is its "value",
     * and two children (acting as a binary tree): a left and right pointer to TTs.
     * There is one extra integer, corresponding to a value. This value indicates the
     * precedence of the operator, in increasing order as follows:
     *
     * ATOM < NEG < AND < OR < IMP < BICOND < IDENTITY
     *
     * These nodes are stored in a minheap (priority queue), and are ordered such that
     * the truth tree operations are performed in the aforementioned order.
     *
     * A TruthTree can have either one or two children (same as a binary tree), with the
     * exception that a tree with only one child is a "stacked" subtree, and should be
     * referenced with getCenter() and addCenter(...) calls. So, nodes with two children
     * (left and right) should not use this; it is used for the conjunction operator.
     */
    private static class TruthTree implements Comparable<TruthTree> {

        /** WffTree "value" for the TruthTree. */
        private WffTree node;

        /** Left pointer. */
        private TruthTree left;

        /** Right pointer. */
        private TruthTree right;

        /** Order of precedence for this node (as described above). */
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
        public int compareTo(TruthTree _o) {
            return this.value - _o.value;
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
