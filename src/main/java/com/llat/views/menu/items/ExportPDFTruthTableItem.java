package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class ExportPDFTruthTableItem {

    private final Controller controller;
    private final MenuItem pdfTruthTable;

    public ExportPDFTruthTableItem(Controller controller) {
        this.controller = controller;
        this.pdfTruthTable = new MenuItem("LaTeX Truth Table");
    }

    public MenuItem getItem() {
        return this.pdfTruthTable;
    }
}
