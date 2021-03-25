package com.llat.algorithms.models;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.models.treenode.ConstantNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Static class for constructing a TruthTree.
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

    //private final BaseTruthTreeGenerator TRUTH_TREE;

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
     * Left pointer.
     */
    private TruthTree left;

    /**
     * Right pointer.
     */
    private TruthTree right;

    /**
     * Order of precedence for this node (as described above).
     */
    private final int VALUE;

    /**
     * Flags for the Truth tree - determines the status (open/closed), and if
     * it is an identity truth tree.
     */
    private int flags;

    public TruthTree(WffTree _node, TruthTree _parent) {
        //this.TRUTH_TREE = _truthTree;
        this.NODE = _node;
        this.PARENT = _parent;
        this.AVAILABLE_CONSTANTS = new HashSet<>();

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
        return this.VALUE - _o.VALUE;
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
                this.replaceVariable(_newRoot, _variableToReplace, constant);

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
        for (TruthTree leaf : _leaves) {
            // Copy the old root, replace all variables with a constant, and add to the tree and queue.
            TruthTree l = leaf;
            for (char c : _universalTruthTree.AVAILABLE_CONSTANTS) {
                if (!l.isClosed()) {
                    // Create a copy and replace the selected variable.
                    WffTree _newRoot = _universalTruthTree.getWff().getChild(0).copy();
                    this.replaceVariable(_newRoot, _variableToReplace, c);

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
//            WffTree w = _identityTruthTree.getWff();
//
//            for (TruthTree leaf : _leaves) {
//                // Copy the old leaf and replace the constants.
//                TruthTree  l = leaf;
//            }
    }

    /**
     * Replaces a variable with a constant node in a WffTree. This is used when performing
     * existential or universal decomposition.
     *
     * @param _newRoot           - root of WffTree to modify.
     * @param _variableToReplace - variable that we want to replace e.g. (x) = x
     * @param _constant          - constant to replace variable with.
     */
    private void replaceVariable(WffTree _newRoot, char _variableToReplace, char _constant) {
        for (int i = 0; i < _newRoot.getChildrenSize(); i++) {
            if (_newRoot.getChild(i).isVariable()) {
                char v = _newRoot.getChild(i).getSymbol().charAt(0);
                if (v == _variableToReplace) {
                    _newRoot.setChild(i, new ConstantNode("" + _constant));
                    break;
                }
            }
            replaceVariable(_newRoot.getChild(i), _variableToReplace, _constant);
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
}
