package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.main.App;
import com.llat.main.Window;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class NewProjectItem {

    private final Controller controller;
    private final MenuItem newProjectItem;

    public NewProjectItem(Controller controller) {
        this.controller = controller;
        this.newProjectItem = new MenuItem(controller.getUiObject().getMenuBar().getFile().getNewProject().getLabel());

        this.newProjectItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.getStage().close();
                new Window(new Stage());
            }
        });
    }

    public MenuItem getItem() {
        return this.newProjectItem;
    }
}