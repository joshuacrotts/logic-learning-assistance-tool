package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.main.Window;
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

            public void handle(ActionEvent event) {
                new Window(new Stage(), 2);
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
