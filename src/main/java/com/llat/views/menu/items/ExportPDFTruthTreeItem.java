package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportPDFTruthTreeItem {

    private final Controller controller;
    private final MenuItem pdfTruthTree;

    public ExportPDFTruthTreeItem(Controller controller) {
        this.controller = controller;
        this.pdfTruthTree = new MenuItem("LaTeX Truth Tree");
    }

    public MenuItem getItem() {
        return this.pdfTruthTree;
    }
}
