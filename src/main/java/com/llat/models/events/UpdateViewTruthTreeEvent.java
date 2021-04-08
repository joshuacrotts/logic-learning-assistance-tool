package com.llat.models.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class UpdateViewTruthTreeEvent implements Event {
    WffTree wffTree;
    boolean isEmpty = false;

    public UpdateViewTruthTreeEvent(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }
    public UpdateViewTruthTreeEvent() {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

}
