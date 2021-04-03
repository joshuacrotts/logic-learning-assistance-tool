package com.llat.algorithms.propositional;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.algorithms.models.TruthTree;
import com.llat.models.treenode.WffTree;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * TODO Document
 */
public final class PropositionalTruthTreeGenerator extends BaseTruthTreeGenerator {

    public PropositionalTruthTreeGenerator(WffTree _tree) {
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
        int iterations = 0;
        // Initialize the min-heap and linkedlist of leaves.
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        LinkedList<TruthTree> leaves = new LinkedList<>();

        // Add the root to these structures and compute all constants in the root.
        leaves.add(_node);
        queue.add(_node);

        // Poll the heap and build the tree.
        while (!queue.isEmpty()) {
            if (++iterations >= TIMEOUT) {
                System.err.println("Timeout error: cannot compute a tree this complex.");
                return;
            }

            TruthTree tree = queue.poll();
            WffTree curr = tree.getWff();
            leaves = getLeaves(tree);
            computeClosedBranches(leaves);

            if (curr.isNegation() && curr.getChild(0).isBicond()) {
                // We handle biconditional negations differently since they're harder.
                this.branchNegationBiconditional(tree, leaves, queue);
            } else if (curr.isNegation() && curr.getChild(0).isImp()) {
                // We handle a negated implication differently.
                this.stackNegationImplication(tree, leaves, queue);
            } else if (curr.isNegExclusiveOr()) {
                this.branchNegationExclusiveOr(tree, leaves, queue);
            } else if (curr.isNegation() && !curr.getChild(0).isAtom()) {
                // If the node is not a simple negation (~A), negate it.
                this.distributeNegation(tree, leaves, queue);
            } else if (curr.isAnd()) {
                this.stackConjunction(tree, leaves, queue);
            } else if (curr.isOr()) {
                this.branchDisjunction(tree, leaves, queue);
            } else if (curr.isImp()) {
                this.branchImplication(tree, leaves, queue);
            } else if (curr.isBicond()) {
                this.branchBiconditional(tree, leaves, queue);
            } else if (curr.isExclusiveOr()) {
                this.branchExclusiveOr(tree, leaves, queue);
            }
        }
    }
}
