package com.llat.main.view;

import com.llat.main.controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class RulesAxiomsView {
    Controller controller;
    private AnchorPane parentPane = new AnchorPane();

    public RulesAxiomsView(Controller _controller) {
        this.controller = _controller;
        parentPane.setMinHeight(500);
        parentPane.setMinWidth(500);
        Label testLabel = new Label();
        testLabel.setText("Rules/Axioms");
        parentPane.getChildren().add(testLabel);
    }
    public AnchorPane getParentPane() {
        return this.parentPane;
    }
}
