package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.RulesAxiomsInterpreter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesAxiomsView {
    Controller controller;
    Stage stage;
    ScrollPane scrollPane = new ScrollPane();
    VBox rulesAxiomsVBox = new VBox();
    Region topFiller = new Region();

    RulesAxiomsInterpreter rulesAxiomsInterpreter;

    public RulesAxiomsView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        // Setting VBox rulesAxiomsVBox settings.
        this.rulesAxiomsVBox.setId("rulesAxiomsVBox");
        this.rulesAxiomsVBox.setAlignment(Pos.TOP_CENTER);
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.rulesAxiomsVBox.setMinWidth(newVal.doubleValue() * .20);
            this.rulesAxiomsVBox.setMaxWidth(newVal.doubleValue() * .20);
        });
        // Setting Region topFiller settings.
        this.rulesAxiomsVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.topFiller.setMinHeight(newVal.doubleValue() * .03);
            this.topFiller.setMaxHeight(newVal.doubleValue() * .03);
        });
        // Setting ScrollPane scrollPane properties.
        this.scrollPane.setId("rulesAxiomsScrollPane");
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setMaxHeight(Double.MAX_VALUE);
        });
        this.scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scrollPane.fitToWidthProperty().set(true);
        this.scrollPane.setPannable(true);
        this.rulesAxiomsVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setMinHeight(newVal.doubleValue() * .95);
            this.scrollPane.setMaxHeight(newVal.doubleValue());
        });
        // Adding children nodes to their parents nodes.
        this.scrollPane.setContent(new AnchorPane());
        this.rulesAxiomsVBox.getChildren().addAll(this.scrollPane);
        VBox.setMargin(this.scrollPane, new Insets(0, 15, 0, 0));
        // Creating interpreter to handle events and actions.
        this.rulesAxiomsInterpreter = new RulesAxiomsInterpreter(this.controller, this);
    }

    public Region getTopFiller() {
        return this.topFiller;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public VBox getParentPane() {
        return this.rulesAxiomsVBox;
    }

}
