package com.llat.algorithms;

import com.llat.algorithms.MainOperatorDetector;
import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.NodeFlag;
import com.llat.models.treenode.WffTree;

public final class LogicallyContingentDeterminer {

    /**
     *
     */
    private WffTree wffTree;

    /**
     *
     */
    private WffTree negatedTree;

    public LogicallyContingentDeterminer(WffTree _wffTree) {
        this.wffTree = _wffTree.getChild(0);

        this.negatedTree.addChild(new NegNode());
        this.negatedTree.setFlags(_wffTree.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.negatedTree.getChild(0).addChild(this.wffTree.copy());
    }

    /**
     * @return
     */
    public boolean get() {
        BaseTruthTreeGenerator treeGenerator;
        BaseTruthTreeGenerator negatedTreeGenerator;
        if (this.wffTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.wffTree);
            negatedTreeGenerator = new PropositionalTruthTreeGenerator(this.negatedTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.wffTree);
            negatedTreeGenerator = new PredicateTruthTreeGenerator(this.negatedTree);
        }

        TruthTree truthTree = treeGenerator.get();
        TruthTree negatedTruthTree = negatedTreeGenerator.get();

        // The consistency branch must close, and the right must have at least one open branch.
        return new ClosedTreeDeterminer(truthTree).get()
                && new ClosedTreeDeterminer(negatedTruthTree).get();
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public WffTree getNegatedTree() {
        return this.negatedTree;
    }
}
