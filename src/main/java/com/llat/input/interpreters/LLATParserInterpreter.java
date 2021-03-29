package com.llat.input.interpreters;

import com.llat.input.LLATParserAdapter;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.FormulaInputEvent;


public class LLATParserInterpreter implements Listener {

    public LLATParserInterpreter() {
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof FormulaInputEvent) { EventBus.throwEvent(new SolvedFormulaEvent(LLATParserAdapter.getAbstractSyntaxTree(((FormulaInputEvent) _event).getFormula()))); }
    }

}
