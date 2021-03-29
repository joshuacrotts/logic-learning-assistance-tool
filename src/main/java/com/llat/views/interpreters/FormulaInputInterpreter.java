package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.FormulaInputView;
import com.llat.views.events.FormulaInputEvent;
import com.llat.views.events.SolveButtonEvent;
import com.llat.views.events.SymbolInputEvent;

public class FormulaInputInterpreter implements Listener {
    Controller controller;
    private FormulaInputView formulaInputView;

    public FormulaInputInterpreter(Controller _controller, FormulaInputView _formulaInputView) {
        this.controller = _controller;
        this.formulaInputView = _formulaInputView;
        EventBus.addListener(this);
        this.controller.setSolveButtonOnAction(this.formulaInputView.getFormulaInputButton());
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolInputEvent) {
            this.formulaInputView.getFormulaInputField().appendText(((SymbolInputEvent) _event).getSymbolInput());
        } else if (_event instanceof SolveButtonEvent) {
            EventBus.throwEvent(new FormulaInputEvent(this.formulaInputView.getFormulaInputField().getText()));
        }
    }
}
