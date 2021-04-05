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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private Controller controller;
    private ParseTreeView truthTreeView;
    private Canvas treeRepresentation = new Canvas(1280,  1280);
    private GraphicsContext tgc = treeRepresentation.getGraphicsContext2D();

    private static final Color BORDER_COLOR = Color.DARKGRAY;
    private static final Color HIGHLIGHTED_COLOR = Color.YELLOW;
    private static final Color BOX_COLOR = Color.ORANGE;
    private static final Color TEXT_COLOR = Color.BLACK;

    public ParseTreeInterpreter(Controller _controller, ParseTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            // Clear the screen before drawing the next tree.
            this.tgc.clearRect(0, 0, this.treeRepresentation.getWidth(), this.treeRepresentation.getHeight());
            this.tgc = this.treeRepresentation.getGraphicsContext2D();

            // Clear the TruthTreeView parentPane of the the treeRepresentation.
            this.truthTreeView.getParentPane().getChildren().remove(this.treeRepresentation);

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

            // Setting VBox treeRepresentation properties.
            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treeRepresentation);
            this.controller.setPaneToPannable(this.treeRepresentation);
            this.controller.setPaneToZoomable(this.treeRepresentation);
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
                    this.tgc.setStroke(Color.BLACK);
                    this.tgc.strokeLine(x1, y1, b2.getCenterX(), b2.getCenterY());
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
        final int ARC_SIZE = 10;
        // Draw the box in the background.
        Rectangle2D.Double box = _layout.getNodeBounds().get(_wffTree);

        // Draw the border first.
        this.tgc.setStroke(Color.DARKGRAY);
        this.tgc.strokeRoundRect(box.x - 1, box.y - 1, box.width + 1, box.height + 1, ARC_SIZE, ARC_SIZE);

        // Now draw the box itself.
        // If the node is highlighted, that means it's selected by an algorithm.
        if (_wffTree.isHighlighted()) {
            // Right now, there's nothing but this will change...
        } else {
            this.tgc.setFill(Color.ORANGE);
            this.tgc.fillRoundRect(box.x - 1, box.y - 1, box.width + 1, box.height + 1, ARC_SIZE, ARC_SIZE);
            this.tgc.setFill(Color.BLACK);
        }

        // Finally, draw and position the text.
        Text t = new Text(_wffTree.getSymbol());

        // These values may need to be adjuated...
        this.tgc.fillText(_wffTree.getSymbol(), box.getCenterX() - t.getBoundsInLocal().getWidth() / 2,
                                               box.getCenterY() + t.getBoundsInLocal().getHeight() / 4);
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
}
