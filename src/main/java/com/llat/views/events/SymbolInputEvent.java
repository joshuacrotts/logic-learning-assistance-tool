package com.llat.views.events;

import com.llat.tools.Event;

public class SymbolInputEvent implements Event {

    /**
     *
     */
    private String symbolInput;

    public SymbolInputEvent(String _symbolInput) {
        this.symbolInput = _symbolInput;
    }

    public String getSymbolInput() {
        return this.symbolInput;
    }

    public void setSymbolInput(String _symbolInput) {
        this.symbolInput = _symbolInput;
    }

}
