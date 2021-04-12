package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.TruthTableInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TruthTableView {

    private final TruthTableInterpreter truthTableInterpreter;
    private final Controller controller;
    private final Stage stage;
    private final VBox parentPane = new VBox();
    private final ScrollPane scrollPane = new ScrollPane();
    private final HBox truthTable = new HBox();

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
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setMaxWidth(Double.MAX_VALUE);
        });

        this.scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.fitToHeightProperty().set(true);

        // Setting HBox truthTable properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.truthTable.setMinWidth(newVal.doubleValue() - 1);
        });

        // Adding children nodes to their parents nodes.
        this.scrollPane.setContent(this.truthTable);
        this.truthTableInterpreter = new TruthTableInterpreter(this.controller, this);
    }

    public HBox getTruthTable() {
        return this.truthTable;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getParentPane() {
        return this.parentPane;
    }
}