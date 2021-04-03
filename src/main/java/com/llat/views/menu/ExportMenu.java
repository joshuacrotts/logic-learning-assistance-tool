package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.menu.items.ExportLatexItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ExportMenu {
    Controller controller;
    Menu exportMenu = new Menu("Export");

    public ExportMenu(Controller controller) {
        this.controller = controller;

        // Setting export menu
        MenuItem latexItem = new ExportLatexItem(this.controller).getItem();
        exportMenu.getItems().add(latexItem);

    }
    public Menu getMenu(){
        return exportMenu;
    }
}
