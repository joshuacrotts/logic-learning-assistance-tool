package com.llat.models.events;

import com.llat.algorithms.models.NDWffTree;
import com.llat.tools.Event;

import java.util.ArrayList;

public class UpdateNaturalDeductionEvent implements Event {
    private ArrayList<NDWffTree> ndWffTrees;
    private boolean isEmpty = false;

    public UpdateNaturalDeductionEvent() {
        this.isEmpty = true;
    }

    public UpdateNaturalDeductionEvent (ArrayList<NDWffTree> _ndWffTree) {
        this.ndWffTrees = _ndWffTree;
    }

    public ArrayList<NDWffTree> getNdWffTrees() {
        return ndWffTrees;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setNdWffTrees(ArrayList<NDWffTree> ndWffTrees) {
        this.ndWffTrees = ndWffTrees;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

}
