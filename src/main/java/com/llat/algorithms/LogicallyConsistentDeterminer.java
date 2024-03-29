package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.AndNode;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicallyConsistentDeterminer {

    /**
     *
     */
    private final WffTree combinedTree;

    private TruthTree combinedTruthTree;

    public LogicallyConsistentDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        // Construct the combined tree, with a biconditional connecting them.
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
    public boolean isConsistent() {
        BaseTruthTreeGenerator truthTreeGenerator;
        if (this.combinedTree.isPropositionalWff()) {
            truthTreeGenerator = new PropositionalTruthTreeGenerator(this.combinedTree);
        } else {
            truthTreeGenerator = new PredicateTruthTreeGenerator(this.combinedTree);
        }

        this.combinedTruthTree = truthTreeGenerator.getTruthTree();
        TruthTree consistentTree = truthTreeGenerator.getTruthTree().getLeft();
        TruthTree inconsistentTree = truthTreeGenerator.getTruthTree().getRight();

        // The consistency branch must close, and the right must have at least one open branch.
        return new OpenTreeDeterminer(inconsistentTree).hasSomeOpen();
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }

    public TruthTree getCombinedTruthTree() {
        return this.combinedTruthTree;
    }
}
