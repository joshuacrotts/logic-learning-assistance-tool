package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.models.treenode.*;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 */
public abstract class BaseTruthTreeGenerator {

    /**
     *
     */
    protected WffTree tree;

    public BaseTruthTreeGenerator(WffTree _tree) {
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
     * <p>
     * https://www.baeldung.com/java-print-binary-tree-diagram
     *
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
     * @param _node
     */
    public abstract void buildTreeHelper(TruthTree _node);

    /**
     * Stacks a conjunction node. The stack works as follows:
     * <p>
     * If we have (P & Q), then
     * <p>
     * P
     * Q
     * <p>
     * is the resulting stack. All stacks should be added to the leaves.
     *
     * @param _conj   - Conjunction node.
     * @param _leaves - list of leaves.
     * @param _queue  - priority queue of nodes left to process.
     */
    protected void stackConjunction(TruthTree _conj, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_conj.getWff() instanceof AndNode)) {
            throw new IllegalArgumentException("Error: conjunction expects conjunction node but got " + _conj.getClass());
        }

        // We need to stack on the leaf ONLY.
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                leaf.addCenter(new TruthTree(_conj.getWff().getChild(0), leaf));

                LinkedList<TruthTree> newLeaf = new LinkedList<>();
                newLeaf.add(leaf.getCenter());

                leaf.getCenter().addCenter(new TruthTree(_conj.getWff().getChild(1), leaf.getCenter()));
                _queue.add(leaf.getCenter());
                _queue.add(leaf.getCenter().getCenter());
            }
        }
    }

    /**
     * Branches a disjunction node. The disjunction works as follows:
     * <p>
     * If we have (A | B), then
     * <p>
     * A OR B
     * <p>
     * is the resulting branch. If there are multiple leaves in the current TruthTree
     * node, then this branch is applied to all of them.
     *
     * @param _disj   - Disjunction node.
     * @param _leaves - list of leaves.
     * @param _queue  - priority queue of nodes left to process.
     */
    protected void branchDisjunction(TruthTree _disj, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_disj.getWff() instanceof OrNode)) {
            throw new IllegalArgumentException("Error: disjunction expects disjunction node but got " + _disj.getClass());
        }

        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                leaf.addLeft(new TruthTree(_disj.getWff().getChild(0), leaf));
                leaf.addRight(new TruthTree(_disj.getWff().getChild(1), leaf));
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getRight());
            }
        }
    }

    /**
     * Branches an implication node in the truth tree.
     * <p>
     * The implication branches as follows:
     * <p>
     * If we have (A -> B), then
     * ~A OR B
     * <p>
     * is the resulting branch. If there are multiple leaves in the current TruthTree
     * node, then this branch is applied to all of them.
     *
     * @param _imp    - Implication node.
     * @param _leaves - list of leaves.
     * @param _queue  - priority queue of nodes left to process.
     */
    protected void branchImplication(TruthTree _imp, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_imp.getWff() instanceof ImpNode)) {
            throw new IllegalArgumentException("Error: implication expects implication node but got " + _imp.getClass());
        }

        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                leaf.addLeft(new TruthTree(getNegatedNode(_imp.getWff().getChild(0)), leaf));
                leaf.addRight(new TruthTree(_imp.getWff().getChild(1), leaf));
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getRight());
            }
        }
    }

    /**
     * Stacks an implication node in the truth tree.
     * <p>
     * The negated implication stacks as follows:
     * <p>
     * If we have ~(A -> B), then
     * A AND ~B
     * <p>
     * is the resulting branch. If there are multiple leaves in the current TruthTree
     * node, then this branch is applied to all of them.
     *
     * @param _negRoot - Implication node.
     * @param _leaves  - list of leaves.
     * @param _queue   - priority queue of nodes left to process.
     */
    protected void stackNegationImplication(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_negRoot.getWff().getChild(0) instanceof ImpNode)) {
            throw new IllegalArgumentException("Error: negated implication child expects implication node but got " + _negRoot.getClass());
        }
        WffTree impNode = _negRoot.getWff().getChild(0);
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                leaf.addCenter(new TruthTree(impNode.getChild(0), leaf));
                leaf.getCenter().addCenter(new TruthTree(getNegatedNode(impNode.getChild(1)), leaf.getCenter()));
                _queue.add(leaf.getCenter());
                _queue.add(leaf.getCenter().getCenter());
            }
        }
    }

    /**
     * Branches an exclusive OR operator as follows:
     * (A XOR B)
     * <p>
     * The negation is applied such that it branches, with the lhs containing
     * ~A, B stacked, and the rhs contains ~B, A.
     * </p>
     * <p>
     * This is identical to the negated biconditional operator.
     *
     * @param _xorRoot - TruthTree node such that its WffTree instance is an xor node.
     * @param _leaves  - list of leaf nodes.
     * @param _queue   - Priority queue to add the four constructed children to.
     */
    protected void branchExclusiveOr(TruthTree _xorRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_xorRoot.getWff() instanceof ExclusiveOrNode)) {
            throw new IllegalArgumentException("Error: branch exclusive or expects exclusive or node but got " + _xorRoot.getClass());
        }

        WffTree xorNode = _xorRoot.getWff();
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Left subtree.
                leaf.addLeft(new TruthTree(xorNode.getChild(0), leaf));
                leaf.getLeft().addCenter(new TruthTree(getNegatedNode(xorNode.getChild(1)), leaf.getLeft()));

                // Right subtree.
                leaf.addRight(new TruthTree(xorNode.getChild(1), leaf));
                leaf.getRight().addCenter(new TruthTree(getNegatedNode(xorNode.getChild(0)), leaf.getRight()));

                // Add them to the queue.
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getLeft().getCenter());
                _queue.add(leaf.getRight());
                _queue.add(leaf.getRight().getCenter());
            }
        }
    }

    /**
     * Branches a negated exclusive OR operator as follows:
     * ~(A XOR B)
     * <p>
     * The negation is applied such that it branches, with the lhs containing
     * A, B stacked, and the rhs contains ~A, ~B.
     * </p>
     * <p>
     * This is identical to the biconditional operator.
     *
     * @param _negRoot - TruthTree node such that its WffTree instance is a negated node and its child is a xor node..
     * @param _leaves  - list of leaf nodes.
     * @param _queue   - Priority queue to add the four constructed children to.
     */
    protected void branchNegationExclusiveOr(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_negRoot.getWff().getChild(0) instanceof ExclusiveOrNode)) {
            throw new IllegalArgumentException("Error: branch negation exclusive or expects exclusive or node but got " + _negRoot.getClass());
        }

        WffTree xorNode = _negRoot.getWff().getChild(0);
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Left subtree.
                leaf.addLeft(new TruthTree(xorNode.getChild(0), leaf));
                leaf.getLeft().addCenter(new TruthTree(xorNode.getChild(1), leaf.getLeft()));

                // Right subtree.
                leaf.addRight(new TruthTree(getNegatedNode(xorNode.getChild(0)), leaf));
                leaf.getRight().addCenter(new TruthTree(getNegatedNode(xorNode.getChild(1)), leaf.getRight()));

                // Add them to the queue.
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getLeft().getCenter());
                _queue.add(leaf.getRight());
                _queue.add(leaf.getRight().getCenter());
            }
        }
    }

    /**
     * Branches a biconditional node in the form of
     * (A <-> B)
     * <p>
     * The negation is applied such that it branches, with the lhs containing
     * A, B stacked, and the rhs contains ~A, ~B.
     *
     * @param _bicond - TruthTree node such that its WffTree instance is a BicondNode.
     * @param _leaves - list of leaf nodes.
     * @param _queue  - Priority queue to add the four constructed children to.
     */
    protected void branchBiconditional(TruthTree _bicond, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_bicond.getWff() instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch biconditional expects biconditional node but got " + _bicond.getClass());
        }

        WffTree bicondNode = _bicond.getWff();
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Left subtree.
                leaf.addLeft(new TruthTree(bicondNode.getChild(0), leaf));
                leaf.getLeft().addCenter(new TruthTree(bicondNode.getChild(1), leaf.getLeft()));

                // Right subtree.
                leaf.addRight(new TruthTree(getNegatedNode(bicondNode.getChild(0)), leaf));
                leaf.getRight().addCenter(new TruthTree(getNegatedNode(bicondNode.getChild(1)), leaf.getRight()));

                // Add them to the queue.
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getLeft().getCenter());
                _queue.add(leaf.getRight());
                _queue.add(leaf.getRight().getCenter());
            }
        }
    }

    /**
     * Distributes a negation to a biconditional node in the form of
     * ~(A <-> B)
     * <p>
     * The negation is applied such that it branches, with the lhs containing
     * ~A, B stacked, and the rhs contains ~B, A.
     *
     * @param _negRoot - TruthTree node such that its WffTree instance is a negated node and its child is a bicond.
     * @param _leaves  - list of leaf nodes.
     * @param _queue   - Priority queue to add the four constructed children to.
     */
    protected void branchNegationBiconditional(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_negRoot.getWff().getChild(0) instanceof BicondNode)) {
            throw new IllegalArgumentException("Error: branch negation biconditional expects biconditional node but got " + _negRoot.getClass());
        }

        WffTree bicondNode = _negRoot.getWff().getChild(0);
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Left subtree.
                leaf.addLeft(new TruthTree(bicondNode.getChild(0), leaf));
                leaf.getLeft().addCenter(new TruthTree(getNegatedNode(bicondNode.getChild(1)), leaf.getLeft()));

                // Right subtree.
                leaf.addRight(new TruthTree(getNegatedNode(bicondNode.getChild(0)), leaf));
                leaf.getRight().addCenter(new TruthTree(bicondNode.getChild(1), leaf));

                // Add them to the queue.
                _queue.add(leaf.getLeft());
                _queue.add(leaf.getLeft().getCenter());
                _queue.add(leaf.getRight());
                _queue.add(leaf.getRight().getCenter());
            }
        }
    }

    /**
     * Distributes a negation into the node.
     * <p>
     * Negations are applied using De'Morgan's laws as follows:
     * ~(A | B) == (~A & ~B)
     * ~(A & B) == (~A | ~B)
     * ~(A -> B) == (~~A & ~B)
     * <p>
     * Each of these equivalences on the rhs of the rule *is* applied when building
     * the tree. This means that there are some redundant steps added. These ought
     * be noted in the final version if time permits.
     *
     * @param _negRoot - negation node itself.
     * @param _leaves  - linked list of leave nodes for this current TruthTree node.
     *                 This is computed before this method is called (and can
     *                 be abstracted to this method...)
     * @param _queue   - priority queue of nodes - each child created in this method
     *                 is added to this priority queue.
     */
    protected void distributeNegation(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        WffTree child = _negRoot.getWff().getChild(0);
        WffTree negatedAtom;
        TruthTree enqueuedTTNode;

        // Double negations are simple - just remove the negations altogether.
        if (child.isNegation()) {
            // Add to all leaves in this tree.
            for (TruthTree leaf : _leaves) {
                if (!leaf.isClosed()) {
                    enqueuedTTNode = new TruthTree(child.getChild(0), leaf);
                    leaf.addCenter(enqueuedTTNode);
                    _queue.add(enqueuedTTNode);
                }
            }
        } else {
            negatedAtom = getNegatedBinaryNode(child);

            // Create the negation nodes for the children.
            NegNode n1 = new NegNode();
            NegNode n2 = new NegNode();

            n1.addChild(child.getChild(0));
            n2.addChild(child.getChild(1));
            negatedAtom.addChild(n1);
            negatedAtom.addChild(n2);
            enqueuedTTNode = new TruthTree(negatedAtom, _negRoot);

            // Call the respective branch/stack function. Removes De'Morgan's laws.
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
     * @return LinkedList<TruthTree> of leaf nodes.
     */
    public static LinkedList<TruthTree> getLeaves(TruthTree _truthTree) {
        LinkedList<TruthTree> leaves = new LinkedList<>();
        getLeavesHelper(_truthTree, leaves);
        return leaves;
    }

    /**
     * Computes the path from every leaf node to the root, looking for
     * closed branches.
     * <p>
     * A branch is closed if and only if there exists a wff P such that
     * ~P is an ancestor.
     * <p>
     * Only leaves may ever be closed. This traversal goes up from all leaves
     * and tries to find a closure. Closures only occur after a wff is completely
     * developed (i.e., cannot close in the middle of a development), so if there
     * exists a node that contradicts with something up the chain, we have to check
     * it. Looks inefficient, but in practice checks very few nodes because of the
     * flag.
     * <p>
     * The invariant is that if a leaf P has no contradictions (that is, no ancestor
     * is ~P) when checked, then it will never contradict with an ancestor.
     *
     * @param _leaves - list of leaves to check for closure.
     */
    public static void computeClosedBranches(LinkedList<TruthTree> _leaves) {
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                TruthTree currentLeaf = leaf;
                // Optimization...
                outer:
                while (currentLeaf != null && (currentLeaf.getFlags() & NodeFlag.STOP_CLOSE_CHECK) == 0) {
                    TruthTree parentToCheck = currentLeaf.getParent();
                    while (parentToCheck != null) {
                        if (currentLeaf.getWff().equals(getFlippedNode(parentToCheck.getWff()))
                                && currentLeaf.getWff().isClosable()) {
                            leaf.setClosed(true);
                            break outer;
                        }
                        parentToCheck = parentToCheck.getParent();
                    }
                    currentLeaf = currentLeaf.getParent();
                }
            }
            leaf.setFlags(NodeFlag.STOP_CLOSE_CHECK);
        }
    }

    /**
     * Computes the negated version of any arbitrary WffTree node. This performs
     * a "raw negation" only, where raw is defined as follows:
     * <p>
     * If our input is a wff P, then we return ~P. Similarly,
     * If our input is a wff ~P, then we return ~~P.
     *
     * @param _wff - WffTree object to negate.
     * @return negated version of WffTree.
     */
    protected static WffTree getNegatedNode(WffTree _wff) {
        WffTree negWff;
        NegNode neg = new NegNode();
        neg.addChild(_wff);
        negWff = neg;

        return negWff;
    }

    /**
     * Recursive helper function for computing leaf nodes in a Truth Tree.
     * Uses a pre-order traversal.
     *
     * @param _truthTree - TruthTree node to recursively search for leaves.
     * @param _leaves    - linked list of leaf nodes to add to.
     */
    protected static void getLeavesHelper(TruthTree _truthTree, LinkedList<TruthTree> _leaves) {
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
     * <p>
     * The difference between this and getNegatedNode(...) is that this node returns
     * the corresponding subtype instead of a generic WffTree or NegNode. In other words,
     * this method returns an AndNode or an OrNode.
     *
     * @param _tree - WffTree node to negate.
     * @return WffTree node instance of the corresponding negative type.
     * @throws IllegalArgumentException if tree is not an OrNode, ImpNode, or AndNode.
     */
    protected static WffTree getNegatedBinaryNode(WffTree _tree) {
        if (_tree.isOr()) {
            return new AndNode();
        } else if (_tree.isAnd()) {
            return new OrNode();
        }

        throw new IllegalArgumentException("Cannot get negated node of type " + _tree);
    }

    /**
     * Computes the negated version of any arbitrary WffTree node. This performs
     * a "simple negation" only, where simple is defined as follows:
     * <p>
     * If our input is a wff P, then we return ~P. Similarly,
     * If our input is a wff ~P, then we return P.
     *
     * @param _wff - WffTree object to negate.
     * @return negated version of WffTree.
     */
    protected static WffTree getFlippedNode(WffTree _wff) {
        WffTree negWff;
        if (_wff.isNegation()) {
            negWff = _wff.getChild(0);
        } else if (_wff.isExistential()) {
            WffTree newUniversal = _wff.getChild(0).copy();
            UniversalQuantifierNode univ = new UniversalQuantifierNode(((ExistentialQuantifierNode) _wff).getVariableSymbol());
            NegNode n = new NegNode();
            n.addChild(newUniversal);
            univ.addChild(n);
            return univ;
        } else if (_wff.isUniversal()) {
            WffTree newExistential = _wff.getChild(0).copy();
            ExistentialQuantifierNode exis = new ExistentialQuantifierNode(((UniversalQuantifierNode) _wff).getVariableSymbol());
            NegNode n = new NegNode();
            n.addChild(newExistential);
            exis.addChild(n);
            return exis;
        } else {
            NegNode neg = new NegNode();
            neg.addChild(_wff);
            negWff = neg;
        }

        return negWff;
    }

    /**
     * TODO Document
     * <p>
     * https://www.baeldung.com/java-print-binary-tree-diagram
     *
     * @param sb
     * @param padding
     * @param pointer
     * @param node
     * @param hasRightSibling
     */
    private static void printHelper(StringBuilder sb, String padding, String pointer, TruthTree node,
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
}
