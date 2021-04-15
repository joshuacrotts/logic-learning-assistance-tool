package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.InputButtonsView;

public class InputButtonInterpreter implements Listener {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final InputButtonsView inputButtonsView;

    public InputButtonInterpreter(Controller _controller, InputButtonsView _inputButtonsView) {
        this.controller = _controller;
        this.inputButtonsView = _inputButtonsView;
        this.inputButtonsView.getPredicateLogicButtons().forEach(this.controller::setSymbolInputButtonOnAction);
        this.inputButtonsView.getPropositionalLogicButtons().forEach(this.controller::setSymbolInputButtonOnAction);
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {

    }
}
