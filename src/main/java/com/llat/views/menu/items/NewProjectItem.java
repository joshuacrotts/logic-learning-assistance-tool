package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.main.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class NewProjectItem {

    private final Controller controller;
    private final MenuItem newProjectItem;

    public NewProjectItem(Controller controller) {
        this.controller = controller;
        this.newProjectItem = new MenuItem("New Project");
        this.newProjectItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new Window(new Stage());
            }
        });
    }

    public MenuItem getItem() {
        return newProjectItem;
    }
}