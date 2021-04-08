package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
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
        this.wffTree = _wffTree.copy();
        this.wffTree.setFlags(_wffTree.getFlags());

        // Create the negated expression.
        this.negatedTree = new WffTree();
        this.negatedTree.addChild(new NegNode());
        this.negatedTree.setFlags(_wffTree.isPropositionalWff() ? NodeFlag.PROPOSITIONAL : NodeFlag.PREDICATE);
        this.negatedTree.getChild(0).addChild(this.wffTree.getChild(0).copy());
    }

    /**
     * @return
     */
    public boolean isContingent() {
        BaseTruthTreeGenerator treeGenerator;
        BaseTruthTreeGenerator negatedTreeGenerator;
        if (this.wffTree.isPropositionalWff() && this.negatedTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.wffTree);
            negatedTreeGenerator = new PropositionalTruthTreeGenerator(this.negatedTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.wffTree);
            negatedTreeGenerator = new PredicateTruthTreeGenerator(this.negatedTree);
        }

        TruthTree truthTree = treeGenerator.getTruthTree();
        TruthTree negatedTruthTree = negatedTreeGenerator.getTruthTree();

        // The consistency branch must close, and the right must have at least one open branch.
        return (new OpenTreeDeterminer(truthTree).hasSomeOpen())
                && (new OpenTreeDeterminer(negatedTruthTree).hasSomeOpen());
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public WffTree getNegatedTree() {
        return this.negatedTree;
    }
}
