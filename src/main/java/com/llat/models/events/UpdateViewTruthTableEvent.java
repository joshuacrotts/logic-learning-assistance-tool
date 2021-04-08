package com.llat.models.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class UpdateViewTruthTableEvent implements Event {
    WffTree wffTree;
    boolean isEmpty = false;
    public UpdateViewTruthTableEvent (WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    public UpdateViewTruthTableEvent () {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public WffTree getWffTree () {
        return this.wffTree;
    }

}
