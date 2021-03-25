package com.llat.views.events;

import com.llat.tools.Event;

public class SymbolDescriptionEvent implements Event {
    private String symbolInput;

    public SymbolDescriptionEvent (String _symbolInput) { this.symbolInput = _symbolInput; }

    public void setSymbolInput (String _symbolInput) { this.symbolInput = _symbolInput; }

    public String getSymbolInput () { return this.symbolInput; }

}
