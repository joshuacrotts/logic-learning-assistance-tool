package com.llat.views.events;

import com.llat.models.symbols.Symbol;
import com.llat.tools.Event;

public class SymbolDescriptionEvent implements Event {

    /**
     *
     */
    private Symbol symbol;

    public SymbolDescriptionEvent(Symbol _symbol) {
        this.symbol = _symbol;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public void setSymbol(Symbol _symbolInput) {
        this.symbol = _symbolInput;
    }

}
