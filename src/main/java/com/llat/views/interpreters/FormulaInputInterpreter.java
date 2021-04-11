package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.events.RandomGeneratedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.FormulaInputView;
import com.llat.views.events.AlgorithmSelectedEvent;
import com.llat.views.events.FormulaInputEvent;
import com.llat.views.events.SolveButtonEvent;
import com.llat.views.events.SymbolInputEvent;
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
            if (newVal.equals(this.lastValidWff) && this.algorithmSelected) {
                this.formulaInputView.getAlgorithmApplyButton().setDisable(false);
            } else {
                this.formulaInputView.getAlgorithmApplyButton().setDisable(true);
            }
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolInputEvent) {
            TextField input = this.formulaInputView.getFormulaInputField();
            if (this.formulaInputView.getCaretPos() != -1) {
                input.insertText(this.formulaInputView.getCaretPos(), ((SymbolInputEvent) _event).getSymbolInput());
            }
            // If they click a button, go to the end of the formula.
            input.requestFocus();
            input.positionCaret(this.formulaInputView.getCaretPos());
            input.deselect();
        } else if (_event instanceof SolveButtonEvent) {
            this.algorithmSelected = false;
            this.formulaInputView.getAlgorithmApplyButton().setDisable(true);
            EventBus.throwEvent(new FormulaInputEvent(this.formulaInputView.getFormulaInputField().getText()));
        } else if (_event instanceof AlgorithmSelectedEvent) {
            this.algorithmSelected = true;
            this.formulaInputView.getAlgorithmApplyButton().setDisable(false);
        } else if (_event instanceof SolvedFormulaEvent) {
            this.lastValidWff = this.formulaInputView.getFormulaInputField().getText();
        }
        else if (_event instanceof RandomGeneratedFormulaEvent) {
            if (!((RandomGeneratedFormulaEvent) _event).isEmpty()) {
                this.formulaInputView.getFormulaInputField().setText(((RandomGeneratedFormulaEvent) _event).getFormula());
            } else {
                this.formulaInputView.getFormulaInputField().setText("Formula could not be generated.");
            }
        }
    }
}
