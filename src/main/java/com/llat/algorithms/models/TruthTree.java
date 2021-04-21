package com.llat.algorithms.models;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.input.events.SyntaxErrorEvent;
import com.llat.models.treenode.ConstantNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;
import com.llat.tools.EventBus;

import java.util.*;

/**
 * Class for constructing a TruthTree.
 * <p>
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
 *
 * </p>
 */
public class TruthTree implements Comparable<TruthTree> {

    /**
     * Maximum number of iterations that a truth tree can substitute a
     * variable with a constant when performing identity or universal
     * decomposition.
     */
    private static final int THRESHOLD_LIMIT = 500;

    /**
     * WffTree "value" for the TruthTree.
     */
    private final WffTree NODE;

    /**
     * Pointer to the parent node for reverse traversal.
     */
    private final TruthTree PARENT;

    /**
     * Pointer to the node that derived this step.
     */
    private final TruthTree DERIVED_PARENT;

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
     * Identifier number of this truth tree node in the tree itself.
     */
    private final int identifierNo;

    /**
     * Order of precedence for this node (as described above).
     */
    private int value;

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
     * Gets the number of universal rules currently applied - this is for a timeout.
     */
    private int universalCount;

    public TruthTree(WffTree _node, TruthTree _parent, TruthTree _derivedParent) {
        this.NODE = _node;
        this.PARENT = _parent;
        this.DERIVED_PARENT = _derivedParent;
        this.AVAILABLE_CONSTANTS = new HashSet<>();
        this.SUBSTITUTIONS = new HashMap<>();
        this.identifierNo = ++BaseTruthTreeGenerator.identityCount;

        // Compute the union of the constants from the parent.
        if (_parent != null) {
            this.AVAILABLE_CONSTANTS.addAll(_parent.getAvailableConstants());
        }

        this.setTruthTreeValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            throw new ClassCastException("Cannot cast class of type " + obj.getClass() + " to type " + this.getClass());
        }

        TruthTree o = (TruthTree) obj;
        return this.getWff().stringEquals(o.getWff());
    }

    @Override
    public int compareTo(TruthTree _o) {
        if (this.value - _o.value == 0) {
            return this.identifierNo - _o.identifierNo;
        }
        return (this.value - _o.value);
    }

    /**
     * Constructs a LaTeX version of the truth tree for printout.
     *
     * @return String representing the TruthTree in LaTeX.
     */
    public String getTexTree() {
        StringBuilder sb = new StringBuilder();
        this.getTexTreeHelper(this, sb, 0);
        return sb.toString();
    }

    /**
     * Performs existential decomposition on this truth tree.
     * <p>
     * Existential decomposition is applied when we have an existential quantifier
     * that binds a variable in some predicate P. We replace all occurrences of the variable
     * bound by the quantifier in P with a constant not currently used in that branch of
     * the truth tree. Generally, this is 'a', but sometimes if that is already in use,
     * we go down the line of constants to find one that we haven't yet used.
     * </p>
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
            // This could wrap around...
            constant++;
        }

        // Replace all variables found with the constant.
        for (TruthTree leaf : _leaves) {
            if (!leaf.isClosed()) {
                // Create a copy and replace the selected variable.
                WffTree _newRoot = _existentialTruthTree.getWff().getChild(0).copy();
                this.replaceSymbol(_newRoot, _variableToReplace, constant);

                // Add to the tree and the queue.
                TruthTree truthTreeRoot = new TruthTree(_newRoot, leaf, _existentialTruthTree);
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
                    TruthTree _newRootTT = new TruthTree(_newRoot, leaf, _universalTruthTree);
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
     * Performs identity decomposition.
     * <p>
     * At the leaf, traverse upwards to find any closable wffs that can substitute in a constant for another
     * constant. If this derives a contr. then close.
     * </p>
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
                        if (!currWff.stringEquals(curr.getWff())) {
                            // Add to the tree and the queue.
                            TruthTree _newRootTT = new TruthTree(currWff, l, _identityTruthTree);
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

    public int getIdentityNumber() {
        return this.identifierNo;
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

    public TruthTree getDerivedParent() {
        return this.DERIVED_PARENT;
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

        //String deriveStep = this.DERIVED_PARENT != null ? "\t\t\t(" + this.DERIVED_PARENT.identifierNo + ") " + this.DERIVED_PARENT.getWff().getSymbol() : "";
        return this.getWff().getStringRep() + " " + leafSignal;
    }

    /**
     * Assigns the precedence value of this truth tree. This is described in
     * further detail in the above javadoc.
     */
    private void setTruthTreeValue() {
        WffTree _node = this.NODE;
        // This is kind of ugly, I know...
        if (_node.isAtom()) {
            this.value = 0;
        } else if (_node.isDoubleNegation()) {
            // Double negations have to have a higher priority.
            this.value = 3;
        } else if (_node.isNegation() && !_node.isNegAnd() && !_node.isNegImp() && !_node.isNegOr()) {
            this.value = 4;
        } else if (_node.isExistential()) {
            this.value = 1;
        } else if (_node.isUniversal()) {
            // Universal HAS to be the last operation - if not, then we run the risk of applying it before we
            // have a constant available.
            this.value = 13;
        } else if (_node.isAnd()) {
            this.value = 6;
        } else if (_node.isNegOr() || _node.isNegImp()) {
            this.value = 7;
        } else if (_node.isOr()) {
            this.value = 8;
        } else if (_node.isNegAnd()) {
            this.value = 9;
        } else if (_node.isImp()) {
            this.value = 10;
        } else if (_node.isBicond()) {
            this.value = 11;
        } else {
            this.value = 12;
        }
    }

    /**
     * Searches through the tree in preorder to build a LaTeX version of it.
     * We use the forest package with a premade template.
     *
     * @param _tree   - TruthTree object to start from.
     * @param _sb     - StringBuilder to continuously concatenate to.
     * @param _indent - level of indentation for the current brackets.
     */
    private void getTexTreeHelper(TruthTree _tree, StringBuilder _sb, int _indent) {
        if (_tree == null) {
            return;
        }

        _sb.append("\t".repeat(_indent));
        _sb.append("[");
        _sb.append(_tree.getWff().getTexCommand());

        // If it's a rule we can apply infinitely many times, add the asterisk.
        if (_tree.getWff().isUniversal() || _tree.getWff().isIdentity()) {
            _sb.append(", uni");
        }

        if (_tree.isLeafNode()) {
            _sb.append(", " + (_tree.isClosed() ? "closed" : "open"));
        } else {
            // Left and rights will need to branch, whereas just a left is a stack.
            _sb.append("\n");
            if (_tree.getLeft() != null && _tree.getRight() != null) {
                this.getTexTreeHelper(_tree.getLeft(), _sb, _indent + 1);
                _sb.append("\n");
                this.getTexTreeHelper(_tree.getRight(), _sb, _indent + 1);
            } else if (_tree.getLeft() != null) {
                this.getTexTreeHelper(_tree.getLeft(), _sb, _indent + 1);
            }
        }

        _sb.append("\n");
        _sb.append("\t".repeat(_indent) + "]");
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
        if (this.universalCount > THRESHOLD_LIMIT) {
            System.err.println("Error - universal constant has reached the upper limit of 100.");
            EventBus.throwEvent(new SyntaxErrorEvent("Error - universal constant has reached the upper limit of 100."));
        }

        for (int i = 0; i < _newRoot.getChildrenSize(); i++) {
            if (_newRoot.getChild(i).isVariable() || _newRoot.getChild(0).isConstant()) {
                char v = _newRoot.getChild(i).getSymbol().charAt(0);
                if (v == _variableToReplace) {
                    _newRoot.setChild(i, new ConstantNode("" + _constant));
                }
            }
            this.replaceSymbol(_newRoot.getChild(i), _variableToReplace, _constant);
        }
    }
}
