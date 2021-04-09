package com.llat.views.interpreters;

import com.llat.algorithms.models.TruthTree;
import com.llat.controller.Controller;
import com.llat.models.events.UpdateViewTruthTreeEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.TruthTreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
public class TruthTreeInterpreter implements Listener {

    /**
     * Color to use when drawing the text/symbol(s).
     */
    private static final Color TEXT_COLOR = Color.BLACK;

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final TruthTreeView truthTreeView;

    /**
     *
     */
    private Pane treePane;

    public TruthTreeInterpreter(Controller _controller, TruthTreeView _truthTreeView) {
        this.controller = _controller;
        this.truthTreeView = _truthTreeView;
        this.treePane = new Pane();
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof UpdateViewTruthTreeEvent) {
            this.truthTreeView.getParentPane().setVisible(true);
            if (this.treePane != null) {
                this.truthTreeView.getParentPane().getChildren().remove(this.treePane);
                this.treePane.getChildren().clear();
                this.treePane = new Pane();
            }

            if (((UpdateViewTruthTreeEvent) _event).isEmpty()) {
                return;
            }

            TruthTree truthTree = ((UpdateViewTruthTreeEvent) _event).getTruthTree();
            TreeForTreeLayout<TruthTree> tree = this.convertToAbegoTree(truthTree);

            // Setup the tree layout configuration.
            double gapBetweenLevels = 10;
            double gapBetweenNodes = 20;
            DefaultConfiguration<TruthTree> configuration = new DefaultConfiguration<>(
                    gapBetweenLevels, gapBetweenNodes);

            // Create the NodeExtentProvider for TruthTree nodes.
            TruthTreeExtentProvider nodeExtentProvider = new TruthTreeExtentProvider();

            // Create the layout.
            TreeLayout<TruthTree> treeLayout = new TreeLayout<>(tree,
                    nodeExtentProvider, configuration);

            this.drawTree(treeLayout);

            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treePane);
            this.treePane.setTranslateX((this.truthTreeView.getParentPane().getWidth() / 2));
            this.treePane.setTranslateY((this.truthTreeView.getParentPane().getHeight() / 2));

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
    private void drawTree(TreeLayout<TruthTree> _layout) {
        this.drawEdges(_layout, _layout.getTree().getRoot());
        for (TruthTree truthTree : _layout.getNodeBounds().keySet()) {
            this.paintBox(_layout, truthTree);
        }
    }

    /**
     * Draws the edges from the TruthTree passed in to all of its children.
     *
     * @param _layout - TreeLayout object constructed from tree library Abego.
     * @param _tree   - tree to draw edge(s) from. Draws edges from this node
     *                to all children.
     */
    private void drawEdges(TreeLayout<TruthTree> _layout, TruthTree _tree) {
        if (!_layout.getTree().isLeaf(_tree)) {
            Rectangle2D b1 = _layout.getNodeBounds().get(_tree);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (TruthTree child : _layout.getTree().getChildren(_tree)) {
                Rectangle2D.Double b2 = _layout.getNodeBounds().get(child);
                // Compute offsets to position it in the center of the screen.
                double x1Off = x1;
                double y1Off = y1;
                double x2Off = b2.getCenterX();
                double y2Off = b2.getCenterY();
                if (_tree.getRight() != null) {
                    this.treePane.getChildren().add(new Line(x1Off, y1Off, x2Off, y2Off));
                }
                this.drawEdges(_layout, child);
            }
        }
    }

    /**
     * Draws the node in the tree. Each node is assigned a "box", which has a corresponding
     * position. This position is defined by the tree in the library.
     *
     * @param _layout    - TreeLayout constructed by the library.
     * @param _truthTree - TruthTree object to construct.
     */
    private void paintBox(TreeLayout<TruthTree> _layout, TruthTree _truthTree) {
        Rectangle2D.Double box = _layout.getNodeBounds().get(_truthTree);

        // Set up the offsets to center the tree.
        double xOffset = box.x;
        double yOffset = box.y;

        // Constructs the node and the borders.
        TruthTreeInterpreter.TruthTreeGuiNode nodeBox = new TruthTreeInterpreter.TruthTreeGuiNode(_truthTree, this.treePane, xOffset, yOffset, box.width, box.height);

        // Finally, draw and position the text.
        Text wffSymbol = new Text(_truthTree.getWff().getStringRep());
        wffSymbol.setFill(TruthTreeInterpreter.TEXT_COLOR);
        wffSymbol.setX(nodeBox.getX() + (nodeBox.getWidth() - wffSymbol.getBoundsInLocal().getWidth()) / 2);
        wffSymbol.setY(nodeBox.getY() + nodeBox.getHeight() / 1.30d);

        // If the branch is a leaf, then we can label it open or closed.
        Text branchLabel = null;
        if (_truthTree.isLeafNode()) {
            if (_truthTree.isClosed()) {
                branchLabel = new Text("X");
            } else {
                branchLabel = new Text("OPEN");
            }
        }

        if (branchLabel != null) {
            branchLabel.setX(nodeBox.getX() + (nodeBox.getWidth() - branchLabel.getBoundsInLocal().getWidth()) / 2);
            branchLabel.setY(nodeBox.getY() + 32);
            this.treePane.getChildren().add(branchLabel);
        }

        // Add all the children to the tree.
        this.treePane.getChildren().addAll(wffSymbol);
    }

    /**
     * Converts a TruthTree and its children into the tree required by the Tree
     * building library Abego. The root is added to the tree in the above method
     * so we start off by enqueueing it, then traversing through its children in BFS
     * fashion. Each child is added to this queue and added to the tree at the same
     * time. We use a BFS because we have to tell the library which parent each
     * node belongs to.
     *
     * @param _root - root of TruthTree.
     * @return TreeForTreeLayout<TruthTree> constructed tree from Abego library.
     */
    private TreeForTreeLayout<TruthTree> convertToAbegoTree(TruthTree _root) {
        Queue<TruthTree> q = new LinkedList<>();
        DefaultTreeForTreeLayout<TruthTree> tree = new DefaultTreeForTreeLayout<TruthTree>(_root);
        q.add(_root);
        while (!q.isEmpty()) {
            TruthTree t = q.poll();
            if (t.getLeft() != null) {
                q.add(t.getLeft());
                tree.addChild(t, t.getLeft());
            }

            if (t.getRight() != null) {
                q.add(t.getRight());
                tree.addChild(t, t.getRight());
            }

        }

        return tree;
    }

    /**
     * This class provides the attributes for the tree library - it determines
     * the positioning and sizing of each node in the GUI.
     */
    private static class TruthTreeExtentProvider implements NodeExtentProvider<TruthTree> {

        /**
         * Default width for a node that only has one or two chars as their symbol.
         */
        private static final int SMALL_WFF_WIDTH = 25;

        /**
         * Multiplier for nodes that contain > 2 chars. The multiplier grows the node.
         */
        private static final int LARGE_WFF_WIDTH_MULTIPLER = 7;

        /**
         * Height for each WFF.
         */
        private static final int WFF_HEIGHT = 15;

        @Override
        public double getWidth(TruthTree treeNode) {
            String s = treeNode.getWff().getStringRep();
            if (s == null) {
                return 0;
            }
            if (s.length() <= 2) {
                return TruthTreeExtentProvider.SMALL_WFF_WIDTH;
            }

            return s.length() * TruthTreeExtentProvider.LARGE_WFF_WIDTH_MULTIPLER;
        }

        @Override
        public double getHeight(TruthTree treeNode) {
            return TruthTreeExtentProvider.WFF_HEIGHT;
        }
    }

    /**
     * Creates a TruthTree node for display in the GUI. Pass in the x, y, width, and height for
     * the backing rectangle. This class also adds the objects to the Pane in the constructor
     * after drawing the borders.
     * <p>
     * Hopefully, by encapsulating this in a class, we can add listeners or whatever else easily.
     */
    private static class TruthTreeGuiNode extends Rectangle {

        /**
         * Color for the outer bottom and right borders.
         */
        private static final Color BORDER_COLOR = Color.BLACK;

        /**
         * Color for the box node itself.
         */
        private static final Color BOX_COLOR = Color.color(1, 1, 1);

        /**
         * Pane to attach this TruthTreeGuiNode to and its border children (lines).
         */
        private final Pane PANE;

        /**
         * Backing TruthTree for this gui node.
         */
        private final TruthTree TRUTH_TREE;

        public TruthTreeGuiNode(TruthTree _tree, Pane _pane, double _x, double _y, double _w, double _h) {
            super(_x, _y, _w, _h);
            this.TRUTH_TREE = _tree;
            this.PANE = _pane;

            // Add the lines and decorations to the pane.
            // First, draw the box itself.
            this.setFill(TruthTreeGuiNode.BOX_COLOR);
            this.PANE.getChildren().addAll(this);
        }

        public Pane getPane() {
            return this.PANE;
        }
    }
}
