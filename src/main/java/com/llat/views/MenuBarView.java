package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarView {
    Controller controller;
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    MenuItem newItem = new MenuItem("New");
    MenuItem openItem = new MenuItem("Open");
    MenuItem exportItem = new MenuItem("Export");
    MenuItem exitItem = new MenuItem("Exit");

    public MenuBarView(Controller _controller) {
        this.controller = _controller;
        // Setting Menu fileMenu properties.
        this.fileMenu.getItems().addAll(this.newItem, this.openItem, this.exportItem, this.exitItem);
        // Setting MenuBar menuBar properties.
        this.menuBar.getMenus().addAll(this.fileMenu);
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

}
