package com.llat.input.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class SolvedFormulaEvent implements Event {

    private WffTree wffTree;

    public SolvedFormulaEvent(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

}
