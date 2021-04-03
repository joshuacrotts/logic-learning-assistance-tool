package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.AndNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicallyConsistentDeterminer {

    /**
     *
     */
    private WffTree combinedTree;

    public LogicallyConsistentDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        // Construct the combined tree, where the biconditional note
        AndNode andNode = new AndNode();
        andNode.addChild(_wffTreeOne.getChild(0));
        andNode.addChild(_wffTreeTwo.getChild(0));

        this.combinedTree = new WffTree();
        this.combinedTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.combinedTree.addChild(andNode);
    }

    /**
     * @return
     */
    public boolean isConsistent() {
        BaseTruthTreeGenerator consistentBranchTreeGenerator;
        BaseTruthTreeGenerator inconsistentBranchTreeGenerator;

        // Create the ROOTs for the two trees.
        WffTree leftSubTree = new WffTree();
        WffTree rightSubTree = new WffTree();
        leftSubTree.addChild(this.combinedTree.getChild(0));
        rightSubTree.addChild(this.combinedTree.getChild(0));

        if (this.combinedTree.isPropositionalWff()) {
            consistentBranchTreeGenerator = new PropositionalTruthTreeGenerator(leftSubTree);
            inconsistentBranchTreeGenerator = new PropositionalTruthTreeGenerator(rightSubTree);
        } else {
            consistentBranchTreeGenerator = new PredicateTruthTreeGenerator(leftSubTree);
            inconsistentBranchTreeGenerator = new PredicateTruthTreeGenerator(rightSubTree);
        }

        TruthTree consistentTree = consistentBranchTreeGenerator.get();
        TruthTree inconsistentTree = inconsistentBranchTreeGenerator.get();

        // The consistency branch must close, and the right must have at least one open branch.
        return (new ClosedTreeDeterminer(consistentTree).hasAllClosed())
                && (new OpenTreeDeterminer(consistentTree).hasSomeOpen());
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }
}
