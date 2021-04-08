package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.menu.FileMenu;
import javafx.scene.control.MenuBar;

public class MenuBarView {

    /**
     *
     */
    private static int menuBarHeight = 25;

    /**
     *
     */
    private Controller controller;

    /**
     *
     */
    private MenuBar menuBar = new MenuBar();

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
