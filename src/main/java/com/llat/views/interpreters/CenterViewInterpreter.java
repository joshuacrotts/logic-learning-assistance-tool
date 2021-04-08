package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.models.events.UpdateViewTruthEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.CenterView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CenterViewInterpreter implements Listener {
    Controller controller;
    CenterView centerView;
    HBox resultHBox ;
    public CenterViewInterpreter (Controller _controller, CenterView _centerView) {
        this.controller = _controller;
        this.centerView = _centerView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof UpdateViewTruthEvent) {
            for (int i = 0; i < this.centerView.getParentPane().getChildren().size(); i++) {
                if (this.centerView.getParentPane().getChildren().get(i).getId() == "resultHBox") {
                    this.centerView.getParentPane().getChildren().remove(i);
                }
            }
            if(((UpdateViewTruthEvent) _event).isEmpty()) {
                return;
            }
            HBox curHbox = new HBox();
            curHbox.setId("resultHBox");
            Label truthLabel = ((UpdateViewTruthEvent) _event).getTruthValue() ? new Label("true") : new Label("false");
            truthLabel.setId(truthLabel.getText() == "true" ?  "trueLabel" : "falseLabel");
            Label resultLabel = new Label("Result: ");
            resultLabel.setId("resultLabel");
            curHbox.getChildren().addAll(resultLabel, truthLabel);
            curHbox.setAlignment(Pos.CENTER);
            curHbox.setSpacing(20);
            this.centerView.getParentPane().getChildren().add(1, curHbox);
        }
    }

}
