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
public class LogicalTautologyDeterminer {

    private WffTree wffTree;

    public LogicalTautologyDeterminer(WffTree _wffTreeOne) {
        this.wffTree = new WffTree();
        this.wffTree.setFlags(_wffTreeOne.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.wffTree.addChild(new NegNode());
        this.wffTree.getChild(0).addChild(_wffTreeOne.getChild(0));
    }

    /**
     * @return
     */
    public boolean get() {
        BaseTruthTreeGenerator treeGenerator;
        if (this.wffTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.wffTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.wffTree);
        }
        TruthTree tt = treeGenerator.get();
        return new ClosedTreeDeterminer(tt).get();
    }

    public WffTree getTree() {
        return this.wffTree;
    }
}
