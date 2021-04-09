package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExportLaTeXTruthTableItem {

    private final Controller controller;
    private final MenuItem latexParseTree;

    public ExportLaTeXTruthTableItem(Controller controller) {
        this.controller = controller;
        this.latexParseTree = new MenuItem("LaTeX Truth Table (PDF)");

        this.latexParseTree.setOnAction((ActionEvent t) -> {
            throw new UnsupportedOperationException("LaTeX Truth Table: Cannot export as LaTeX yet...");
        });
    }

    public MenuItem getItem() {
        return latexParseTree;
    }
}
