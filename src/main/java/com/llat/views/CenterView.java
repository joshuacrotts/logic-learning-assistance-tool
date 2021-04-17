package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.CenterViewInterpreter;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CenterView {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final VBox parentPane = new VBox();

    /**
     *
     */
    private final TabPane tabPane;

    /**
     *
     */
    private final CenterViewInterpreter centerViewInterpreter;

    public CenterView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox parentPane properties.
        this.parentPane.setId("centerView");
        // Setting TabPane tabPane properties.
        this.tabPane = new TabPane();
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getTabs().addAll(new Tab(this.controller.getUiObject().getMainView().getMainViewLabels().getTruthTreeLabel()), new Tab(this.controller.getUiObject().getMainView().getMainViewLabels().getParseTreeLabel()), new Tab(this.controller.getUiObject().getMainView().getMainViewLabels().getTruthTableLabel()));
        this.tabPane.getTabs().get(0).setContent(new TruthTreeView(this.controller).getParentPane());
        this.tabPane.getTabs().get(1).setContent(new ParseTreeView(this.controller).getParentPane());
        this.tabPane.getTabs().get(2).setContent(new TruthTableView(this.controller).getParentPane());
        // Adding children nodes to their parents nodes.
        // this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane(), this.scrollPane);
        this.parentPane.getChildren().addAll(new AlgorithmSelectionView(this.controller).getParentPane(), this.tabPane);
        this.centerViewInterpreter = new CenterViewInterpreter(this.controller, this);
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            ((Pane) this.tabPane.getTabs().get(0).getContent()).setMinHeight(newVal.doubleValue());
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            ((Pane) this.tabPane.getTabs().get(1).getContent()).setMinHeight(newVal.doubleValue());
        });
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }
}
