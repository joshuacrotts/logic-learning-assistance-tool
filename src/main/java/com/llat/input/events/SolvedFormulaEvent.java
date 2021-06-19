package com.llat.input.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

import java.util.ArrayList;

public class SolvedFormulaEvent implements Event {

    private ArrayList<WffTree> wffTree;

    public SolvedFormulaEvent(ArrayList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

    public ArrayList<WffTree> getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(ArrayList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

}
