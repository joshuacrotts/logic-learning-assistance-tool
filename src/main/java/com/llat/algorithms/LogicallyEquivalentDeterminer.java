package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicallyEquivalentDeterminer {

    private WffTree combinedTree;

    public LogicallyEquivalentDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        BicondNode bicond = new BicondNode();
        bicond.addChild(_wffTreeOne);
        bicond.addChild(_wffTreeTwo);

        this.combinedTree = new WffTree();
        this.combinedTree.addChild(new NegNode());
        this.combinedTree.getChild(0).addChild(bicond);
    }

    /**
     * @return
     */
    public boolean get() {
        PropositionalTruthTreeGenerator tree = new PropositionalTruthTreeGenerator(this.combinedTree);
        TruthTree tt = tree.get();
        return new ClosedTreeDeterminer(tt).get();
    }
}
