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
        TruthTree ttn = new TruthTree(this.tree.getChild(0), null);
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

        String pointerRight = "└── ";
        String pointerLeft = (root.getRight() != null) ? "├── " : "└── ";

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
            String pointerRight = "└── ";
            String pointerLeft = (node.getRight() != null) ? "├── " : "└── ";

            printHelper(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            printHelper(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }

    /**
     *
     * @param _root
     */
    private void printPaths(TruthTree _root) {
        LinkedList<TruthTree> leaves = this.getLeaves(_root);
        System.out.println(leaves);
        printPathsHelper(leaves);
    }

    /**
     *
     * @param _leaves
     */
    private void printPathsHelper(LinkedList<TruthTree> _leaves) {
        for (TruthTree leaf : _leaves) {
            System.out.println(leaf);
            TruthTree l = leaf;
            while (l != null) {
                System.out.print(l + " -> ");
                l = l.getParent();
            }
            System.out.println();
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
                branchNegationBiconditional(tree, leaves, queue);
            } else if (tree.getWff().isNegation() && !tree.getWff().getChild(0).isAtom()) {
                // If the node is not a simple negation (~A), negate it.
                distributeNegation(tree, leaves, queue);
            } else if (tree.getWff().isAnd()) {
                stackConjunction(tree, leaves, queue);
            } else if (tree.getWff().isOr()) {
                branchDisjunction(tree, leaves, queue);
            } else if (tree.getWff().isImp()) {
                branchImplication(tree, leaves, queue);
            } else if (tree.getWff().isBicond()) {
                branchBiconditional(tree, leaves, queue);
            }

            leaves = this.getLeaves(_node);
            this.computeClosedBranches(leaves);
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
            leaf.addCenter(new TruthTree(_conj.getWff().getChild(0), leaf));
            leaf.getCenter().addCenter(new TruthTree(_conj.getWff().getChild(1), leaf.getCenter()));
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
        for (TruthTree leaf : _leaves) {
            TruthTree l = new TruthTree(_disj.getWff().getChild(0), leaf);
            TruthTree r = new TruthTree(_disj.getWff().getChild(1), leaf);
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
        for (TruthTree leaf : _leaves) {
            NegNode neg = new NegNode();
            neg.addChild(_imp.getWff().getChild(0));
            TruthTree l = new TruthTree(neg, leaf);
            TruthTree r = new TruthTree(_imp.getWff().getChild(1), leaf);
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
     * @param _leaves - list of leaf nodes.
     * @param _queue - Priority queue to add the four constructed children to.
     */
    private void branchBiconditional(TruthTree _bicond, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_bicond.getWff() instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch biconditional expects biconditional node but got " + _bicond.getClass());
        }

        // TODO optimize this to not include de'morgans laws.
        WffTree bicondNode = _bicond.getWff();
        for (TruthTree leaf : _leaves) {
            // Left subtree.
            leaf.addLeft(new TruthTree(bicondNode.getChild(0), leaf));
            leaf.getLeft().addCenter(new TruthTree(bicondNode.getChild(1), leaf.getLeft()));

            // Right subtree.
            NegNode neg1 = new NegNode();
            neg1.addChild(bicondNode.getChild(0));
            leaf.addRight(new TruthTree(neg1, leaf));

            NegNode neg2 = new NegNode();
            neg2.addChild(bicondNode.getChild(1));
            leaf.getRight().addCenter(new TruthTree(neg2, leaf.getRight()));

            // Add them to the queue.
            _queue.add(leaf.getLeft());
            _queue.add(leaf.getLeft().getCenter());
            _queue.add(leaf.getRight());
            _queue.add(leaf.getRight().getCenter());
        }
    }

    /**
     * Distributes a negation to a biconditional node in the form of
     * ~(A <-> B)
     *
     * The negation is applied such that it branches, with the lhs containing
     * ~A, B stacked, and the rhs contains ~B, A.
     *
     * @param _negRoot - TruthTree node such that its WffTree instance is a negated node and its child is a bicond.
     * @param _leaves - list of leaf nodes.
     * @param _queue - Priority queue to add the four constructed children to.
     */
    private void branchNegationBiconditional(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_negRoot.getWff().getChild(0) instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch negation biconditional expects biconditional node but got " + _negRoot.getClass());
        }

        // TODO optimize this to remove demorgan's law rules
        WffTree bicondNode = _negRoot.getWff().getChild(0);
        for (TruthTree leaf : _leaves) {
            // Left subtree.
            leaf.addLeft(new TruthTree(bicondNode.getChild(0), leaf));
            NegNode neg = new NegNode();
            neg.addChild(bicondNode.getChild(1));
            leaf.getLeft().addCenter(new TruthTree(neg, leaf.getLeft()));

            // Right subtree.
            leaf.addRight(new TruthTree(bicondNode.getChild(1), leaf));
            NegNode neg2 = new NegNode();
            neg2.addChild(bicondNode.getChild(0));
            leaf.getRight().addCenter(new TruthTree(neg2, leaf.getRight()));

            // Add them to the queue.
            _queue.add(leaf.getLeft());
            _queue.add(leaf.getLeft().getCenter());
            _queue.add(leaf.getRight());
            _queue.add(leaf.getRight().getCenter());
        }
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
            // Now apply the new rule to every branch in this subTT.
            for (TruthTree leaf : _leaves) {
                enqueuedTTNode = new TruthTree(child.getChild(0), leaf);
                leaf.addCenter(enqueuedTTNode);
                if (!enqueuedTTNode.getWff().isAtom()) {
                    _queue.add(enqueuedTTNode);
                }
            }
        } else {
            negatedAtom = this.getNegatedBinaryNode(child);

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
            enqueuedTTNode = new TruthTree(negatedAtom, _negRoot);

            // Call the respective branch/stack function.
            // Removes De'Morgan's laws.
            if (child.isAnd()) {
                this.branchDisjunction(enqueuedTTNode, _leaves, _queue);
            } else {
                this.stackConjunction(enqueuedTTNode, _leaves, _queue);
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
     * Returns the negated version of the provided binary node WffTree type.
     * <p>
     * The disjunction (OR) and implication operators return the ampersand (AND),
     * whereas the ampersand (AND) returns a disjunction (OR).
     *
     * The difference between this and getNegatedNode(...) is that this node returns
     * the corresponding subtype instead of a generic WffTree or NegNode. In other words,
     * this method returns an AndNode or an OrNode.
     *
     * @param _tree - WffTree node to negate.
     *
     * @return WffTree node instance of the corresponding negative type.
     *
     * @throws IllegalArgumentException if tree is not an OrNode, ImpNode, or AndNode.
     */
    private WffTree getNegatedBinaryNode(WffTree _tree) {
        if (_tree.isOr() || _tree.isImp()) {
            return new AndNode();
        } else if (_tree.isAnd()) {
            return new OrNode();
        }

        throw new IllegalArgumentException("Cannot get negated node of type " + _tree);
    }

    /**
     * Computes the negated version of any arbitrary WffTree node. This performs
     * a "simple negation" only, where simple is defined as follows:
     *
     * If our input is a wff P, then we return ~P. Similarly,
     * If our input is a wff ~P, then we return P.
     *
     * @param _wff - WffTree object to negate.
     *
     * @return negated version of WffTree.
     */
    private WffTree getNegatedNode(WffTree _wff) {
        WffTree negWff;
        if (_wff.isNegation()) {
            negWff = _wff.getChild(0);
        } else {
            NegNode neg = new NegNode();
            neg.addChild(_wff);
            negWff = neg;
        }

        return negWff;
    }

    /**
     * TODO Document later
     *
     * @param _leaves
     */
    private void computeClosedBranches(LinkedList<TruthTree> _leaves) {
        for (TruthTree leaf : _leaves) {
            // Only check to see if a branch is closed above if the current leaf
            // is still open.
            if (!leaf.isClosed()) {
                TruthTree curr = leaf;
                TruthTree negTT = new TruthTree(getNegatedNode(leaf.getWff()), null);
                while (curr != null) {
                    if (curr.equals(negTT)) {
                        leaf.setClosed(true);
                        break;
                    }
                    curr = curr.getParent();
                }
            }
        }
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

        /** Pointer to the parent node for reverse traversal. */
        private TruthTree parent;

        /** Left pointer. */
        private TruthTree left;

        /** Right pointer. */
        private TruthTree right;

        /** Order of precedence for this node (as described above). */
        private int value;

        /** */
        private boolean closed;

        public TruthTree(WffTree _node, TruthTree _parent) {
            // This is kind of ugly, I know...
            this.node = _node;
            this.parent = _parent;
            if (_node.isAtom()) {
                this.value = 0;
            } else if (_node.isDoubleNegation()) {
                // Double negations have to have a higher priority.
                this.value = 1;
            } else if (_node.isNegation() && !_node.isNegAnd() && !_node.isNegImp() && !_node.isNegOr()) {
                this.value = 2;
            } else if (_node.isAnd()) {
                this.value = 3;
            } else if (_node.isNegOr() || _node.isNegImp()) {
                this.value = 4;
            } else if (_node.isOr()) {
                this.value = 5;
            } else if (_node.isNegAnd()) {
                this.value = 6;
            } else if (_node.isImp()) {
                this.value = 7;
            } else if (_node.isBicond()) {
                this.value = 8;
            } else {
                this.value = 9;
            }
        }

        @Override
        public boolean equals(Object obj) {
            TruthTree o = (TruthTree) obj;
            return this.getWff().getStringRep().equals(o.getWff().getStringRep());
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

        public TruthTree getParent() {
            return this.parent;
        }

        public TruthTree getCenter() {
            if (this.right != null) {
                throw new IllegalCallerException("Cannot return center if right child is null.");
            }

            return this.left;
        }

        public boolean isClosed() {
            return this.closed;
        }

        public void setClosed(boolean _closed) {
            this.closed = _closed;
        }

        @Override
        public String toString() {
            String leafSignal = "";
            if (this.isLeafNode()) {
                leafSignal = this.closed ? "X" : "\u2713";
            }
            return this.getWff().getStringRep() + " " + leafSignal;
        }
    }
}
