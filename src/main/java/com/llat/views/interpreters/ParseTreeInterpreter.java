package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.ParseTreeView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ParseTreeInterpreter implements Listener {
    Controller controller;
    ParseTreeView truthTreeView;
    VBox treeRepresentation = new VBox();

    public ParseTreeInterpreter(Controller _controller, ParseTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            // Clear the TruthTreeView parentPane of the the treeRepresentation.
            this.truthTreeView.getParentPane().getChildren().remove(this.treeRepresentation);
            // Create the View of the tree from the first child of the root.
            this.treeRepresentation = this.createTree(((SolvedFormulaEvent) _event).getWffTree().getChild(0));
            // Setting VBox treeRepresentation properties.
            this.treeRepresentation.setId("parseTreeVBox");
            this.treeRepresentation.setMinSize(this.truthTreeView.getParentPane().getWidth(), this.truthTreeView.getParentPane().getHeight() * .50);
            this.truthTreeView.getParentPane().heightProperty().addListener((obs, oldVal, newVal) -> { this.treeRepresentation.setMinHeight(newVal.doubleValue() * .50); });
            this.truthTreeView.getParentPane().widthProperty().addListener((obs, oldVal, newVal) -> { this.treeRepresentation.setMinWidth(newVal.doubleValue() * .50); });
            this.treeRepresentation.setAlignment(Pos.CENTER);
            this.controller.setPaneToZoomable(this.treeRepresentation);
            this.controller.setPaneToPannable(this.treeRepresentation);
            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treeRepresentation);

        }
    }

    private VBox createTree(WffTree _wffTree) {
        VBox parentVBox = new VBox();
        HBox parentHBox = new HBox();
        if(_wffTree.isPredicate()) {
            parentVBox.getChildren().add(new Button(_wffTree.getStringRep()));
        }
        else {
            for (WffTree child : _wffTree.getChildren()) {
                parentHBox.getChildren().add(createTree(child));
            }
            parentVBox.getChildren().add(new Button(_wffTree.getSymbol()));
        }
        parentVBox.getChildren().add(parentHBox);
        parentVBox.setAlignment(Pos.TOP_CENTER);
        parentVBox.getChildren().forEach((child) -> child.setId("treeNode"));
        parentHBox.getChildren().forEach((child) -> child.setId("treeNode"));
        parentHBox.setId("rulesAxiomsText");
        parentHBox.setAlignment(Pos.TOP_CENTER);
        return parentVBox;
    }

}

