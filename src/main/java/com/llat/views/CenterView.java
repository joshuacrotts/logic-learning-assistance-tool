package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CenterView {
    Controller controller;
    AnchorPane parentPane = new AnchorPane();
    VBox vBox = new VBox();


    public CenterView(Controller _controller) {
        this.controller = _controller;
        Pane tree = new ParseTreeView(this.controller).getParentPane();
        this.vBox.getChildren().addAll(new AlgorithmsView(this.controller).getParentPane(), new TruthTableView(this.controller).getParentPane(), tree);
        this.parentPane.getChildren().addAll(vBox);
    }
    public Pane getParentPane() {
        return this.parentPane;
    }

}
