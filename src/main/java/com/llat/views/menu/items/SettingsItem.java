package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.views.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SettingsItem {
    Controller controller;
    Stage stage;
    MenuItem openItem;

    public SettingsItem(Controller controller) {
        this.controller = controller;
        this.stage = this.controller.getStage();
        this.openItem = new MenuItem("Settings");

//        openItem.setDisable(true);
//        this.openItem.setOnAction((value) ->  {
//
//            this.openItem.setText("Clicked!");
//        });
//
        this.openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage settignsStage = new SettingsView(controller).getStage();
                // Set position of second window, related to primary window.
                settignsStage.setX(stage.getX() + 200);
                settignsStage.setY(stage.getY() + 100);

                settignsStage.show();
            }
        });
    }

    public MenuItem getItem(){
        return openItem;
    }
    public Stage getStage(){
        return this.stage;
    }
}
