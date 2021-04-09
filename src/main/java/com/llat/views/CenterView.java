package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.CenterViewInterpreter;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CenterView {
    Controller controller;
    VBox parentPane = new VBox();
    TabPane tabPane;
    CenterViewInterpreter centerViewInterpreter;

    public CenterView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox parentPane properties.
        this.parentPane.setId("centerView");
        // Setting TabPane tabPane properties.
        this.tabPane = new TabPane();
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getTabs().addAll(new Tab("Truth Tree"), new Tab("Parse Tree"), new Tab("Truth Table"));
        this.tabPane.getTabs().get(0).setContent(new TruthTreeView(this.controller).getParentPane());
        this.tabPane.getTabs().get(1).setContent(new ParseTreeView(this.controller).getParentPane());
        this.tabPane.getTabs().get(2).setContent(new TruthTableView(this.controller).getParentPane());
        // Adding children nodes to their parents nodes.
        // this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane(), this.scrollPane);
        this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane(), this.tabPane);
        this.centerViewInterpreter = new CenterViewInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }

}
