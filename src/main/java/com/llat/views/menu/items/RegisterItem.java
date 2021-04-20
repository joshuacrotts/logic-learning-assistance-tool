package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.tools.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class RegisterItem {

    private final Controller controller;
    private final MenuItem registerItem;

    public RegisterItem(Controller controller) {
        this.controller = controller;
        this.registerItem = new MenuItem(controller.getUiObject().getMenuBar().getFile().getRegister().getLabel());
        this.registerItem.setDisable(!this.controller.hasNetworkConnection());
        this.registerItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.changeViewTo(ViewManager.REGISTER);
            }
        });
    }

    public MenuItem getItem() {
        return this.registerItem;
    }
}
