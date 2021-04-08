package com.llat.views.events;

import com.llat.tools.Event;

public class ApplyAlgorithmEvent implements Event {
    String algorithmType;

    public ApplyAlgorithmEvent(String _algorithmType) {
        this.algorithmType = _algorithmType;
    }

    public String getAlgorithmType() {
        return this.algorithmType;
    }
}
