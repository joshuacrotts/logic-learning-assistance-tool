package com.llat.views.interpreters;

import com.llat.algorithms.models.NDWffTree;
import com.llat.models.events.UpdateNaturalDeductionEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.NaturalDeductionView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NaturalDeductionViewInterpreter implements Listener {
    private NaturalDeductionView naturalDeductionView;
    public NaturalDeductionViewInterpreter (NaturalDeductionView _naturalDeductionView) {
        this.naturalDeductionView = _naturalDeductionView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof UpdateNaturalDeductionEvent) {
            if (this.naturalDeductionView.getParentPane().getChildren().contains(this.naturalDeductionView.getNaturalDeductionVBox())) {
                this.removeNaturalDeductionVBox();
                this.clearNaturalDeductionVBox();
            }
            if (((UpdateNaturalDeductionEvent) _event).isEmpty()) {
                return;
            }
            else {
                ((UpdateNaturalDeductionEvent) _event).getNdWffTrees().forEach((wffTree) -> {
                    this.setContentNaturalDeductionVBox(wffTree);
                });
                this.addNaturalDeductionVBox();
                this.naturalDeductionView.setNaturalDeductionVBoxProperties();
            }
        }
    }

    public void addNaturalDeductionVBox () {
        this.naturalDeductionView.getParentPane().getChildren().add(this.naturalDeductionView.getNaturalDeductionVBox());
    }

    public void clearNaturalDeductionVBox () {
        this.naturalDeductionView.setNaturalDeductionVBox(new VBox());
    }

    public void removeNaturalDeductionVBox () {
        this.naturalDeductionView.getParentPane().getChildren().remove(this.naturalDeductionView.getNaturalDeductionVBox());
    }
    //(A->B), A => B
    public void setContentNaturalDeductionVBox (NDWffTree _ndWffTree) {
        this.naturalDeductionView.getNaturalDeductionVBox().getChildren().add(new Label(_ndWffTree.toString()));
    }

}