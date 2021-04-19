package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportLaTeXTruthTreeItem {

    private final Controller controller;
    private final MenuItem latexTruthTree;

    public ExportLaTeXTruthTreeItem(Controller controller) {
        this.controller = controller;
        this.latexTruthTree = new MenuItem("LaTeX Truth Tree");
    }

    public MenuItem getItem() {
        return this.latexTruthTree;
    }

}
