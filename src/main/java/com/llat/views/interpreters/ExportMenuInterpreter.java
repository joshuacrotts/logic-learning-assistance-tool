package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.views.menu.ExportMenu;

public class ExportMenuInterpreter {

    private final Controller controller;
    private final ExportMenu exportMenu;

    public ExportMenuInterpreter(Controller _controller, ExportMenu _exportMenu) {
        this.controller = _controller;
        this.exportMenu = _exportMenu;

        // Setting action events for menu items that throw events to export respective LaTex files.
        this.controller.setExportLatexOnAction(this.exportMenu.getExportLaTeXParseTreeItem().getItem(), ExportMenu.ExportType.LATEX_PARSE_TREE);
        this.controller.setExportLatexOnAction(this.exportMenu.getExportLaTeXTruthTreeItem().getItem(), ExportMenu.ExportType.LATEX_TRUTH_TREE);
        this.controller.setExportLatexOnAction(this.exportMenu.getExportLaTeXTruthTableItem().getItem(), ExportMenu.ExportType.LATEX_TRUTH_TABLE);
    }
}
