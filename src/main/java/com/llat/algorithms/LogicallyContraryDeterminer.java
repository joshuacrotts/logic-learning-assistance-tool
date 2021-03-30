package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.*;

/**
 *
 */
public class LogicallyContraryDeterminer {

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
    public boolean get() {
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
        return new ClosedTreeDeterminer(consistentTree).get()
                && !(new ClosedTreeDeterminer(inconsistentTree).get())
                && !(new OpenTreeDeterminer(inconsistentTree).get());
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }
}
