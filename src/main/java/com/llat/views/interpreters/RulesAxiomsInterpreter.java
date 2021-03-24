package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.RulesAxiomsView;
import com.llat.views.events.SymbolDescriptionEvent;
import com.llat.views.events.SymbolInputEvent;
import javafx.scene.layout.VBox;

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
            VBox symbolDetailsVBox = this.rulesAxiomsView.getSymbolDetailsVBox();
            symbolDetailsVBox.setVisible(true);
        }
    }

}
