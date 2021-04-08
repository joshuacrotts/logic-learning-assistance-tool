package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.TruthTableView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TruthTableInterpreter implements Listener {
    private Controller controller;
    private TruthTableView truthTableView;
    public TruthTableInterpreter (Controller _controller, TruthTableView _truthTableView) {
        this.controller = _controller;
        this.truthTableView = _truthTableView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
/*        if (_event instanceof SolvedFormulaEvent) {
            this.truthTableView.getTruthTable().getChildren().clear();
            createTruthTable(((SolvedFormulaEvent) _event).getWffTree().getChild(0), this.truthTableView.getTruthTable());
            this.truthTableView.getTruthTable().setLayoutX((this.truthTableView.getParentPane().getWidth() / 2) - (this.truthTableView.getTruthTable().getWidth() / 2) );
        }*/
    }

    //(A∧B) (A∧(A∧(A∧B)))
    public void createTruthTable (WffTree _wffTree, HBox _truthTable) {
        int childSize = _wffTree.getChildrenSize();
        Button wffSymbol = new Button(_wffTree.getStringRep());
        wffSymbol.setMaxWidth(Double.MAX_VALUE);
        VBox truthColumn = new VBox();
        truthColumn.getChildren().add(wffSymbol);
        _wffTree.getTruthValues().forEach((truthValue) -> {
            Text truthValueButton = new Text(truthValue.toString());
//            truthValueButton.setMaxWidth(Double.MAX_VALUE);
            truthColumn.getChildren().add(truthValueButton);
        });

        switch (childSize) {
            case 1:
                createTruthTable(_wffTree.getChild(0), _truthTable);
                break;
            case 2:
                createTruthTable(_wffTree.getChild(0), _truthTable);
                createTruthTable(_wffTree.getChild(1), _truthTable);
                break;
            default:
                break;
        }
        HBox.setHgrow(truthColumn, Priority.ALWAYS);
        _truthTable.getChildren().add(truthColumn);
    }

}
