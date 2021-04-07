package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;

public class CenterView {
    Controller controller;
    VBox parentPane = new VBox();
    VBox vBox = new VBox();
    ScrollPane scrollPane = new ScrollPane();


    public CenterView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox parentPane properties.
        this.parentPane.setId("centerView");
        // Setting ScrollPane scrollPane properties.
        this.scrollPane.setId("treeTableScrollPane");
        this.scrollPane.setContent(this.vBox);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setFitToWidth(true);
        // Adding children nodes to their parents nodes.
        this.vBox.getChildren().addAll(new TruthTableView(this.controller).getParentPane(), new ParseTreeView(this.controller).getParentPane());
       // this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane(), this.scrollPane);
        this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane());

    }
    public Pane getParentPane() {
        return this.parentPane;
    }

}
