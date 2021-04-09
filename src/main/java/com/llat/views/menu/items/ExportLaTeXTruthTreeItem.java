package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExportLaTeXTruthTreeItem {

    private final Controller controller;
    private final MenuItem latexTruthTree;

    public ExportLaTeXTruthTreeItem(Controller controller) {
        this.controller = controller;
        this.latexTruthTree = new MenuItem("LaTeX Truth Tree (PDF)");

        latexTruthTree.setOnAction((ActionEvent t) -> {
            throw new UnsupportedOperationException("LaTeX Truth Tree: Cannot export as LaTeX yet...");
        });
    }

    public MenuItem getItem() {
        return latexTruthTree;
    }
}
