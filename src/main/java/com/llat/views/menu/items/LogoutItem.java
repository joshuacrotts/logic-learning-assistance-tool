package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.main.Window;
import com.llat.models.localstorage.credentials.CredentialsAdaptor;
import com.llat.models.localstorage.credentials.CredentialsObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class LogoutItem {

    private final Controller controller;
    private final MenuItem loginItem;

    public LogoutItem(Controller controller) {
        this.controller = controller;
        this.loginItem = new MenuItem("Logout");
        this.loginItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                CredentialsAdaptor ca = new CredentialsAdaptor();
                CredentialsObject co = new CredentialsObject("", "11");
                ca.update(co);
                controller.getStage().close();
                new Window(new Stage());

            }
        });
    }

    public MenuItem getItem() {
        return this.loginItem;
    }
}
