package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportLaTeXTruthTableItem {

    private final Controller controller;
    private final MenuItem latexParseTree;

    public ExportLaTeXTruthTableItem(Controller controller) {
        this.controller = controller;
        this.latexParseTree = new MenuItem("LaTeX Truth Table (.tex)");
    }

    public MenuItem getItem() {
        return this.latexParseTree;
    }

}
