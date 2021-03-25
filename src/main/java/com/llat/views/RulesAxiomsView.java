package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.RulesAxiomsInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesAxiomsView {
    Controller controller;
    Stage stage;
    ScrollPane scrollPane = new ScrollPane();
    VBox rulesAxiomsVBox = new VBox();
    Region aboveRulesAxiomsLabel = new Region();

    RulesAxiomsInterpreter rulesAxiomsInterpreter;

    public RulesAxiomsView (Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        // Setting VBox rulesAxiomsVBox properties.
        this.rulesAxiomsVBox.setId("rulesAxiomsVBox");
        this.rulesAxiomsVBox.setSpacing(0);
        this.rulesAxiomsVBox.setAlignment(Pos.TOP_CENTER);
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.rulesAxiomsVBox.setMinWidth(newVal.doubleValue() * .20);
            this.rulesAxiomsVBox.setMaxWidth(newVal.doubleValue() * .20);
        });
        // Setting Region abovePropositionalLogicLabel settings.
        this.rulesAxiomsVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.aboveRulesAxiomsLabel.setMinHeight(newVal.doubleValue() * .03);
            this.aboveRulesAxiomsLabel.setMaxHeight(newVal.doubleValue() * .03);
        });
        // Setting ScrollPane parentPane properties.
        this.scrollPane.setId("rulesAxiomsScrollPane");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setMaxWidth(newVal.doubleValue() * .20);
        });
        this.scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scrollPane.fitToWidthProperty().set(true);
        // Adding children nodes to their parents nodes.
        this.rulesAxiomsVBox.getChildren().addAll(this.aboveRulesAxiomsLabel, this.scrollPane);
        // Creating interpreter to handle events and actions.
        this.rulesAxiomsInterpreter = new RulesAxiomsInterpreter(this.controller, this);
    }

    public ScrollPane getScrollPane () { return this.scrollPane; }

    public VBox getParentPane() { return this.rulesAxiomsVBox; }

}
