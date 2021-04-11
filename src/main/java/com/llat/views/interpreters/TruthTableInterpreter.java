package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.UnsolvedFormulaEvent;
import com.llat.models.events.UpdateViewTruthTableEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.TruthTableView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TruthTableInterpreter implements Listener {

    private final Controller controller;
    private final TruthTableView truthTableView;

    public TruthTableInterpreter(Controller _controller, TruthTableView _truthTableView) {
        this.controller = _controller;
        this.truthTableView = _truthTableView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof UpdateViewTruthTableEvent) {
            this.truthTableView.getTruthTable().getChildren().clear();
            if (((UpdateViewTruthTableEvent) _event).isEmpty()) {
                this.truthTableView.getParentPane().getChildren().remove(this.truthTableView.getScrollPane());
                return;
            }
            if (this.truthTableView.getScrollPane().getParent() == null) {
                this.truthTableView.getParentPane().getChildren().add(this.truthTableView.getScrollPane());
            }

            this.createTruthTable(((UpdateViewTruthTableEvent) _event).getWffTree().getChild(0), this.truthTableView.getTruthTable());
            this.truthTableView.getTruthTable().setLayoutX((this.truthTableView.getParentPane().getWidth() / 2) - (this.truthTableView.getTruthTable().getWidth() / 2));
        }
        else if (_event instanceof UnsolvedFormulaEvent) {
            this.truthTableView.getTruthTable().getChildren().clear();
            this.truthTableView.getParentPane().getChildren().remove(this.truthTableView.getScrollPane());
        }
    }

    //(A∧B) (A∧(A∧(A∧B)))
    public void createTruthTable(WffTree _wffTree, HBox _truthTable) {
        int childSize = _wffTree.getChildrenSize();
        Button wffSymbol = new Button(_wffTree.getStringRep());
        wffSymbol.setMaxWidth(Double.MAX_VALUE);
        VBox truthColumn = new VBox();
        truthColumn.getChildren().add(wffSymbol);
        _wffTree.getTruthValues().forEach((truthValue) -> {
            Button truthValueButton = new Button(truthValue.toString());
            truthValueButton.setMaxWidth(Double.MAX_VALUE);
            truthColumn.getChildren().add(truthValueButton);
        });

        switch (childSize) {
            case 1:
                this.createTruthTable(_wffTree.getChild(0), _truthTable);
                break;
            case 2:
                this.createTruthTable(_wffTree.getChild(0), _truthTable);
                this.createTruthTable(_wffTree.getChild(1), _truthTable);
                break;
            default:
                break;
        }
        HBox.setHgrow(truthColumn, Priority.ALWAYS);
        _truthTable.getChildren().add(truthColumn);
    }

}
