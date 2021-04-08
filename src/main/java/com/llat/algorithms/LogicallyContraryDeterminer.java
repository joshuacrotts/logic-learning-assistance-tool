package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicallyContraryDeterminer {

    /**
     *
     */
    private WffTree combinedTree;

    public LogicallyContraryDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        // Construct the combined tree, where the biconditional note
        BicondNode bicondNode = new BicondNode();
        bicondNode.addChild(_wffTreeOne.getChild(0));
        bicondNode.addChild(_wffTreeTwo.getChild(0));

        this.combinedTree = new WffTree();
        this.combinedTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.combinedTree.addChild(bicondNode);
    }

    /**
     * @return
     */
    public boolean isContrary() {
        BaseTruthTreeGenerator consistentBranchTreeGenerator;
        BaseTruthTreeGenerator inconsistentBranchTreeGenerator;

        // Create the ROOTs for the two trees.
        WffTree leftSubTree = new WffTree();
        WffTree rightSubTree = new WffTree();
        leftSubTree.addChild(this.combinedTree.getChild(0).getChild(0));
        rightSubTree.addChild(this.combinedTree.getChild(0).getChild(1));

        if (this.combinedTree.isPropositionalWff()) {
            consistentBranchTreeGenerator = new PropositionalTruthTreeGenerator(leftSubTree);
            inconsistentBranchTreeGenerator = new PropositionalTruthTreeGenerator(rightSubTree);
        } else {
            consistentBranchTreeGenerator = new PredicateTruthTreeGenerator(leftSubTree);
            inconsistentBranchTreeGenerator = new PredicateTruthTreeGenerator(rightSubTree);
        }

        TruthTree consistentTree = consistentBranchTreeGenerator.getTruthTree();
        TruthTree inconsistentTree = inconsistentBranchTreeGenerator.getTruthTree();

        // The consistency branch must close, and the right must have at least one open branch.
        return (new ClosedTreeDeterminer(consistentTree).hasAllClosed())
                && !(new OpenTreeDeterminer(inconsistentTree).hasSomeOpen());
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }
}
