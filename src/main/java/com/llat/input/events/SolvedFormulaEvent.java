package com.llat.input.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import java.util.LinkedList;

public class SolvedFormulaEvent implements Event {

    private LinkedList<WffTree> wffTree;
    public SolvedFormulaEvent(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

    public LinkedList<WffTree> getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

}
