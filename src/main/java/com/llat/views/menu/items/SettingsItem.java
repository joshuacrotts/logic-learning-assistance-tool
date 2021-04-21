package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.views.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SettingsItem {

    private final Controller controller;
    private final MenuItem openItem;
    private final Stage stage;

    public SettingsItem(Controller controller) {
        this.controller = controller;
        this.stage = this.controller.getStage();
        this.openItem = new MenuItem(controller.getUiObject().getMenuBar().getFile().getSettings().getLabel());
        this.openItem.setOnAction((event) -> {
            Stage settingsStage = new SettingsView(controller).getSettingsStage();
            settingsStage.setX((this.stage.getX()) + this.stage.getScene().getX() + (this.stage.getWidth() / 2) - (settingsStage.getWidth() / 2));
            settingsStage.setY((this.stage.getY()) + this.stage.getScene().getY() +  (this.stage.getHeight() / 2) - (settingsStage.getHeight() / 2));
            settingsStage.showAndWait();
        });
    }

    public MenuItem getItem() {
        return this.openItem;
    }

    public Stage getStage() {
        return this.stage;
    }
}
