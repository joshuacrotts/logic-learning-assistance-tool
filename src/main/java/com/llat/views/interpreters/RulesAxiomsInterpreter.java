package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.RulesAxiomsView;
import com.llat.views.SymbolDescriptionView;
import com.llat.views.events.SymbolDescriptionEvent;
import javafx.scene.layout.VBox;

public class RulesAxiomsInterpreter implements Listener {

    /**
     *
     */
    private final RulesAxiomsView rulesAxiomsView;

    /**
     *
     */
    private final Controller controller;

    public RulesAxiomsInterpreter(Controller _controller, RulesAxiomsView _rulesAxiomsView) {
        this.controller = _controller;
        this.rulesAxiomsView = _rulesAxiomsView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SymbolDescriptionEvent) {
            VBox symbolDescriptionVBox = new SymbolDescriptionView(((SymbolDescriptionEvent) _event).getSymbol()).getParentPane();
            symbolDescriptionVBox.getChildren().add(0, this.rulesAxiomsView.getTopFiller());
            this.rulesAxiomsView.getScrollPane().setContent(symbolDescriptionVBox);
        }
    }
}
