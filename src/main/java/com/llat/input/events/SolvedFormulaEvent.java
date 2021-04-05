package com.llat.input.events;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

import java.util.LinkedHashSet;

public class SolvedFormulaEvent implements Event {

    private WffTree wffTree;
    private TruthTableGenerator propositionalTruthTreeGenerator;
    public SolvedFormulaEvent(WffTree _wffTree) {
        this.wffTree = _wffTree;
        this.propositionalTruthTreeGenerator = new TruthTableGenerator(this.wffTree);
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

}
