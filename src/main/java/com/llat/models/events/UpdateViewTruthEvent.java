package com.llat.models.events;

import com.llat.tools.Event;

public class UpdateViewTruthEvent implements Event {
    boolean truthValue;
    boolean isEmpty = false;
    public UpdateViewTruthEvent (boolean _truthValue) {
        this.truthValue = _truthValue;
    }
    public UpdateViewTruthEvent () {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public boolean getTruthValue() {
        return this.truthValue;
    }
}
