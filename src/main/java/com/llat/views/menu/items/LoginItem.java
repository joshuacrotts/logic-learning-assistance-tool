package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.tools.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class LoginItem {

    private final Controller controller;
    private final MenuItem loginItem;

    public LoginItem(Controller controller) {
        this.controller = controller;
        this.loginItem = new MenuItem("Login");
        this.loginItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.changeViewTo(ViewManager.LOGIN);
            }
        });
    }

    public MenuItem getItem() {
        return loginItem;
    }
}
