package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.ParseTreeInterpreter;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ParseTreeView {

    public static int MAXSCALE = 8;
    public static int MINSCALE = 0;

    private Controller controller;
    private VBox truthTreeVBox = new VBox();
    private ParseTreeInterpreter truthTreeInterpreter;

    public ParseTreeView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox truthTreeVBox properties.
        this.truthTreeVBox.setId("truthTreeVBox");
        this.controller.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.truthTreeVBox.setMaxWidth(newVal.doubleValue() * .60);
            this.truthTreeVBox.setMinWidth(newVal.doubleValue() * .60);
        });
        this.controller.getStage().getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.truthTreeVBox.setMinHeight((newVal.doubleValue() * .90) - MenuBarView.menuBarHeight);
            this.truthTreeVBox.setMaxHeight((newVal.doubleValue() * .90) - MenuBarView.menuBarHeight);
        });
        this.truthTreeVBox.setAlignment(Pos.TOP_CENTER);
        // Adding children nodes to their parents nodes.
        // Creating interpreter to handle events and actions.
        this.truthTreeInterpreter = new ParseTreeInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.truthTreeVBox;
    }
}
