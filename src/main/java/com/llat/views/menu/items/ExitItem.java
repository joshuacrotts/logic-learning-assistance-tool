package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExitItem {

    private final Controller controller;
    private final MenuItem exitItem;

    public ExitItem(Controller controller) {
        this.controller = controller;
        this.exitItem = new MenuItem("Exit");

        this.exitItem.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
    }

    public MenuItem getItem() {
        return this.exitItem;
    }
}
