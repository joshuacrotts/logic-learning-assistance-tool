package com.llat.algorithms;

import com.llat.algorithms.MainOperatorDetector;
import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

public final class LogicallyContradictoryDeterminer {

    /**
     *
     */
    private WffTree combinedTree;

    public LogicallyContradictoryDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
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
        BaseTruthTreeGenerator treeGenerator;
        if (this.combinedTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.combinedTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.combinedTree);
        }
        return new ClosedTreeDeterminer(treeGenerator.get()).get();
    }
}
