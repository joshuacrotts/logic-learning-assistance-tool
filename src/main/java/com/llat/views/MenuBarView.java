package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.menu.FileMenu;
import javafx.scene.control.MenuBar;

public class MenuBarView {
    Controller controller;
    MenuBar menuBar = new MenuBar();


    public MenuBarView(Controller _controller) {
        this.controller = _controller;

        // Setting MenuBar menuBar properties.
        this.menuBar.getMenus().addAll(new FileMenu(this.controller).getMenu());

    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

}
