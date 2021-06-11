package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.input.events.UnsolvedFormulaEvent;
import com.llat.models.AlgorithmType;
import com.llat.models.events.RandomGeneratedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.ApplyAlgorithmEvent;
import com.llat.views.events.SolveButtonEvent;
import com.llat.views.menu.ExportMenu;
import com.llat.views.menu.ExportType;

public class ExportMenuInterpreter implements Listener {

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
        // Setting properties for ExportMenu exportMenu.
        this.exportMenu.getMenu().setText(controller.getUiObject().getMenuBar().getExport().getLabel());
        // Setting action events for menu items that throw events to export respective LaTex files.
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXParseTreeItem().getItem(), ExportType.LATEX_PARSE_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXTruthTreeItem().getItem(), ExportType.LATEX_TRUTH_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportLaTeXTruthTableItem().getItem(), ExportType.LATEX_TRUTH_TABLE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFParseTreeMenuItem().getItem(), ExportType.PDF_PARSE_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFTruthTreeMenuItem().getItem(), ExportType.PDF_TRUTH_TREE);
        this.controller.setExportOnAction(this.exportMenu.getExportPDFTruthTableMenuItem().getItem(), ExportType.PDF_TRUTH_TABLE);
        this.disableMenuItems();
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {

        if (_event instanceof ApplyAlgorithmEvent) {
            switch (AlgorithmType.getEnum(((ApplyAlgorithmEvent) _event).getAlgorithmType())) {
                case RANDOM_PREDICATE_FORMULA:
                case RANDOM_PROPOSITIONAL_FORMULA:
                    this.disableMenuItems();
                    break;
                default:
                    this.enableMenuItems();
            }
        }
        if (_event instanceof SolveButtonEvent) {
            this.disableMenuItems();
        }
    }

    public void disableMenuItems () {
        this.exportMenu.getMenu().getItems().forEach( (item) -> {
            item.setDisable(true);
        });
    }

    public void enableMenuItems () {
        this.exportMenu.getExportLaTeXMenu().setDisable(false);
        this.exportMenu.getExportPDFMenu().setDisable(!this.controller.hasNetworkConnection());
    }

}

