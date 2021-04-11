package com.llat.input.interpreters;

import com.llat.input.LLATErrorListener;
import com.llat.input.LLATParserAdapter;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.input.events.SyntaxErrorEvent;
import com.llat.input.events.SyntaxWarningEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.FormulaInputEvent;

import java.util.Iterator;
import java.util.LinkedList;

public class LLATParserInterpreter implements Listener {

    public LLATParserInterpreter() {
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof FormulaInputEvent) {
            LinkedList<WffTree> linkedTree = LLATParserAdapter.getAbstractSyntaxTree(((FormulaInputEvent) _event).getFormula());
            LLATErrorListener.getErrorIterator().forEachRemaining((message) -> {
                EventBus.throwEvent(new SyntaxErrorEvent(message.toString()));
            });
            LLATErrorListener.getWarningIterator().forEachRemaining((message) -> {
                EventBus.throwEvent(new SyntaxWarningEvent(message.toString()));
            });
            if (linkedTree != null) {
                WffTree wffTree = LLATParserAdapter.getAbstractSyntaxTree(((FormulaInputEvent) _event).getFormula()).get(0);
                EventBus.throwEvent(new SolvedFormulaEvent(linkedTree));
            }
        }
    }

}
