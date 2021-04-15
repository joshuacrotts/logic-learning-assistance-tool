package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SyntaxErrorEvent;
import com.llat.input.events.SyntaxWarningEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.LLATErrorView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LLATErrorViewInterpreter implements Listener {

    /**
     *
     */
    private Controller controller;

    /**
     *
     */
    private LLATErrorView llatErrorView;

    public LLATErrorViewInterpreter (Controller _controller, LLATErrorView _llatErrorView) {
        this.controller = _controller;
        this.llatErrorView = _llatErrorView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SyntaxErrorEvent) {
            Text errorMsg = new Text(((SyntaxErrorEvent) _event).getErrorMessage());
            errorMsg.setFill(Color.RED);
            errorMsg.setId("errorText");
            this.llatErrorView.getLogBox().getChildren().add(errorMsg);
        }
        else if (_event instanceof SyntaxWarningEvent) {
            Text warningMsg = new Text(((SyntaxWarningEvent) _event).getWarningMessage());
            warningMsg.setFill(Color.YELLOW);
            warningMsg.setId("warningText");
            this.llatErrorView.getLogBox().getChildren().add(warningMsg);
        }
    }
}
