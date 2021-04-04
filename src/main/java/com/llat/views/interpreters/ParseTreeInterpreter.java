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
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Queue;


public class ParseTreeInterpreter implements Listener {
    double curWidth = 0;
    double totalWidth = 0;
    double totalHeight = 0;
    private Controller controller;
    private ParseTreeView truthTreeView;
    private Canvas treeRepresentation = new Canvas(1280, 1280);
    GraphicsContext tgc = treeRepresentation.getGraphicsContext2D();
    private double nodeWidth = 10;
    private double nodeHeight = 50;

    public ParseTreeInterpreter(Controller _controller, ParseTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            WffTree wff = ((SolvedFormulaEvent) _event).getWffTree().getChild(0);
            TreeForTreeLayout<WffTree> tree = this.convertToAbegoTree(wff);
            // setup the tree layout configuration
            double gapBetweenLevels = 50;
            double gapBetweenNodes = 10;
            DefaultConfiguration<WffTree> configuration = new DefaultConfiguration<WffTree>(
                    gapBetweenLevels, gapBetweenNodes);

            // create the NodeExtentProvider for TextInBox nodes
            TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

            // create the layout
            TreeLayout<WffTree> treeLayout = new TreeLayout<WffTree>(tree,
                    nodeExtentProvider, configuration);

            this.drawTree(treeLayout);
        }
    }

    private void drawTree(TreeLayout<WffTree> layout) {
        this.drawEdges(layout, layout.getTree().getRoot());

        // paint the boxes
        for (WffTree wffTree : layout.getNodeBounds().keySet()) {
            this.paintBox(layout, wffTree);
        }
    }

    private void drawEdges(TreeLayout<WffTree> layout, WffTree _tree) {
        if (!layout.getTree().isLeaf(_tree)) {
                Rectangle2D b1 = layout.getNodeBounds().get(_tree);
                double x1 = b1.getCenterX();
                double y1 = b1.getCenterY();
                for (WffTree child : layout.getTree().getChildren(_tree)) {
                    Rectangle2D.Double b2 = layout.getNodeBounds().get(child);
                    this.tgc.strokeLine(x1, y1, b2.getCenterX(), b2.getCenterY());
                    this.drawEdges(layout, child);
            }
        }
    }

    private void paintBox(TreeLayout<WffTree> layout, WffTree wffTree) {
        // draw the box in the background
        final int ARC_SIZE = 10;
        Rectangle2D.Double box = layout.getNodeBounds().get(wffTree);

        // draw the text on top of the box (possibly multiple lines)
        final Text text = new Text(wffTree.getSymbol());
        text.applyCss();
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y;
        tgc.fillText(wffTree.getSymbol(), x, y);
    }

    private TreeForTreeLayout<WffTree> convertToAbegoTree(WffTree _root) {
        Queue<WffTree> q = new LinkedList<>();
        DefaultTreeForTreeLayout<WffTree> tree = new DefaultTreeForTreeLayout<WffTree>(_root);
        q.add(_root);

        while (!q.isEmpty()) {
            WffTree t = q.poll();
            for (WffTree ch : t.getChildren()) {
                q.add(ch);
                tree.addChild(ch, t);
            }
        }

        return tree;
    }

    private class TextInBoxNodeExtentProvider implements
            NodeExtentProvider<WffTree> {

        @Override
        public double getWidth(WffTree treeNode) {
            return treeNode.getStringRep().length();
        }

        @Override
        public double getHeight(WffTree treeNode) {
            return 10;
        }
    }
}










