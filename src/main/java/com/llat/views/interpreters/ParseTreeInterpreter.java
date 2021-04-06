package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.ParseTreeView;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class ParseTreeInterpreter implements Listener {

    /**
     *
     */
    private Controller controller;

    /**
     *
     */
    private ParseTreeView truthTreeView;

    /**
     *
     */
    private Pane treePane;

    /**
     * Color to use when drawing the text/symbol(s).
     */
    private static final Color TEXT_COLOR = Color.BLACK;

    public ParseTreeInterpreter(Controller _controller, ParseTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        this.treePane = new Pane();
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            if (this.treePane != null) {
                this.truthTreeView.getParentPane().getChildren().remove(this.treePane);
            }
            this.treePane = new Pane();

            WffTree wff = ((SolvedFormulaEvent) _event).getWffTree().getChild(0);
            wff.clearHighlighting();
            TreeForTreeLayout<WffTree> tree = this.convertToAbegoTree(wff);

            // Setup the tree layout configuration.
            double gapBetweenLevels = 20;
            double gapBetweenNodes = 20;
            DefaultConfiguration<WffTree> configuration = new DefaultConfiguration<WffTree>(
                    gapBetweenLevels, gapBetweenNodes);

            // Create the NodeExtentProvider for WffTree nodes.
            WffTreeExtentProvider nodeExtentProvider = new WffTreeExtentProvider();

            // Create the layout.
            TreeLayout<WffTree> treeLayout = new TreeLayout<WffTree>(tree,
                    nodeExtentProvider, configuration);

            this.drawTree(treeLayout);

            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treePane);
            this.controller.setPaneToPannable(this.treePane);
            this.controller.setPaneToZoomable(this.treePane);
        }
    }

    /**
     * Draws the Abego tree in the Canvas. This may need to be adjusted, but it
     * looks like it works for now.
     *
     * @param _layout - Tree constructed by Abego.
     */
    private void drawTree(TreeLayout<WffTree> _layout) {
        this.drawEdges(_layout, _layout.getTree().getRoot());
        for (WffTree wffTree : _layout.getNodeBounds().keySet()) {
            this.paintBox(_layout, wffTree);
        }
    }

    /**
     * Draws the edges from the WffTree passed in to all of its children.
     *
     * @param _layout - TreeLayout object constructed from tree library Abego.
     * @param _tree - tree to draw edge(s) from. Draws edges from this node
     *                to all children.
     */
    private void drawEdges(TreeLayout<WffTree> _layout, WffTree _tree) {
        if (!_layout.getTree().isLeaf(_tree)) {
                Rectangle2D b1 = _layout.getNodeBounds().get(_tree);
                double x1 = b1.getCenterX();
                double y1 = b1.getCenterY();
                for (WffTree child : _layout.getTree().getChildren(_tree)) {
                    Rectangle2D.Double b2 = _layout.getNodeBounds().get(child);
                    // Compute offsets to position it in the center of the screen.
                    double x1Off = x1 + this.truthTreeView.getParentPane().getWidth() / 2;
                    double y1Off = y1 + this.truthTreeView.getParentPane().getHeight() / 2;
                    double x2Off = b2.getCenterX() + this.truthTreeView.getParentPane().getWidth() / 2;
                    double y2Off = b2.getCenterY() + this.truthTreeView.getParentPane().getHeight() / 2;
                    this.treePane.getChildren().add(new Line(x1Off, y1Off, x2Off, y2Off));
                    this.drawEdges(_layout, child);
            }
        }
    }

    /**
     * Draws the node in the tree. Each node is assigned a "box", which has a corresponding
     * position. This position is defined by the tree in the library.
     *
     * @param _layout - TreeLayout constructed by the library.
     * @param _wffTree - WffTree object to construct.
     */
    private void paintBox(TreeLayout<WffTree> _layout, WffTree _wffTree) {
        Rectangle2D.Double box = _layout.getNodeBounds().get(_wffTree);

        // Set up the offsets to center the tree.
        double xOffset = box.x + this.truthTreeView.getParentPane().getWidth() / 2;
        double yOffset = box.y + this.truthTreeView.getParentPane().getHeight() / 2;

        // Constructs the node and the borders.
        WffTreeGuiNode nodeBox = new WffTreeGuiNode(_wffTree, this.treePane, xOffset, yOffset, box.width, box.height);

        // Finally, draw and position the text.
        Text wffSymbol = new Text(_wffTree.getSymbol());
        wffSymbol.setFill(ParseTreeInterpreter.TEXT_COLOR);
        wffSymbol.setX(nodeBox.getX() + (nodeBox.getWidth() - wffSymbol.getBoundsInLocal().getWidth()) / 2);
        wffSymbol.setY(nodeBox.getY() + nodeBox.getHeight() / 1.30d);

        // Add all the children to the tree.
        this.treePane.getChildren().addAll(wffSymbol);
    }

    /**
     * Converts a WffTree and its children into the tree required by the Tree
     * building library Abego. The root is added to the tree in the above method
     * so we start off by enqueueing it, then traversing through its children in BFS
     * fashion. Each child is added to this queue and added to the tree at the same
     * time. We use a BFS because we have to tell the library which parent each
     * node belongs to.
     *
     * @param _root - root of WffTree.
     *
     * @return TreeForTreeLayout<WffTree> constructed tree from Abego library.
     */
    private TreeForTreeLayout<WffTree> convertToAbegoTree(WffTree _root) {
        Queue<WffTree> q = new LinkedList<>();
        DefaultTreeForTreeLayout<WffTree> tree = new DefaultTreeForTreeLayout<WffTree>(_root);
        q.add(_root);
        while (!q.isEmpty()) {
            WffTree t = q.poll();
            for (WffTree ch : t.getChildren()) {
                q.add(ch);
                tree.addChild(t, ch);
            }
        }

        return tree;
    }

    /**
     * This class provides the attributes for the tree library - it determines
     * the positioning and sizing of each node in the GUI.
     */
    private static class WffTreeExtentProvider implements NodeExtentProvider<WffTree> {

        /**
         * Default width for a node that only has one or two chars as their symbol.
         */
        private static final int SMALL_WFF_WIDTH = 25;

        /**
         * Multiplier for nodes that contain > 2 chars. The multiplier grows the node.
         */
        private static final int LARGE_WFF_WIDTH_MULTIPLER = 4;

        /**
         * Height for each WFF.
         */
        private static final int WFF_HEIGHT = 15;

        @Override
        public double getWidth(WffTree treeNode) {
            String s = treeNode.getSymbol();
            if (s.length() <= 2) {
                return WffTreeExtentProvider.SMALL_WFF_WIDTH;
            }

            return s.length() * WffTreeExtentProvider.LARGE_WFF_WIDTH_MULTIPLER;
        }

        @Override
        public double getHeight(WffTree treeNode) {
            return WffTreeExtentProvider.WFF_HEIGHT;
        }
    }

    /**
     * Creates a WffTree node for display in the GUI. Pass in the x, y, width, and height for
     * the backing rectangle. This class also adds the objects to the Pane in the constructor
     * after drawing the borders.
     *
     * Hopefully, by encapsulating this in a class, we can add listeners or whatever else easily.
     */
    private static class WffTreeGuiNode extends Rectangle {

        /**
         * Backing WffTree for this gui node.
         */
        private WffTree WFF_TREE;

        /**
         * Pane to attach this WffTreeGuiNode to and its border children (lines).
         */
        private final Pane PANE;

        /**
         * Color for the inner left and top borders.
         */
        private static final Color INNER_LEFT_BORDER_COLOR = Color.color(0.84314f, 0.97647f, 0.49412f);

        /**
         * Color for the inner bottom and right borders.
         */
        private static final Color INNER_RIGHT_BORDER_COLOR = Color.color(0.35686f, 0.45490f, 0.23137f);

        /**
         * Color for the outer left and top borders.
         */
        private static final Color OUTER_LEFT_BORDER_COLOR = Color.color(0.99608f, 0.98431f, 0.58431f);

        /**
         * Color for the outer bottom and right borders.
         */
        private static final Color OUTER_RIGHT_BORDER_COLOR = Color.BLACK;

        /**
         * Color for the box node itself.
         */
        private static final Color BOX_COLOR = Color.color(0.54902f, 0.70980f, 0.35294);

        /**
         * Color for when the node is highlighted by an algorithm.
         */
        private static final Color HIGHLIGHTED_COLOR = Color.YELLOW;

        public WffTreeGuiNode(WffTree _tree, Pane _pane, double _x, double _y, double _w, double _h) {
            super(_x, _y, _w, _h);
            this.WFF_TREE = _tree;
            this.PANE = _pane;

            // Add the lines and decorations to the pane.
            // First, draw the box itself.
            this.setFill(this.WFF_TREE.isHighlighted() ? WffTreeGuiNode.HIGHLIGHTED_COLOR : WffTreeGuiNode.BOX_COLOR);

            // Draw the left-bot and top-right inner borders.
            Line innerLeftToBot = new Line(_x, _y, _x, _y + _h);
            Line innerTopToRight = new Line(_x, _y, _x + _w, _y);
            innerLeftToBot.setStroke(WffTreeGuiNode.INNER_LEFT_BORDER_COLOR);
            innerTopToRight.setStroke(WffTreeGuiNode.INNER_LEFT_BORDER_COLOR);

            // Draw the bot-right and top-right-bot inner borders.
            Line innerBotToRight = new Line(_x, _y + _h, _x + _w, _y + _h);
            Line innerTopRightToBot = new Line(_x + _w, _y, _x + _w, _y + _h);
            innerBotToRight.setStroke(WffTreeGuiNode.INNER_RIGHT_BORDER_COLOR);
            innerTopRightToBot.setStroke(WffTreeGuiNode.INNER_RIGHT_BORDER_COLOR);

            // Draw the left-bot and top-right outer borders.
            Line outerLeftToBot = new Line(_x - 1, _y - 1, _x - 1, _y + _h);
            Line outerTopToRight = new Line(_x - 1, _y - 1, _x + _w, _y - 1);
            outerLeftToBot.setStroke(WffTreeGuiNode.OUTER_LEFT_BORDER_COLOR);
            outerTopToRight.setStroke(WffTreeGuiNode.OUTER_LEFT_BORDER_COLOR);

            // Draw the bot-right and top-right-bot outer borders.
            Line outerBotToRight = new Line(_x - 1, _y + _h + 1, _x + _w + 1, _y + _h + 1);
            Line outerTopRightToBot = new Line(_x + _w + 1, _y - 1, _x + _w + 1, _y + _h + 1);
            outerBotToRight.setStroke(WffTreeGuiNode.OUTER_RIGHT_BORDER_COLOR);
            outerTopRightToBot.setStroke(WffTreeGuiNode.OUTER_RIGHT_BORDER_COLOR);

            this.PANE.getChildren().addAll(this, innerLeftToBot, innerTopToRight, innerBotToRight, innerTopRightToBot,
                    outerLeftToBot, outerTopToRight, outerBotToRight, outerTopRightToBot);
        }

        public Pane getPane() {
            return this.PANE;
        }
    }
}
