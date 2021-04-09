package com.llat.views.menu;

import com.llat.controller.Controller;
import javafx.scene.control.Menu;

public class HelpMenu {

    private final Controller controller;
    private final Menu helpMenu;

    public HelpMenu(Controller controller) {
        this.controller = controller;
        this.helpMenu = new Menu("Help");
    }

    public Menu getMenu() {
        return helpMenu;
    }
}
