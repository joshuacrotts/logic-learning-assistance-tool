package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class OpenItem {
    Controller controller;
    MenuItem openItem;

    public OpenItem(Controller controller) {
        this.controller = controller;
        this.openItem = new MenuItem("Open Project");

        openItem.setDisable(true);
    }
    public MenuItem getItem(){
        return openItem;
    }
}