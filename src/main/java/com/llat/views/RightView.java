package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RightView {
    Controller controller;
    Stage stage;
    ScrollPane scrollPane = new ScrollPane();
    Tab historyView;
    Tab axiomsView;
    AnchorPane parentPane = new AnchorPane();
    TabPane tabPane = new TabPane();


    public RightView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
//        this.parentPane.setStyle("-fx-background-color: black");
        // Setting tabPane rulesAxiomsVBox settings.
        this.tabPane.setId("rightView");
        this.tabPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            tabPane.setTabMinWidth(newVal.doubleValue() * .35);
            tabPane.setTabMaxWidth(newVal.doubleValue() * .35);
        });
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.tabPane.setMinWidth(newVal.doubleValue() * .20);
            this.tabPane.setMaxWidth(newVal.doubleValue() * .20);
        });
        this.stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.tabPane.setMinHeight(newVal.doubleValue() * .88);
            this.tabPane.setMaxHeight(newVal.doubleValue());
        });
        // Setting ScrollPane scrollPane properties.
        this.scrollPane.setId("rulesAxiomsScrollPane");
        this.stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setMaxHeight(Double.MAX_VALUE);
        });
         this.axiomsView = new Tab("Axioms", new RulesAxiomsView(this.controller).getParentPane());
        this.axiomsView.setClosable(false);
        this.historyView = new Tab("History", new HistoryView(this.controller).getParentPane());
        this.historyView.setClosable(false);
        this.tabPane.getTabs().addAll(axiomsView, historyView);
        this.parentPane.getChildren().addAll(tabPane);
    }
    public Pane getParentPane() {
        return this.parentPane;
    }

}
