package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExitItem {
    Controller controller;
    MenuItem exitItem;

    public ExitItem(Controller controller) {
        this.controller = controller;
        this.exitItem = new MenuItem("Exit");

        exitItem.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
    }

    public MenuItem getItem() {
        return exitItem;
    }
}
