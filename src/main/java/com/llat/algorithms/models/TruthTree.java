package com.llat.algorithms.models;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.models.treenode.ConstantNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

import java.util.*;

/**
 * Class for constructing a TruthTree.
 * <p>
 * TruthTrees contain three instance objects: a WffTree node, which is its "value",
 * and two children (acting as a binary tree): a left and right pointer to TTs.
 * There is one extra integer, corresponding to a value. This value indicates the
 * precedence of the operator, in increasing order as follows:
 * <p>
 * NEG_QUANTIFIERS < EXISTENTIAL < DOUBLE_NEG < CONJ < DISJ < IMP < BICOND < PREDICATE < UNIVERSAL
 * <p>
 * These nodes are stored in a minheap (priority queue), and are ordered such that
 * the truth tree operations are performed in the aforementioned order.
 * <p>
 * A TruthTree can have either one or two children (same as a binary tree), with the
 * exception that a tree with only one child is a "stacked" subtree, and should be
 * referenced with getCenter() and addCenter(...) calls. So, nodes with two children
 * (left and right) should not use this; it is used for the conjunction operator.
 */
public class TruthTree implements Comparable<TruthTree> {

    /**
     *
     */
    private static int truthTreeCount = 0;

    /**
     * WffTree "value" for the TruthTree.
     */
    private final WffTree NODE;

    /**
     * Pointer to the parent node for reverse traversal.
     */
    private final TruthTree PARENT;

    /**
     * Set of available constants allocated to this TruthTree as well as
     * any parents above it.
     */
    private final Set<Character> AVAILABLE_CONSTANTS;

    /**
     * Map of any available substitutions indicated by the identity operator.
     * A substitution is defined by having two constants a, b where the node
     * a = b exists. Then, for any occurrence of a in a closable node, we can
     * substitute this for b, and vice versa.
     */
    private final Map<Character, Character> SUBSTITUTIONS;

    /**
     * Order of precedence for this node (as described above).
     */
    private final int VALUE;
    /**
     * Left pointer.
     */
    private TruthTree left;
    /**
     * Right pointer.
     */
    private TruthTree right;

    /**
     * Flags for the Truth tree - determines the status (open/closed), and if
     * it is an identity truth tree.
     */
    private int flags;

    /**
     *
     */
    private int identifierNo;

    public TruthTree(WffTree _node, TruthTree _parent) {
        //this.TRUTH_TREE = _truthTree;
        this.NODE = _node;
        this.PARENT = _parent;
        this.AVAILABLE_CONSTANTS = new HashSet<>();
        this.SUBSTITUTIONS = new HashMap<>();
        this.identifierNo = ++TruthTree.truthTreeCount;

        // Compute the union of the constants from the parent.
        if (_parent != null) {
            this.AVAILABLE_CONSTANTS.addAll(_parent.getAvailableConstants());
        }

        // This is kind of ugly, I know...
        if (_node.isAtom()) {
            this.VALUE = 0;
        } else if (_node.isDoubleNegation()) {
            // Double negations have to have a higher priority.
            this.VALUE = 3;
        } else if (_node.isNegation() && !_node.isNegAnd() && !_node.isNegImp() && !_node.isNegOr()) {
            this.VALUE = 4;
        } else if (_node.isExistential()) {
            this.VALUE = 1;
        } else if (_node.isUniversal()) {
            this.VALUE = 12;
        } else if (_node.isAnd()) {
            this.VALUE = 5;
        } else if (_node.isNegOr() || _node.isNegImp()) {
            this.VALUE = 6;
        } else if (_node.isOr()) {
            this.VALUE = 7;
        } else if (_node.isNegAnd()) {
            this.VALUE = 8;
        } else if (_node.isImp()) {
            this.VALUE = 9;
        } else if (_node.isBicond()) {
            this.VALUE = 10;
        } else {
            this.VALUE = 11;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            throw new ClassCastException("Cannot cast class of type " + obj.getClass() + " to type " + this.getClass());
        }

        TruthTree o = (TruthTree) obj;
        return this.getWff().equals(o.getWff());
    }

    @Override
    public int compareTo(TruthTree _o) {
        if (this.VALUE - _o.VALUE == 0) {
            return this.identifierNo - _o.identifierNo;
        }
        return (this.VALUE - _o.VALUE);
    }

    /**
     * TODO Document
     *
     * @param _existentialTruthTree
     * @param _variableToReplace
     * @param _leaves
     */
    public void addExistentialConstant(TruthTree _existentialTruthTree, LinkedList<TruthTree> _leaves,
                                       PriorityQueue<TruthTree> _queue, char _variableToReplace) {
        // Find the next available constant to use.
        char constant = 'a';
        while (_existentialTruthTree.AVAILABLE_CONSTANTS.contains(constant)) {
            constant++;
        }

        // Replace all variables found with the constant.
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Create a copy and replace the selected variable.
                WffTree _newRoot = _existentialTruthTree.getWff().getChild(0).copy();
                this.replaceSymbol(_newRoot, _variableToReplace, constant);

                // Add to the tree and the queue.
                TruthTree truthTreeRoot = new TruthTree(_newRoot, leaf);
                leaf.addCenter(truthTreeRoot);
                truthTreeRoot.AVAILABLE_CONSTANTS.add(constant);
                _queue.add(leaf.getCenter());
            }
        }
    }

    /**
     * @param _universalTruthTree
     * @param _leaves
     */
    public void addUniversalConstant(TruthTree _universalTruthTree, LinkedList<TruthTree> _leaves,
                                     PriorityQueue<TruthTree> _queue, char _variableToReplace) {
        // Add a default constant if one is not available to the universal quantifier.
        if (_universalTruthTree.AVAILABLE_CONSTANTS.isEmpty()) {
            _universalTruthTree.addConstant('a');
        }

        for (TruthTree leaf : _leaves) {
            // Copy the old root, replace all variables with a constant, and add to the tree and queue.
            TruthTree l = leaf;
            for (char c : _universalTruthTree.AVAILABLE_CONSTANTS) {
                if (!l.isClosed()) {
                    // Create a copy and replace the selected variable.
                    WffTree _newRoot = _universalTruthTree.getWff().getChild(0).copy();
                    this.replaceSymbol(_newRoot, _variableToReplace, c);

                    // Add to the tree and the queue.
                    TruthTree _newRootTT = new TruthTree(_newRoot, leaf);
                    l.addCenter(_newRootTT);
                    _queue.add(_newRootTT);

                    // Set the traversing child to the next node added, get the leaves of it
                    // and then recursively close the branches if any contradictions are found.
                    l = l.getCenter();
                    LinkedList<TruthTree> ttl = BaseTruthTreeGenerator.getLeaves(_universalTruthTree);
                    BaseTruthTreeGenerator.computeClosedBranches(ttl);
                }
            }
        }
    }

    /**
     * TODO Handle replacements e.g. a=b, Fab, ~Faa, add ~Fab or Faa
     * <p>
     * At the leaf, traverse upwards to find any closable wffs that can substitute in a constant for another
     * constant. If this derives a contr. then close.
     *
     * @param _identityTruthTree
     * @param _leaves
     * @param _queue
     */
    public void addIdentityConstant(TruthTree _identityTruthTree, LinkedList<TruthTree> _leaves,
                                    PriorityQueue<TruthTree> _queue) {
        char constantOne = _identityTruthTree.getWff().getChild(0).getSymbol().charAt(0);
        char constantTwo = _identityTruthTree.getWff().getChild(1).getSymbol().charAt(0);

        // If the constants are the same, then there's really nothing we can do.
        if (constantOne == constantTwo) {
            return;
        }

        // Go from the leaf up.
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                TruthTree curr = leaf.getParent();
                TruthTree l = leaf;
                // From the leaf, find a possible contradiction.
                while (curr != null) {
                    WffTree currWff = curr.getWff().copy();
                    // If the node we find is closable AND it's not an identity operator, we can
                    // try to replace the constant we found.
                    if (currWff.isClosable() && !currWff.isIdentity() && !currWff.isNegIdentity()) {
                        this.replaceSymbol(currWff, constantOne, constantTwo);
                        if (!currWff.equals(curr.getWff())) {
                            // Add to the tree and the queue.
                            TruthTree _newRootTT = new TruthTree(currWff, l);
                            l.addCenter(_newRootTT);
                            _queue.add(_newRootTT);
                            l = l.getCenter();
                        }
                    }
                    curr = curr.getParent();
                }
            }
        }
    }

    public boolean isLeafNode() {
        return this.left == null && this.right == null;
    }

    public WffTree getWff() {
        return this.NODE;
    }

    public void addLeft(TruthTree _left) {
        this.left = _left;
    }

    public TruthTree getLeft() {
        return this.left;
    }

    public void addRight(TruthTree _right) {
        this.right = _right;
    }

    public TruthTree getRight() {
        return this.right;
    }

    public void addCenter(TruthTree _c) {
        if (this.right != null) {
            throw new IllegalArgumentException("Cannot add truth tree to right child - add it to the left only.");
        }

        this.left = _c;
    }

    public TruthTree getCenter() {
        if (this.right != null) {
            throw new IllegalCallerException("Cannot return center if right child is null.");
        }

        return this.left;
    }

    public boolean isClosed() {
        return (this.flags & NodeFlag.CLOSED) != 0;
    }

    public void setClosed(boolean _closed) {
        this.flags |= _closed ? NodeFlag.CLOSED : 0;
    }

    public TruthTree getParent() {
        return this.PARENT;
    }

    public void addConstant(char _ch) {
        this.AVAILABLE_CONSTANTS.add(_ch);
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flag) {
        this.flags |= flag;
    }

    public Set<Character> getAvailableConstants() {
        return this.AVAILABLE_CONSTANTS;
    }

    @Override
    public String toString() {
        String leafSignal = "";
        if (this.isLeafNode()) {
            leafSignal = this.isClosed() ? "X" : "open";
        }
        return this.getWff().getStringRep() + " " + leafSignal;
    }

    /**
     * Replaces a variable or a constant with a constant node in a WffTree. This is used when performing
     * existential, universal decomposition, or identity decomposition.
     *
     * @param _newRoot           - root of WffTree to modify.
     * @param _variableToReplace - variable that we want to replace e.g. (x) = x
     * @param _constant          - constant to replace variable with.
     */
    private void replaceSymbol(WffTree _newRoot, char _variableToReplace, char _constant) {
        for (int i = 0; i < _newRoot.getChildrenSize(); i++) {
            if (_newRoot.getChild(i).isVariable() || _newRoot.getChild(0).isConstant()) {
                char v = _newRoot.getChild(i).getSymbol().charAt(0);
                if (v == _variableToReplace) {
                    _newRoot.setChild(i, new ConstantNode("" + _constant));
                    break;
                }
            }
            replaceSymbol(_newRoot.getChild(i), _variableToReplace, _constant);
        }
    }
}
