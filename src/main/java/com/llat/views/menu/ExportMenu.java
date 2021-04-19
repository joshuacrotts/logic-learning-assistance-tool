package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.interpreters.ExportMenuInterpreter;
import com.llat.views.menu.items.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 */
public class ExportMenu {

    private final Controller controller;
    private final Menu exportMenu;
    private final ExportMenuInterpreter exportMenuInterpreter;
    private final ExportLaTeXTruthTableItem exportLaTeXTruthTableItem;
    private final ExportLaTeXParseTreeItem exportLaTeXParseTreeItem;
    private final ExportLaTeXTruthTreeItem exportLaTeXTruthTreeItem;
    private final ExportPDFTruthTableItem exportPDFTruthTableMenuItem;
    private final ExportPDFParseTreeItem exportPDFParseTreeMenuItem;
    private final ExportPDFTruthTreeItem exportPDFTruthTreeMenuItem;

    private final Menu exportLaTeXMenu;
    private final Menu exportPDFMenu;

    public ExportMenu(Controller controller) {
        this.controller = controller;
        this.exportMenu = new Menu(controller.getUiObject().getMenuBar().getExport().getLabel());
        this.exportLaTeXMenu = new Menu("Export as LaTeX (.tex)");
        this.exportPDFMenu = new Menu("Export as PDF (.pdf)");
        this.exportLaTeXTruthTableItem = new ExportLaTeXTruthTableItem(this.controller);
        this.exportLaTeXParseTreeItem = new ExportLaTeXParseTreeItem(this.controller);
        this.exportLaTeXTruthTreeItem = new ExportLaTeXTruthTreeItem(this.controller);
        this.exportPDFTruthTableMenuItem = new ExportPDFTruthTableItem(this.controller);
        this.exportPDFParseTreeMenuItem = new ExportPDFParseTreeItem(this.controller);
        this.exportPDFTruthTreeMenuItem = new ExportPDFTruthTreeItem(this.controller);

        // Add sub-submenus to the submenu.
        this.exportLaTeXMenu.getItems().addAll(this.exportLaTeXTruthTableItem.getItem(), this.exportLaTeXParseTreeItem.getItem(), this.exportLaTeXTruthTreeItem.getItem());
        this.exportPDFMenu.getItems().addAll(this.exportPDFTruthTableMenuItem.getItem(), this.exportPDFParseTreeMenuItem.getItem(), this.exportPDFTruthTreeMenuItem.getItem());

        // Adding children nodes to their parents nodes.
        this.exportMenu.getItems().addAll(this.exportLaTeXMenu, this.exportPDFMenu);

        // Creating interpreter to handle events and actions.
        this.exportMenuInterpreter = new ExportMenuInterpreter(this.controller, this);
    }

    public Menu getMenu() {
        return this.exportMenu;
    }

    public Menu getExportLaTeXMenu() {
        return this.exportLaTeXMenu;
    }

    public Menu getExportPDFMenu() {
        return this.exportPDFMenu;
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

    public ExportPDFTruthTableItem getExportPDFTruthTableMenuItem() {
        return exportPDFTruthTableMenuItem;
    }

    public ExportPDFParseTreeItem getExportPDFParseTreeMenuItem() {
        return exportPDFParseTreeMenuItem;
    }

    public ExportPDFTruthTreeItem getExportPDFTruthTreeMenuItem() {
        return exportPDFTruthTreeMenuItem;
    }

}
