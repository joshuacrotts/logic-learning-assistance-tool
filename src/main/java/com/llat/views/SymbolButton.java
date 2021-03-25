package com.llat.views;

import com.llat.models.symbols.Symbol;
import javafx.scene.control.Button;

public class SymbolButton extends Button {
    private Symbol symbol;

    public SymbolButton() {}

    public SymbolButton(Symbol _defaultSymbol) {
        super(_defaultSymbol.getSymbol().getApplied());
        this.symbol = _defaultSymbol;
    }

    public void setDefaultSymbol (Symbol _defaultSymbol) { this.symbol = _defaultSymbol; }

    public Symbol getDefaultSymbol () { return this.symbol; }

}
