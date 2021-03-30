package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.BicondNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

/**
 *
 */
public class LogicallyEquivalentDeterminer {

    private WffTree combinedTree;

    public LogicallyEquivalentDeterminer(WffTree _wffTreeOne, WffTree _wffTreeTwo) {
        BicondNode bicond = new BicondNode();
        bicond.addChild(_wffTreeOne.getChild(0));
        bicond.addChild(_wffTreeTwo.getChild(0));

        this.combinedTree = new WffTree();
        this.combinedTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.combinedTree.addChild(new NegNode());
        this.combinedTree.getChild(0).addChild(bicond);
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
