package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.ImpNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public final class LogicallyImpliedDeterminer {

    /**
     *
     */
    private final TruthTree truthTree;

    /**
     *
     */
    private final WffTree combinedTree;

    public LogicallyImpliedDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        ImpNode impNode = new ImpNode();
        impNode.addChild(_wffTreeOne.getChild(0));
        impNode.addChild(_wffTreeTwo.getChild(0));

        this.combinedTree = new WffTree();
        this.combinedTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.combinedTree.addChild(new NegNode());
        this.combinedTree.getChild(0).addChild(impNode);

        BaseTruthTreeGenerator treeGenerator;
        if (this.combinedTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.combinedTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.combinedTree);
        }

        this.truthTree = treeGenerator.getTruthTree();
    }

    /**
     * @return
     */
    public boolean isImplied() {
        return new ClosedTreeDeterminer(this.truthTree).hasAllClosed();
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }

    public TruthTree getTruthTree() {
        return this.truthTree;
    }
}
