package com.llat.input.events;

import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class SolvedFormulaEvent implements Event {

    private WffTree wffTree;
    private TruthTableGenerator propositionalTruthTreeGenerator;
    public SolvedFormulaEvent(WffTree _wffTree) {
        this.wffTree = _wffTree;
        this.propositionalTruthTreeGenerator = new TruthTableGenerator(this.wffTree);
        this.propositionalTruthTreeGenerator.getTruthTable();
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

}
