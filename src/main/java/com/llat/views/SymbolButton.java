package com.llat.views;

import javafx.scene.control.Button;

public class SymbolButton extends Button {
    private String defaultSymbol = "";

    public SymbolButton() {}

    public SymbolButton(String _defaultSymbol) {
        super(_defaultSymbol);
        this.defaultSymbol = _defaultSymbol;
    }

    public void setDefaultSymbol (String _defaultSymbol) { this.defaultSymbol = _defaultSymbol; }

    public String getDefaultSymbol () { return this.defaultSymbol; }

}
