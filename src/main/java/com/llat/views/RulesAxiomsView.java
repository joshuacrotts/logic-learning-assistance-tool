package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.RulesAxiomsInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesAxiomsView {
    Controller controller;
    Stage stage;
    ScrollPane parentPane = new ScrollPane();
    VBox rulesAxiomsVBox = new VBox();
    Region aboveRulesAxiomsLabel = new Region();
    Label rulesAxiomsLabel = new Label("Rules/Axioms");
    VBox symbolDetailsVBox = new VBox();
    Label symbolNameLabel = new Label("Symbol Name");
    Label formalNameLabel = new Label("Formal Name");
    Label alternativeSymbolsLabel = new Label("Alternative Symbols");
    Label definitionLabel = new Label("Definition");
    Label examplesLabel = new Label("Examples");
    RulesAxiomsInterpreter rulesAxiomsInterpreter;

    public RulesAxiomsView (Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        // Setting ScrollPane parentPane properties.
        this.parentPane.setId("rulesAxiomsScrollPane");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> { this.parentPane.setMinWidth(newVal.doubleValue() * .20); });
        this.parentPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.parentPane.fitToWidthProperty().set(true);
        this.parentPane.fitToHeightProperty().set(true);
        // Setting VBox rulesAxiomsVBox properties.
        this.rulesAxiomsVBox.setId("rulesAxiomsVBox");
        this.rulesAxiomsVBox.setSpacing(0);
        this.rulesAxiomsVBox.setAlignment(Pos.TOP_CENTER);
        // Setting Region abovePropositionalLogicLabel settings.
        this.rulesAxiomsVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.aboveRulesAxiomsLabel.setMinHeight(newVal.doubleValue() * .03);
            this.aboveRulesAxiomsLabel.setMaxHeight(newVal.doubleValue() * .03);
        });
        // Setting Label propositionalLogicLabel settings.
        this.rulesAxiomsLabel.setId("rulesAxiomsLabel");
        // Setting VBox symbolDetailsVBox settings.
        this.symbolDetailsVBox.setVisible(false);
        this.symbolDetailsVBox.setId("symbolDetailsVBox");
        this.symbolDetailsVBox.setSpacing(0);
        this.symbolDetailsVBox.setAlignment(Pos.TOP_CENTER);
        // Adding children nodes to their parents nodes.
        this.symbolDetailsVBox.getChildren().addAll(this.symbolNameLabel, this.formalNameLabel, this.alternativeSymbolsLabel, this.definitionLabel, this.examplesLabel);
        this.rulesAxiomsVBox.getChildren().addAll(this.aboveRulesAxiomsLabel, this.rulesAxiomsLabel, this.symbolDetailsVBox);
        this.parentPane.setContent(this.rulesAxiomsVBox);
        // Creating interpreter to handle events and actions.
        this.rulesAxiomsInterpreter = new RulesAxiomsInterpreter(this.controller, this);
    }

    public VBox getSymbolDetailsVBox () { return this.symbolDetailsVBox; }

    public ScrollPane getParentPane() { return this.parentPane; }

}
