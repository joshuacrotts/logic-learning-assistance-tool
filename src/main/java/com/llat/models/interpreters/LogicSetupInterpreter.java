package com.llat.models.interpreters;


import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.LogicSetup;
import com.llat.models.events.SetAlgorithmInputEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;

public class LogicSetupInterpreter implements Listener {
    LogicSetup logicSetup;

    public LogicSetupInterpreter (LogicSetup _logicSetup) {
        this.logicSetup = _logicSetup;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            this.logicSetup.setWffTree(((SolvedFormulaEvent) _event).getWffTree());
            EventBus.throwEvent(new SetAlgorithmInputEvent(this.logicSetup.getAvailableAlgorithms()));
        }
    }

}
