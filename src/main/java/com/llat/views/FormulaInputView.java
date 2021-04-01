package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.FormulaInputInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class FormulaInputView {
    Controller controller;
    Stage stage;
    AnchorPane parentPane = new AnchorPane();
    HBox formulaInputHBox = new HBox();
    TextField formulaInputField = new TextField();
    Button formulaInputButton = new Button("Solve");
    FormulaInputInterpreter formulaInputInterpreter;

    public FormulaInputView(Controller _controller) {
        this.controller = _controller;
        this.stage = _controller.getStage();
        // Setting HBox parentHBox settings.
        this.formulaInputHBox.setId("formulaInputHBox");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputHBox.setMinWidth(newVal.doubleValue());
        });
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputHBox.setMinHeight(newVal.doubleValue() * .05);
        });
        this.formulaInputHBox.setAlignment(Pos.CENTER);
        this.formulaInputHBox.setFillHeight(true);
        this.formulaInputHBox.setSpacing(4);
        // Setting TextField formulaInputField settings.
        HBox.setHgrow(this.formulaInputField, Priority.ALWAYS);
        this.formulaInputHBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputField.setMaxWidth(newVal.doubleValue() * .50);
        });
        // Setting Button formulaInputButton settings.
        HBox.setHgrow(this.formulaInputButton, Priority.ALWAYS);
        // Adding children nodes to their parents nodes.
        this.formulaInputHBox.getChildren().addAll(this.formulaInputField, this.formulaInputButton);
        this.parentPane.getChildren().addAll(this.formulaInputHBox);
        // Creating interpreter to handle events and actions.
        this.formulaInputInterpreter = new FormulaInputInterpreter(this.controller, this);
    }

    public Button getFormulaInputButton() {
        return this.formulaInputButton;
    }

    public TextField getFormulaInputField() {
        return this.formulaInputField;
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
