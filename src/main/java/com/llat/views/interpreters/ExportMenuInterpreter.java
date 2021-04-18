package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.views.menu.ExportMenu;
import com.llat.views.menu.ExportType;

public class ExportMenuInterpreter {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final ExportMenu exportMenu;

    public ExportMenuInterpreter(Controller _controller, ExportMenu _exportMenu) {
        this.controller = _controller;
        this.exportMenu = _exportMenu;

        // Setting action events for menu items that throw events to export respective LaTex files.
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXParseTreeItem().getItem(), ExportType.LATEX_PARSE_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXTruthTreeItem().getItem(), ExportType.LATEX_TRUTH_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXTruthTableItem().getItem(), ExportType.LATEX_TRUTH_TABLE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFParseTreeMenuItem(), ExportType.PDF_PARSE_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFTruthTreeMenuItem(), ExportType.PDF_TRUTH_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFTruthTableMenuItem(), ExportType.PDF_TRUTH_TABLE);
    }
}
