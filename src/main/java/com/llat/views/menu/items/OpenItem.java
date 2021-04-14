package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class OpenItem {

    private final Controller controller;
    private final MenuItem openItem;

    public OpenItem(Controller controller) {
        this.controller = controller;
        this.openItem = new MenuItem(controller.getUiObject().getMenuBar().getFile().getOpenProject().getLabel());

        this.openItem.setDisable(true);
    }

    public MenuItem getItem() {
        return this.openItem;
    }
}