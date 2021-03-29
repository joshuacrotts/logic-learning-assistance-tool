package com.llat.input.interpreters;

import com.llat.input.LLATParserAdapter;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.FormulaInputEvent;

import java.util.LinkedList;

public class LLATParserInterpreter implements Listener {

    public LLATParserInterpreter() {
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof FormulaInputEvent) {
            LinkedList<WffTree> wffTreeList = LLATParserAdapter.getAbstractSyntaxTree(((FormulaInputEvent) _event).getFormula());
            wffTreeList.get(0).printSyntaxTree();
        }
    }
}
