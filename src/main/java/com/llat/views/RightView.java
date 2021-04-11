package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RightView {

    private final Controller controller;
    private final Stage stage;
    private final VBox parentPane = new VBox();
    private final Tab historyTab;
    private final Tab axiomsTab;
    private final TabPane tabPane = new TabPane();
    private final RulesAxiomsView rulesAxiomsView;
    private final HistoryView historyView;

    public RightView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        this.rulesAxiomsView = new RulesAxiomsView(this.controller);
        this.historyView = new HistoryView(this.controller);
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.parentPane.setMinWidth(newVal.doubleValue() * .20);
            this.parentPane.setMaxWidth(newVal.doubleValue() * .20);
        });
        // Setting TabPane tabPane properties.
        this.tabPane.setId("rightView");
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.tabPane.setMinWidth(newVal.doubleValue());
            this.tabPane.setMaxWidth(newVal.doubleValue());
            this.tabPane.setTabMinWidth((newVal.doubleValue() / 2) - 30);
            this.tabPane.setTabMaxWidth((newVal.doubleValue() / 2) - 30);
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.tabPane.setTabMaxHeight(newVal.doubleValue());
        });
        // Setting Tab axiomsTab properties.
        this.axiomsTab = new Tab("Axioms", this.rulesAxiomsView.getParentPane());
        this.axiomsTab.setClosable(false);
        // Setting Tab historyTab properties.
        this.historyTab = new Tab("History", this.historyView.getParentPane());
        this.historyTab.setClosable(false);
        // Setting VBox rulesAxiomsView properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.rulesAxiomsView.getParentPane().setMinWidth(newVal.doubleValue());
            this.rulesAxiomsView.getParentPane().setMaxWidth(newVal.doubleValue());
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.rulesAxiomsView.getParentPane().setMinHeight(newVal.doubleValue());
            this.rulesAxiomsView.getParentPane().setMaxHeight(newVal.doubleValue());
        });
        // Setting VBox historyView properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.historyView.getParentPane().setMinWidth(newVal.doubleValue());
            this.historyView.getParentPane().setMaxWidth(newVal.doubleValue());
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.historyView.getParentPane().setMinHeight(newVal.doubleValue());
            this.historyView.getParentPane().setMaxHeight(newVal.doubleValue());
        });
        // Adding children nodes to their parents.
        this.tabPane.getTabs().addAll(this.axiomsTab, this.historyTab);
        this.parentPane.getChildren().addAll(this.tabPane);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }
}
