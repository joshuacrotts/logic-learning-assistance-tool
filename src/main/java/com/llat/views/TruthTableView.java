package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.TruthTableInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TruthTableView {

    private Controller controller;
    private Stage stage;
    private VBox parentPane = new VBox();
    private ScrollPane scrollPane = new ScrollPane();
    private HBox truthTable = new HBox();
    TruthTableInterpreter truthTableInterpreter;
    public TruthTableView(Controller _controller) {
        this.controller = _controller;
        this.stage = _controller.getStage();
        // Setting VBox parentPane properties.
        this.parentPane.setAlignment(Pos.TOP_CENTER);
        this.parentPane.setId("truthTableParentPane");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.parentPane.minWidth(newVal.doubleValue() * .60);
            this.parentPane.maxWidth(newVal.doubleValue() * .60);
        });
        // Setting scrollPane scrollPane properties.
        this.scrollPane.setId("truthTableScrollPane");
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> { this.scrollPane.setMaxWidth(Double.MAX_VALUE); });
        this.scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.fitToHeightProperty().set(true);
        // Setting HBox truthTable properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> { this.truthTable.setMinWidth(newVal.doubleValue() - 1); });
        // Adding children nodes to their parents nodes.
        this.scrollPane.setContent(this.truthTable);
        this.parentPane.getChildren().addAll(this.scrollPane);
        this.truthTableInterpreter = new TruthTableInterpreter(this.controller, this);
    }

    public HBox getTruthTable() {
        return this.truthTable;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getParentPane() {
        return parentPane;
    }

}