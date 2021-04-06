package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.tools.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class LoginItem {
    Controller controller;
    MenuItem newItem;

    public LoginItem(Controller controller) {
        this.controller = controller;
        this.newItem = new MenuItem("Login");
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.changeViewTo(ViewManager.LOGIN);            }
        });


    }

    public MenuItem getItem(){
        return newItem;
    }
}
