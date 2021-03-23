package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.InputButtonsView;

public class InputButtonInterpreter implements Listener {
    Controller controller;
    InputButtonsView inputButtonsView;

    public InputButtonInterpreter (Controller _controller, InputButtonsView _inputButtonsView) {
        this.controller = _controller;
        this.inputButtonsView = _inputButtonsView;
        EventBus.addListener(this);
        this.inputButtonsView.getPropositionalLogicButtons();
        this.inputButtonsView.getPredicateLogicButtons().forEach((button) -> { this.controller.setSymbolInputButtonOnAction(button); });
        this.inputButtonsView.getPropositionalLogicButtons().forEach((button) -> { this.controller.setSymbolInputButtonOnAction(button); });
    }

    @Override
    public void catchEvent(Event _event) {

    }

}
