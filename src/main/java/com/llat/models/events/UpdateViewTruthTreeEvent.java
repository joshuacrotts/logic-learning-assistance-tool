package com.llat.models.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class UpdateViewTruthTreeEvent implements Event {

    /**
     *
     */
    private WffTree wffTree;

    /**
     *
     */
    private boolean isEmpty;

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
