package com.llat.views.menu.items;

import com.llat.controller.Controller;
import javafx.scene.control.MenuItem;

public class LoginItem {
    Controller controller;
    MenuItem newItem;

    public LoginItem(Controller controller) {
        this.controller = controller;
        this.newItem = new MenuItem("Login");


    }

    public MenuItem getItem(){
        return newItem;
    }
}
