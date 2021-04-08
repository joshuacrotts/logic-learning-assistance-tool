package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.menu.FileMenu;
import javafx.scene.control.MenuBar;

public class MenuBarView {
    Controller controller;
    MenuBar menuBar = new MenuBar();
    static int menuBarHeight = 25;

    public MenuBarView(Controller _controller) {
        this.controller = _controller;
        // Setting Menu fileMenu properties.
        this.menuBar.setMinHeight(MenuBarView.menuBarHeight);
        this.menuBar.setMaxHeight(MenuBarView.menuBarHeight);
        // Adding children nodes to their parents nodes.
        this.menuBar.getMenus().addAll(new FileMenu(this.controller).getMenu());

    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

}
