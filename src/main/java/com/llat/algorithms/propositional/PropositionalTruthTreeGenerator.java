package com.llat.algorithms.propositional;

import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.algorithms.models.TruthTree;
import com.llat.models.treenode.*;

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
        LinkedList<TruthTree> leaves = new LinkedList<>();
        PriorityQueue<TruthTree> queue = new PriorityQueue<>();
        queue.add(_node);

        while (!queue.isEmpty()) {
            TruthTree tree = queue.poll();
            leaves = getLeaves(tree);

            if (tree.getWff().isNegation() && tree.getWff().getChild(0).isBicond()) {
                // We handle biconditional negations differently since they're harder.
                this.branchNegationBiconditional(tree, leaves, queue);
            } else if (tree.getWff().isNegation() && !tree.getWff().getChild(0).isAtom()) {
                // If the node is not a simple negation (~A), negate it.
                this.distributeNegation(tree, leaves, queue);
            } else if (tree.getWff().isAnd()) {
                this.stackConjunction(tree, leaves, queue);
            } else if (tree.getWff().isOr()) {
                this.branchDisjunction(tree, leaves, queue);
            } else if (tree.getWff().isImp()) {
                this.branchImplication(tree, leaves, queue);
            } else if (tree.getWff().isBicond()) {
                this.branchBiconditional(tree, leaves, queue);
            }

            leaves = getLeaves(_node);
            computeClosedBranches(leaves);
        }
    }
}
