package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportLaTeXParseTreeItem {
    private final Controller controller;
    private final MenuItem latexParseTree;

    public ExportLaTeXParseTreeItem(Controller controller) {
        this.controller = controller;
        this.latexParseTree = new MenuItem("LaTeX Parse Tree (PDF)");
    }

    public MenuItem getItem() {
        return this.latexParseTree;
    }

}
