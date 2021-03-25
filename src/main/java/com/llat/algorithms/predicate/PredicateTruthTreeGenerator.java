package com.llat.algorithms.predicate;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.algorithms.models.TruthTree;
import com.llat.models.treenode.*;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * TODO Document
 */
public final class PredicateTruthTreeGenerator extends BaseTruthTreeGenerator {

    public PredicateTruthTreeGenerator(WffTree _tree) {
        super(_tree);
    }

    /**
     * Builds the propositional truth tree. A truth tree is characterized by
     * branches and stacks. More information is presented in the documentation.
     *
     * @param _node - TruthTree root.
     */
    @Override
    public void buildTreeHelper(TruthTree _node) {
        // Initialize the min-heap and linkedlist of leaves.
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        LinkedList<TruthTree> leaves = new LinkedList<>();

        // Add the root to these structures and compute all constants in the root.
        leaves.add(_node);
        queue.add(_node);
        getAllConstants(leaves);

        // Poll the heap and build the tree.
        while (!queue.isEmpty()) {
            TruthTree tree = queue.poll();
            WffTree curr = tree.getWff();
            computeClosedBranches(leaves);
            leaves = getLeaves(tree);
            if (curr.isNegation() && curr.getChild(0).isBicond()) {
                // We handle biconditional negations differently since they're harder.
                this.branchNegationBiconditional(tree, leaves, queue);
            } else if (curr.isNegation() && !curr.getChild(0).isPredicate() && !curr.getChild(0).isQuantifier() && !curr.getChild(0).isIdentity()) {
                // If the node is not a simple negation (~A) AND it's not a quantifier, negate it.
                this.distributeNegation(tree, leaves, queue);
            } else if (curr.isNegation() && tree.getWff().getChild(0).isQuantifier()) {
                // If the node is not a simple negation (~A), negate it.
                this.distributeNegationQuantifier(tree, leaves, queue);
            } else if (curr.isExistential()) {
                this.existentialDecomposition(tree, leaves, queue);
            } else if (curr.isUniversal()) {
                this.universalDecomposition(tree, leaves, queue);
            } else if (curr.isIdentity()) {
                this.identityDecomposition(tree, leaves, queue);
            } else if (curr.isAnd()) {
                this.stackConjunction(tree, leaves, queue);
            } else if (curr.isOr()) {
                this.branchDisjunction(tree, leaves, queue);
            } else if (curr.isImp()) {
                this.branchImplication(tree, leaves, queue);
            } else if (curr.isBicond()) {
                this.branchBiconditional(tree, leaves, queue);
            }

            leaves = getLeaves(_node);
        }
    }

    /**
     * Computes existential decomposition on any arbitrary node in the tree.
     *
     * Existential decomposition is defined by the replacement of a variable
     * with a constant not previously used in the tree.
     *
     * @param _existentialTruthTree - Existential node.
     * @param _leaves               - list of leaves.
     * @param _queue                - priority queue of nodes left to process.
     */
    private void existentialDecomposition(TruthTree _existentialTruthTree, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_existentialTruthTree.getWff() instanceof ExistentialQuantifierNode)) {
            throw new IllegalArgumentException("Error: existential quantifier node expects existential node but got " + _existentialTruthTree.getClass());
        }

        char variableToReplace = ((ExistentialQuantifierNode) _existentialTruthTree.getWff()).getVariableSymbol().charAt(0);
        _existentialTruthTree.addExistentialConstant(_existentialTruthTree, _leaves, _queue, variableToReplace);
    }

    /**
     * Computes universal decomposition on any arbitrary node in the tree.
     *
     * Universal decomposition is defined by the replacement of a quantified
     * variable in the tree by a constant PREVIOUSLY used in the tree.
     *
     * Note that this method of decomposition adds all instances of previous
     * constants to the tree, which is inefficient and generally unnecessary.
     *
     * @param _universalTruthTree - Universal node.
     * @param _leaves             - list of leaves.
     * @param _queue              - priority queue of nodes left to process.
     */
    private void universalDecomposition(TruthTree _universalTruthTree, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_universalTruthTree.getWff() instanceof UniversalQuantifierNode)) {
            throw new IllegalArgumentException("Error: universal quantifier node expects universal node but got " + _universalTruthTree.getClass());
        }

        // Add all possible constants to our list of them.
        for (TruthTree leaf : _leaves) {
            _universalTruthTree.getAvailableConstants().addAll(leaf.getAvailableConstants());
        }

        // There must be at least one constant somewhere in the truth tree. If not, then it's invalid...
        if (_universalTruthTree.getAvailableConstants().isEmpty()) {
            throw new InvalidParameterException("Universal truth tree node should have at least one constant available, but none are listed.");
        }

        char variableToReplace = ((UniversalQuantifierNode) _universalTruthTree.getWff()).getVariableSymbol().charAt(0);
        _universalTruthTree.addUniversalConstant(_universalTruthTree, _leaves, _queue, variableToReplace);
    }

    /**
     *
     * @param _identityTruthTree
     * @param _leaves
     * @param _queue
     */
    private void identityDecomposition(TruthTree _identityTruthTree, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        if (!(_identityTruthTree.getWff() instanceof IdentityNode)) {
            throw new IllegalArgumentException("Error: identity truth tree node expects identity node but got " + _identityTruthTree.getWff().getClass());
        }

        // Add all possible constants to our list of them.
        for (TruthTree leaf : _leaves) {
            _identityTruthTree.getAvailableConstants().addAll(leaf.getAvailableConstants());
        }

        _identityTruthTree.addIdentityConstant(_identityTruthTree, _leaves, _queue);
    }

    /**
     * Flips the quantifier with a negation in front as follows:
     *
     * ~(x)P = (Ex)~P
     * ~(Ex)P = (x)~P
     *
     * @param _negRoot - negation node itself.
     * @param _leaves  - linked list of leave nodes for this current TruthTree node.
     *                 This is computed before this method is called (and can
     *                 be abstracted to this method...)
     * @param _queue   - priority queue of nodes - each child created in this method
     *                 is added to this priority queue.
     */
    private void distributeNegationQuantifier(TruthTree _negRoot, LinkedList<TruthTree> _leaves, PriorityQueue<TruthTree> _queue) {
        WffTree negatedQuantifier = getFlippedNode(_negRoot.getWff().getChild(0));
        for (TruthTree tt : _leaves) {
            if (!tt.isClosed()) {
                tt.addCenter(new TruthTree(negatedQuantifier, tt));
                _queue.add(tt.getCenter());
            }
        }
    }

    /**
     * TODO Document
     *
     * @param _leaves
     */
    private void getAllConstants(LinkedList<TruthTree> _leaves) {
        for (TruthTree leaf : _leaves) {
            this.getAllConstantsHelper(leaf);
        }
    }

    /**
     * TODO Document
     *
     * @param _tree
     */
    private void getAllConstantsHelper(TruthTree _tree) {
        TruthTree curr = _tree;
        while (curr != null) {
            String str = curr.getWff().getStringRep();
            for (int c = 0; c < str.length(); c++) {
                char ch = str.charAt(c);
                if (ch >= 'a' && ch <= 't') {
                    _tree.addConstant(ch);
                }
            }
            curr = curr.getParent();
        }
    }
}