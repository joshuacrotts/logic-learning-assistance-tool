package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.ParseTreeView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;


public class ParseTreeInterpreter implements Listener {
    private Controller controller;
    private ParseTreeView truthTreeView;
    private Canvas treeRepresentation = new Canvas(1280, 1280);
    GraphicsContext tgc = treeRepresentation.getGraphicsContext2D();

    public ParseTreeInterpreter(Controller _controller, ParseTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            tgc.clearRect(0, 0, treeRepresentation.getWidth(), treeRepresentation.getHeight());
            this.tgc = this.treeRepresentation.getGraphicsContext2D();
            // Clear the TruthTreeView parentPane of the the treeRepresentation.
            this.truthTreeView.getParentPane().getChildren().remove(this.treeRepresentation);
            // Create the View of the tree from the first child of the root.
            this.getTotalWidth(((SolvedFormulaEvent) _event).getWffTree().getChild(0), 0);
            this.createTree(((SolvedFormulaEvent) _event).getWffTree().getChild(0), (this.treeRepresentation.getWidth() / 2) - this.totalWidth / 2, this.treeRepresentation.getHeight() / 2 - ((this.totalHeight * nodeHeight) / 2), 0);
            // Setting VBox treeRepresentation properties.
            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treeRepresentation);
            this.controller.setPaneToPannable(this.treeRepresentation);
            this.controller.setPaneToZoomable(this.treeRepresentation);


        }
    }
    //((((Pxyzabcd & (Eqqqq <-> ~(x)(y)(z)Exyzd)) | (x)(w)Zxw) -> ~Eaaaa) & ~Pcdefgh)
    private double nodeWidth= 10;
    private double nodeHeight = 50;
    double curWidth = 0;
    public double[] createTree(WffTree _wffTree, double _center, double _height, double _depth) {
        if(_depth == 0) {curWidth = 0;}
        Text rootWffText = _wffTree.isPredicate() ? new Text(_wffTree.getStringRep()) : new Text(_wffTree.getSymbol());
        if(_wffTree.getChildrenSize() >= 1) {
            double[] leftchildProperties = createTree(_wffTree.getChild(0), _center, _height, _depth + 1);
            curWidth += nodeWidth;
            curWidth += rootWffText.getLayoutBounds().getWidth();
            this.tgc.strokeLine(_center + curWidth + (rootWffText.getBoundsInParent().getWidth() / 2), _height + (_depth * nodeHeight) + (rootWffText.getBoundsInParent().getHeight()/ 2), leftchildProperties[0], leftchildProperties[1]);
            double parentWidth = _center + curWidth;
            double parentHeight = _height + (_depth * nodeHeight);
            this.tgc.fillText(rootWffText.getText(), _center + curWidth, _height + (_depth * nodeHeight));
            if (_wffTree.getChildrenSize() == 2) {
                double[] rightchildProperties = createTree(_wffTree.getChild(1), _center, _height, _depth + 1);
                this.tgc.strokeLine(parentWidth + (rootWffText.getBoundsInParent().getWidth() / 2), parentHeight + (rootWffText.getBoundsInParent().getHeight()/ 2), rightchildProperties[0], rightchildProperties[1]);
                return new double[] {parentWidth + (rootWffText.getBoundsInParent().getWidth() / 2), parentHeight - rootWffText.getBoundsInParent().getHeight()};
            }
            return new double[] {parentWidth + (rootWffText.getBoundsInParent().getWidth() / 2), parentHeight - rootWffText.getBoundsInParent().getHeight()};
        }
        else {
            curWidth += nodeWidth;
            curWidth += rootWffText.getLayoutBounds().getWidth();
            this.tgc.fillText(rootWffText.getText(), _center + curWidth, _height + (_depth * nodeHeight));
            return new double[] {_center + curWidth + (rootWffText.getBoundsInParent().getWidth() / 2), _height + (_depth * nodeHeight) - rootWffText.getBoundsInParent().getHeight()};
        }
    }

    double totalWidth = 0;
    double totalHeight = 0;
    public void getTotalWidth(WffTree _wffTree, int _depth) {
        if(_depth == 0) {totalWidth = 0; totalHeight = 0;}
        if (_wffTree.getChildrenSize() == 2) {
            getTotalWidth(_wffTree.getChild(0),_depth + 1);
            totalWidth += nodeWidth;
            getTotalWidth(_wffTree.getChild(1),_depth + 1);
        }
        else {
            if (_depth > totalHeight) {totalHeight = _depth;}
            totalWidth += nodeWidth;
        }
    }

}










