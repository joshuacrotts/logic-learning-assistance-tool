package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.ParseTreeInterpreter;
import com.llat.views.interpreters.TruthTreeInterpreter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TruthTreeView {

    public static int MAXSCALE = 8;
    public static int MINSCALE = 0;

    private Controller controller;
    private Pane truthTreeVBox = new VBox();
    private TruthTreeInterpreter truthTreeInterpreter;

    public TruthTreeView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox truthTreeVBox properties.
        this.truthTreeVBox.setId("truthTreeVBox");
        this.controller.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.truthTreeVBox.setMaxWidth(newVal.doubleValue() * .60);
            this.truthTreeVBox.setMinWidth(newVal.doubleValue() * .60);
        });
        this.controller.getStage().getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.truthTreeVBox.setMinHeight(newVal.doubleValue());
            this.truthTreeVBox.setMaxHeight(newVal.doubleValue());
        });
        this.truthTreeVBox.setVisible(false);
        // Adding children nodes to their parents nodes.
        // Creating interpreter to handle events and actions.
        this.truthTreeInterpreter = new TruthTreeInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.truthTreeVBox;
    }
}
