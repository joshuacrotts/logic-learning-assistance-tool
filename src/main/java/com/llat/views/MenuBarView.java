package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarView {

    protected static int menuBarHeight = 45;

    private Controller controller;
    private MenuBar menuBar = new MenuBar();
    private Menu fileMenu = new Menu("File");
    private MenuItem newItem = new MenuItem("New");
    private MenuItem openItem = new MenuItem("Open");
    private MenuItem exportItem = new MenuItem("Export");
    private MenuItem exitItem = new MenuItem("Exit");

    public MenuBarView(Controller _controller) {
        this.controller = _controller;
        // Setting Menu fileMenu properties.
        this.fileMenu.getItems().addAll(this.newItem, this.openItem, this.exportItem, this.exitItem);
        // Setting MenuBar menuBar properties.
        this.menuBar.setMinHeight(MenuBarView.menuBarHeight);
        this.menuBar.setMaxHeight(MenuBarView.menuBarHeight);
        // Adding children nodes to their parents nodes.
        this.menuBar.getMenus().addAll(this.fileMenu);
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

}
