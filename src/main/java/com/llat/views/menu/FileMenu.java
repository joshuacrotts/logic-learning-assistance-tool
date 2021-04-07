package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.menu.items.*;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class FileMenu {
    Controller controller;
    Menu fileMenu = new Menu("File");

    public FileMenu(Controller controller) {
        this.controller = controller;


        // Setting Menu fileMenu properties.
        this.fileMenu.getItems().addAll(new NewProjectItem(this.controller).getItem(), new OpenItem(this.controller).getItem(), new ExportMenu(this.controller).getMenu(), new SeparatorMenuItem(), new LoginItem(this.controller).getItem(), new RegisterItem(this.controller).getItem(), new SeparatorMenuItem(), new SettingsItem(this.controller).getItem(), new SeparatorMenuItem(), new ExitItem(this.controller).getItem());

    }

    public Menu getMenu() {
        return fileMenu;
    }
}
