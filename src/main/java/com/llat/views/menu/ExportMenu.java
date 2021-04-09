package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.menu.items.ExportLaTeXParseTreeItem;
import com.llat.views.menu.items.ExportLaTeXTruthTableItem;
import com.llat.views.menu.items.ExportLaTeXTruthTreeItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ExportMenu {

    private final Controller controller;
    private final Menu exportMenu;

    public ExportMenu(Controller controller) {
        this.controller = controller;
        this.exportMenu = new Menu("Export");

        // Setting export menu
        MenuItem latexTruthTableItem = new ExportLaTeXTruthTableItem(this.controller).getItem();
        MenuItem latexParseTreeItem = new ExportLaTeXParseTreeItem(this.controller).getItem();
        MenuItem latexTruthTreeItem = new ExportLaTeXTruthTreeItem(this.controller).getItem();
        this.exportMenu.getItems().addAll(latexTruthTableItem, latexParseTreeItem, latexTruthTreeItem);

    }

    public Menu getMenu() {
        return exportMenu;
    }
}
