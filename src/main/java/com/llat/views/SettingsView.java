package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsView {
    private Controller controller;
    private AnchorPane parentPane = new AnchorPane();
    private Stage stage;

    public SettingsView(Controller controller) {
        this.controller = controller;
    }

    public AnchorPane getParentPane() {
        return this.parentPane;
    }
}
