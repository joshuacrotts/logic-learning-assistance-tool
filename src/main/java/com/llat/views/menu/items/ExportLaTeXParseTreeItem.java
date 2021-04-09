package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExportLaTeXParseTreeItem {

    private final Controller controller;
    private final MenuItem latexParseTree;

    public ExportLaTeXParseTreeItem(Controller controller) {
        this.controller = controller;
        this.latexParseTree = new MenuItem("LaTeX Parse Tree (PDF)");

        this.latexParseTree.setOnAction((ActionEvent t) -> {
            throw new UnsupportedOperationException("LaTeX Parse Tree: Cannot export as LaTeX yet...");
        });
    }

    public MenuItem getItem() {
        return latexParseTree;
    }
}
