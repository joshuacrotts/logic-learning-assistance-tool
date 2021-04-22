package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.input.events.SyntaxErrorEvent;
import com.llat.models.events.RandomGeneratedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.FormulaInputView;
import com.llat.views.events.*;
import javafx.scene.control.TextField;

public class FormulaInputInterpreter implements Listener {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final FormulaInputView formulaInputView;

    /**
     *
     */
    private String lastValidWff = null;

    /**
     *
     */
    private boolean algorithmSelected = false;

    public FormulaInputInterpreter(Controller _controller, FormulaInputView _formulaInputView) {
        this.controller = _controller;
        this.formulaInputView = _formulaInputView;
        this.controller.setSolveButtonOnAction(this.formulaInputView.getFormulaInputButton());
        this.formulaInputView.getAlgorithmApplyButton().setOnMousePressed((event) -> {
            this.controller.setApplyAlgorithmOnAction(this.formulaInputView.getAlgorithmApplyButton());
            this.algorithmSelected = false;
        });

        this.formulaInputView.getFormulaInputField().textProperty().addListener((obs, oldVal, newVal) -> {
            this.formulaInputView.getAlgorithmApplyButton().setDisable(!newVal.equals(this.lastValidWff) || !this.algorithmSelected);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolInputEvent) {
            // If they click a symbol, position the cursor where it should be.
            TextField input = this.formulaInputView.getFormulaInputField();
            if (this.formulaInputView.getCaretPos() != -1) {
                input.insertText(this.formulaInputView.getCaretPos(), ((SymbolInputEvent) _event).getSymbolInput());
            }

            // If they click a button, go to the position where it previously was.
            input.requestFocus();
            input.positionCaret(this.formulaInputView.getCaretPos());
            input.deselect();
        } else if (_event instanceof SolveButtonEvent) {
            // We want to disable the buttons so they are forced to pick
            // an algorithm to use.
            this.algorithmSelected = false;
            this.formulaInputView.getAlgorithmApplyButton().setDisable(true);
            this.formulaInputView.getFormulaInputButton().setDisable(true);
            this.formulaInputView.getFormulaInputField().requestFocus();
            this.formulaInputView.getFormulaInputField().positionCaret(this.formulaInputView.getCaretPos());
            this.formulaInputView.getFormulaInputField().deselect();
            EventBus.throwEvent(new FormulaInputEvent(this.formulaInputView.getFormulaInputField().getText()));
        } else if (_event instanceof AlgorithmSelectedEvent) {
            // If they select an algorithm, they have to click apply.
            this.algorithmSelected = true;
            this.formulaInputView.getFormulaInputButton().setDisable(true);
            this.formulaInputView.getAlgorithmApplyButton().setDisable(false);
        } else if (_event instanceof SyntaxErrorEvent) {
            // If a syntax error occurs, just reenable the button.
            this.formulaInputView.getFormulaInputButton().setDisable(false);
        } else if (_event instanceof ApplyAlgorithmButtonEvent) {
            // Once they click an algorithm, we can hit apply.
            this.formulaInputView.getFormulaInputButton().setDisable(false);
        } else if (_event instanceof SolvedFormulaEvent) {
            this.lastValidWff = this.formulaInputView.getFormulaInputField().getText();
        } else if (_event instanceof RandomGeneratedFormulaEvent) {
            if (!((RandomGeneratedFormulaEvent) _event).isEmpty()) {
                this.formulaInputView.getFormulaInputField().setText(((RandomGeneratedFormulaEvent) _event).getFormula());
            }
        }
    }
}
