package com.llat.views.menu;

import com.llat.controller.Controller;
import com.llat.views.menu.items.*;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class FileMenu {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final Menu fileMenu;

    public FileMenu(Controller controller) {
        this.controller = controller;
        this.fileMenu = new Menu(controller.getUiObject().getMenuBar().getFile().getLabel());

        // Setting Menu fileMenu properties.
        if (this.controller.getUser() == null){
            this.fileMenu.getItems().addAll(
                    new NewProjectItem(this.controller).getItem(),
                    new OpenItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new LoginItem(this.controller).getItem(),
                    new RegisterItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new SettingsItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new ExitItem(this.controller).getItem());
        }else{
            this.fileMenu.getItems().addAll(
                    new NewProjectItem(this.controller).getItem(),
                    new OpenItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new LogoutItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new SettingsItem(this.controller).getItem(),
                    new SeparatorMenuItem(),
                    new ExitItem(this.controller).getItem());
        }
    }

    public Menu getMenu() {
        return this.fileMenu;
    }
}
