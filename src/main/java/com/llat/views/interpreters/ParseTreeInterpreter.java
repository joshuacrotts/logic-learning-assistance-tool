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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
            Button predicateNode = new Button(_wffTree.getStringRep());
            predicateNode.setId("treeNode");
            parentVBox.getChildren().add(predicateNode);
        }
        else {
            for (WffTree child : _wffTree.getChildren()) {
                parentHBox.getChildren().addAll(createTree(child));
            }
            Button node = new Button(_wffTree.getSymbol());
            node.setId("treeNode");
            parentVBox.getChildren().add(node);
            if(!(_wffTree.getChildrenSize() == 0)) {
                if(_wffTree.getChildrenSize() == 1) {
                    Button singleBracket = new Button();
                    singleBracket.setId("singleBracket");
                    parentVBox.getChildren().addAll(singleBracket);
                }
                if(_wffTree.getChildrenSize() == 2) {
                    HBox testHBox = new HBox();
                    testHBox.setId("treeNode");
                    testHBox.setAlignment(Pos.TOP_CENTER);
                    Button leftBracket = new Button();
                    leftBracket.setId("leftBracketButton");
                    leftBracket.setMaxWidth(Double.MAX_VALUE);
                    Button middleBracket = new Button();
                    middleBracket.setId("middleBracketButton");
                    Button rightBracket = new Button();
                    rightBracket.setId("rightBracketButton");
                    testHBox.getChildren().addAll(leftBracket, middleBracket, rightBracket);
                    parentVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
                        leftBracket.setMinWidth(newVal.doubleValue()  * .3);
                        middleBracket.setMinWidth(newVal.doubleValue() * .3);
                        rightBracket.setMinWidth(newVal.doubleValue() * .3);
                        System.out.println(node.getWidth());
                    });
                    //parentHBox.setSpacing(250);
                    parentVBox.getChildren().addAll( testHBox);
                }

            }
        }

        parentVBox.getChildren().add(parentHBox);
        parentVBox.setAlignment(Pos.TOP_CENTER);

        parentHBox.setAlignment(Pos.TOP_CENTER);
        parentVBox.setId("treeNode");
        parentHBox.setId("treeNode");

        return parentVBox;
    }

}

