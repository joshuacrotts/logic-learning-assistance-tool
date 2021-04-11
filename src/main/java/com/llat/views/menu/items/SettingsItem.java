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
        this.openItem = new MenuItem("Settings");
        this.openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage settingsStage = new SettingsView(controller).getStage();
                // Set position of second window, related to primary window.
                settingsStage.setX(SettingsItem.this.stage.getX() + 200);
                settingsStage.setY(SettingsItem.this.stage.getY() + 100);
                settingsStage.show();
            }
        });
    }

    public MenuItem getItem() {
        return this.openItem;
    }

    public Stage getStage() {
        return this.stage;
    }
}
