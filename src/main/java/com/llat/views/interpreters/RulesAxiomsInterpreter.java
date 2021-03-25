package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.RulesAxiomsView;
import com.llat.views.SymbolDescriptionView;
import com.llat.views.events.SymbolDescriptionEvent;

public class RulesAxiomsInterpreter implements Listener {
    RulesAxiomsView rulesAxiomsView;
    Controller controller;
    public RulesAxiomsInterpreter (Controller _controller, RulesAxiomsView _rulesAxiomsView) {
        this.controller = _controller;
        this.rulesAxiomsView = _rulesAxiomsView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolDescriptionEvent) {
            this.rulesAxiomsView.getScrollPane().setVisible(false);
            this.rulesAxiomsView.getScrollPane().setContent(new SymbolDescriptionView(((SymbolDescriptionEvent) _event).getSymbol()).getParentPane());
            this.rulesAxiomsView.getScrollPane().setVisible(true);
        }
    }

}
