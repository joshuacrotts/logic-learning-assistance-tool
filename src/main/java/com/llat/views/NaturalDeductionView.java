package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.NaturalDeductionViewInterpreter;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NaturalDeductionView {
    private Controller controller;
    private Pane parentPane = new Pane();
    private VBox naturalDeductionVBox = new VBox();
    private NaturalDeductionViewInterpreter interpreter;

    public NaturalDeductionView (Controller _controller) {
        this.controller = _controller;
        this.interpreter = new NaturalDeductionViewInterpreter(this);
    }

    public void setNaturalDeductionVBoxProperties () {
        this.naturalDeductionVBox.setAlignment(Pos.BASELINE_LEFT);
    }

    public Pane getParentPane () {
        return this.parentPane;
    }

    public VBox getNaturalDeductionVBox() {
        return naturalDeductionVBox;
    }

    public Controller getController() {
        return controller;
    }

    public NaturalDeductionViewInterpreter getInterpreter() {
        return interpreter;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void setNaturalDeductionVBox(VBox naturalDeductionVBox) {
        this.naturalDeductionVBox = naturalDeductionVBox;
    }

    public void setInterpreter(NaturalDeductionViewInterpreter interpreter) {
        this.interpreter = interpreter;
    }

}