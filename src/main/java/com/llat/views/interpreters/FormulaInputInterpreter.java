package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.Listener;
import com.llat.views.FormulaInputView;
import com.llat.views.events.SymbolInputEvent;

public class FormulaInputInterpreter implements Listener {
    private Controller controller;
    private FormulaInputView formulaInputView;
    public FormulaInputInterpreter (Controller _controller, FormulaInputView _formulaInputView) {
        this.controller = _controller;
        this.formulaInputView = _formulaInputView;
        this.controller.addListener(this);
    }
    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolInputEvent) {
            this.formulaInputView.getFormulaInputField().appendText(((SymbolInputEvent) _event).getSymbolInput());
        }
    }

}
