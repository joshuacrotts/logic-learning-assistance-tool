package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.ParseTreeInterpreter;
import javafx.scene.layout.Pane;

public class ParseTreeView {
    public static int MAXSCALE = 8;
    public static int MINSCALE = 0;

    private Controller controller;
    private Pane parentPane = new Pane();
    private ParseTreeInterpreter truthTreeInterpreter;

    public ParseTreeView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox truthTreeVBox properties.
        this.parentPane.setId("truthTreeVBox");
        this.controller.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.parentPane.setMaxWidth(newVal.doubleValue() * .60);
            this.parentPane.setMinWidth(newVal.doubleValue() * .60);
        });
        this.parentPane.setVisible(false);
        // Adding children nodes to their parents nodes.
        // Creating interpreter to handle events and actions.
        this.truthTreeInterpreter = new ParseTreeInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }
}
