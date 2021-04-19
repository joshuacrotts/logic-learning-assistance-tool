package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportPDFParseTreeItem {

    private final Controller controller;
    private final MenuItem pdfParseTree;

    public ExportPDFParseTreeItem(Controller controller) {
        this.controller = controller;
        this.pdfParseTree = new MenuItem("LaTeX Parse Tree");
    }

    public MenuItem getItem() {
        return this.pdfParseTree;
    }
}
