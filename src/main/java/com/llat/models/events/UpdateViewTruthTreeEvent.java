package com.llat.models.events;

import com.llat.algorithms.models.TruthTree;
import com.llat.tools.Event;

public class UpdateViewTruthTreeEvent implements Event {

    /**
     *
     */
    private TruthTree truthTree;

    /**
     *
     */
    private boolean isEmpty = false;

    public UpdateViewTruthTreeEvent(TruthTree _truthTree) {
        this.truthTree = _truthTree;
    }

    public UpdateViewTruthTreeEvent() {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public TruthTree getTruthTree() {
        return this.truthTree;
    }

}
