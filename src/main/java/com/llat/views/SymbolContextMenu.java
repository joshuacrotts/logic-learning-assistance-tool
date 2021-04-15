package com.llat.views;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class SymbolContextMenu extends ContextMenu {

    /**
     *
     */
    private SymbolButton symbolButton;

    public SymbolContextMenu(SymbolButton _button) {
        this.symbolButton = _button;
        MenuItem titleItem = new MenuItem("Alternate Symbols:");
        titleItem.setDisable(true);
        this.getItems().add(titleItem);

        // Iterate through and replace the current symbol if we choose another.
        for (int i = 0; i < this.symbolButton.getDefaultSymbol().getSymbol().getAllSymbols().size(); i++) {
            String symbol = this.symbolButton.getDefaultSymbol().getSymbol().getAllSymbols().get(i);
            MenuItem menu = new MenuItem(symbol);
            menu.setOnAction((e) -> {
                _button.getDefaultSymbol().getSymbol().setApplied(symbol);
                _button.setText(symbol);
            });
            this.getItems().add(menu);
        }
    }
}
