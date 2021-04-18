package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.interpreters.ExportMenuInterpreter;
import com.llat.views.menu.items.ExportLaTeXParseTreeItem;
import com.llat.views.menu.items.ExportLaTeXTruthTableItem;
import com.llat.views.menu.items.ExportLaTeXTruthTreeItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ExportMenu {

    private final Controller controller;
    private final Menu exportMenu;
    private final ExportMenuInterpreter exportMenuInterpreter;
    private final ExportLaTeXTruthTableItem exportLaTeXTruthTableItem;
    private final ExportLaTeXParseTreeItem exportLaTeXParseTreeItem;
    private final ExportLaTeXTruthTreeItem exportLaTeXTruthTreeItem;
    private final MenuItem exportPDFTruthTableMenuItem = new MenuItem("PDF Truth Table (.pdf)");
    private final MenuItem exportPDFParseTreeMenuItem = new MenuItem("PDF Parse Tree (.pdf)");
    private final MenuItem exportPDFTruthTreeMenuItem = new MenuItem("PDF Truth Tree (.pdf)");

    public ExportMenu(Controller controller) {
        this.controller = controller;
        this.exportMenu = new Menu(controller.getUiObject().getMenuBar().getExport().getLabel());
        // Initializing ExportLaTeXTruthTableItem exportLaTeXTruthTableItem.
        this.exportLaTeXTruthTableItem = new ExportLaTeXTruthTableItem(this.controller);
        // Initializing ExportLaTeXParseTreeItem exportLaTeXParseTreeItem
        this.exportLaTeXParseTreeItem = new ExportLaTeXParseTreeItem(this.controller);
        // Initializing ExportLaTeXTruthTreeItem exportLaTeXTruthTreeItem
        this.exportLaTeXTruthTreeItem = new ExportLaTeXTruthTreeItem(this.controller);
        // Setting
        // Adding children nodes to their parents nodes.
        this.exportMenu.getItems().addAll(this.exportLaTeXTruthTableItem.getItem(), this.exportLaTeXParseTreeItem.getItem(), this.exportLaTeXTruthTreeItem.getItem(),
                                          this.exportPDFTruthTableMenuItem, this.exportPDFParseTreeMenuItem, this.exportPDFTruthTreeMenuItem);
        // Creating interpreter to handle events and actions.
        this.exportMenuInterpreter = new ExportMenuInterpreter(this.controller, this);
    }

    public Menu getMenu() {
        return this.exportMenu;
    }

    public ExportLaTeXTruthTableItem getExportLaTeXTruthTableItem() {
        return this.exportLaTeXTruthTableItem;
    }

    public ExportLaTeXParseTreeItem getExportLaTeXParseTreeItem() {
        return this.exportLaTeXParseTreeItem;
    }

    public ExportLaTeXTruthTreeItem getExportLaTeXTruthTreeItem() {
        return this.exportLaTeXTruthTreeItem;
    }

    public MenuItem getExportPDFTruthTableMenuItem() {
        return exportPDFTruthTableMenuItem;
    }

    public MenuItem getExportPDFParseTreeMenuItem() {
        return exportPDFParseTreeMenuItem;
    }

    public MenuItem getExportPDFTruthTreeMenuItem() {
        return exportPDFTruthTreeMenuItem;
    }

}
