package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.FormulaInputInterpreter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class FormulaInputView {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final Stage stage;

    /**
     *
     */
    private final HBox formulaInputHBox = new HBox();

    /**
     *
     */
    private final TextField formulaInputField = new TextField();

    /**
     *
     */
    private final Button formulaInputButton = new Button("Solve");
    private final Button algorithmApplyButton = new Button("Apply");
    /**
     *
     */
    private final FormulaInputInterpreter formulaInputInterpreter;

    /**
     * Keeps track of the current caret position for when the field loses and
     * gains focus.
     */
    private int caretPos = -1;

    public FormulaInputView(Controller _controller) {
        this.controller = _controller;
        this.stage = _controller.getStage();
        // Setting HBox parentHBox properties.
        this.formulaInputHBox.setId("formulaInputHBox");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputHBox.setMinWidth(newVal.doubleValue());
        });
        this.formulaInputHBox.setAlignment(Pos.CENTER);
        this.formulaInputHBox.setFillHeight(true);
        this.formulaInputHBox.setSpacing(4);
        this.formulaInputHBox.setPadding(new Insets(10, 0, 10, 0));
        // Setting TextField formulaInputField properties.
        HBox.setHgrow(this.formulaInputField, Priority.ALWAYS);
        this.formulaInputHBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputField.setMaxWidth(newVal.doubleValue() * .50);
        });
        // If the formula input field loses focus, store the caret position.
        this.formulaInputField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            this.caretPos = this.formulaInputField.getCaretPosition();
        });
        // Setting Button formulaInputButton properties.
        HBox.setHgrow(this.formulaInputButton, Priority.ALWAYS);
        // Setting Button algorithmApplyButton properties.
        HBox.setHgrow(this.algorithmApplyButton, Priority.ALWAYS);
        // Adding children nodes to their parents nodes.
        this.formulaInputHBox.getChildren().addAll(this.formulaInputField, this.formulaInputButton, this.algorithmApplyButton);
        // Creating interpreter to handle events and actions.
        this.formulaInputInterpreter = new FormulaInputInterpreter(this.controller, this);
    }

    public Button getFormulaInputButton() {
        return this.formulaInputButton;
    }

    public Button getAlgorithmApplyButton() {
        return this.algorithmApplyButton;
    }

    public TextField getFormulaInputField() {
        return this.formulaInputField;
    }

    public Pane getParentPane() {
        return this.formulaInputHBox;
    }

    public int getCaretPos() {
        return this.caretPos;
    }

    public void setCaretPos(int _caretPos) {
        this.caretPos = _caretPos;
    }

}
