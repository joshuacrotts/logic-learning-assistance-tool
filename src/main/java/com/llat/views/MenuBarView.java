package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.menu.ExportMenu;
import com.llat.views.menu.FileMenu;
import com.llat.views.menu.HelpMenu;
import javafx.scene.control.MenuBar;

public class MenuBarView {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final MenuBar menuBar = new MenuBar();

    public MenuBarView(Controller _controller) {
        this.controller = _controller;

        this.menuBar.setId("topMenuBar");
        // Adding children nodes to their parents nodes.
        this.menuBar.getMenus().addAll(
                new FileMenu(this.controller).getMenu(),
                new ExportMenu(this.controller).getMenu(),
                new HelpMenu(this.controller).getMenu());

        // Check the operating system and adjust the menubar accordingly.
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            this.menuBar.useSystemMenuBarProperty().set(true);
        }
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }
}
