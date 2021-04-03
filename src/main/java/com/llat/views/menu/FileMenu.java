package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.menu.items.ExitItem;
import com.llat.views.menu.items.NewProjectItem;
import com.llat.views.menu.items.OpenItem;
import com.llat.views.menu.items.SettingsItem;
import javafx.scene.control.Menu;

public class FileMenu {
    Controller controller;
    Menu fileMenu = new Menu("File");

    public FileMenu(Controller controller) {
        this.controller = controller;


        // Setting Menu fileMenu properties.
        this.fileMenu.getItems().addAll(new NewProjectItem(this.controller).getItem(), new OpenItem(this.controller).getItem(), new ExportMenu(this.controller).getMenu(), new SettingsItem(this.controller).getItem(), new ExitItem(this.controller).getItem());

    }
    public Menu getMenu(){
        return fileMenu;
    }
}
