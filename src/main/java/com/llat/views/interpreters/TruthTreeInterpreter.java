package com.llat.views.interpreters;

import com.llat.algorithms.models.TruthTree;
import com.llat.controller.Controller;
import com.llat.input.events.UnsolvedFormulaEvent;
import com.llat.models.events.UpdateViewTruthTreeEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.TruthTreeView;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    private final int TREE_LEVEL_GAP = 40;

    /**
     *
     */
    private final int TREE_NODE_GAP = 50;

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
            TreeForTreeLayout<TruthTreeGuiNode> tree = this.convertToAbegoTree(truthTree);

            // Setup the tree layout configuration.
            DefaultConfiguration<TruthTreeGuiNode> configuration = new DefaultConfiguration<>(
                    this.TREE_LEVEL_GAP, this.TREE_NODE_GAP);

            // Create the NodeExtentProvider for TruthTree nodes.
            TruthTreeExtentProvider nodeExtentProvider = new TruthTreeExtentProvider();

            // Create the layout.
            TreeLayout<TruthTreeGuiNode> treeLayout = new TreeLayout<>(tree,
                    nodeExtentProvider, configuration);

            this.drawTree(treeLayout);

            // Adding children nodes to their parents nodes.
            this.truthTreeView.getParentPane().getChildren().add(this.treePane);
            this.treePane.setTranslateX((this.truthTreeView.getParentPane().getWidth() / 2) - ((this.treePane.getWidth() / 2)));
            this.treePane.setTranslateY((this.truthTreeView.getParentPane().getHeight() / 2) - ((this.treePane.getHeight() / 2)));
            this.truthTreeView.getParentPane().widthProperty().addListener((obs, oldVal, newVal) -> {
                this.treePane.setTranslateX((newVal.doubleValue() / 2) - (this.treePane.getWidth() / 2));
            });

            this.truthTreeView.getParentPane().heightProperty().addListener((obs, oldVal, newVal) -> {
                this.treePane.setTranslateY((newVal.doubleValue() / 2) - (this.treePane.getHeight() / 2));
            });

            this.controller.setPaneToPannable(this.treePane);
            this.controller.setPaneToZoomable(this.treePane);
        } else if (_event instanceof UnsolvedFormulaEvent) {
            if (this.treePane != null) {
                this.truthTreeView.getParentPane().getChildren().remove(this.treePane);
            }
        }
    }

    /**
     * Draws the Abego tree in the Canvas. This may need to be adjusted, but it
     * looks like it works for now.
     *
     * @param _layout - Tree constructed by Abego.
     */
    private void drawTree(TreeLayout<TruthTreeGuiNode> _layout) {
        this.drawEdges(_layout, _layout.getTree().getRoot());
        for (TruthTreeGuiNode truthTree : _layout.getNodeBounds().keySet()) {
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
    private void drawEdges(TreeLayout<TruthTreeGuiNode> _layout, TruthTreeGuiNode _tree) {
        final int V_OFFSET = 12;
        final int H_OFFSET = 2;

        // Grab the parent node and its child then draw a line.
        if (!_layout.getTree().isLeaf(_tree)) {
            Rectangle2D b1 = _layout.getNodeBounds().get(_tree);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (TruthTreeGuiNode child : _layout.getTree().getChildren(_tree)) {
                Rectangle2D.Double b2 = _layout.getNodeBounds().get(child);
                // Compute offsets to position it in the center of the screen.
                double x1Off = x1;
                double y1Off = y1 + b1.getHeight() / 2 + H_OFFSET;
                double x2Off = b2.getCenterX();
                double y2Off = b2.getCenterY() - b2.getHeight() / 2 - V_OFFSET;
                this.treePane.getChildren().add(new Line(x1Off, y1Off, x2Off, y2Off));
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
    private void paintBox(TreeLayout<TruthTreeGuiNode> _layout, TruthTreeGuiNode _truthTree) {
        final int V_OFFSET = 12;
        final int Y_LABEL_OFFSET = 6;
        Rectangle2D.Double box = _layout.getNodeBounds().get(_truthTree);
        double prevY = box.y;
        String[] lines = _truthTree.text.split("\n");
        // Stacked nodes are made up of multiple lines, so this breaks them apart.
        for (String line : lines) {
            Text wffSymbolText = new Text(line);
            Label wffSymbol = new Label(line);
            wffSymbol.setLayoutX(box.getX() + (box.getWidth() - wffSymbolText.getBoundsInLocal().getWidth()) / 2);
            wffSymbol.setLayoutY(prevY - Y_LABEL_OFFSET);
            prevY += V_OFFSET;

            wffSymbol.setTooltip(_truthTree.createTooltip());
            this.treePane.getChildren().add(wffSymbol);
        }

        // If the branch is a leaf, then we can label it open or closed.
        Text branchText = null;
        Label branchLabel = null;

        if (_layout.getTree().isLeaf(_truthTree)) {
            if (_truthTree.isClosed()) {
                branchText = new Text("✕");
                branchLabel = new Label("✕");
            } else {
                branchText = new Text("OPEN");
                branchLabel = new Label("OPEN");
            }
        }

        if (branchLabel != null) {
            branchLabel.setTranslateX(box.getX() + (box.getWidth() - branchText.getBoundsInLocal().getWidth()) / 2);
            branchLabel.setTranslateY(prevY + Y_LABEL_OFFSET);
            this.treePane.getChildren().add(branchLabel);
        }
    }

    /**
     *
     */
    private TreeForTreeLayout<TruthTreeGuiNode> convertToAbegoTree(TruthTree _root) {
        Queue<TruthTreeGuiNode> q = new LinkedList<>();

        TruthTreeGuiNode guiNode = new TruthTreeGuiNode(_root, this.treePane);
        TruthTreeGuiNode stackRoot = null;
        TruthTreeGuiNode t = null;

        DefaultTreeForTreeLayout<TruthTreeGuiNode> tree = new DefaultTreeForTreeLayout<>(guiNode);
        q.add(guiNode);
        boolean branch = true;

        while (!q.isEmpty() || !branch) {
            // If we branch, then that means we reset the ptr.
            if (branch) {
                t = q.poll();
                stackRoot = t;
            }

            // If the right is null but the left isn't, it stacks.
            if (t.getLeft() != null && t.getRight() == null) {
                stackRoot.stackNode(t.getLeft());
                t = new TruthTreeGuiNode(t.getLeft(), this.treePane);
                branch = false;
            } else if (t.getRight() != null) {
                // In here we branch and add to the queue.
                TruthTreeGuiNode left = new TruthTreeGuiNode(t.getLeft(), this.treePane);
                TruthTreeGuiNode right = new TruthTreeGuiNode(t.getRight(), this.treePane);
                q.add(left);
                q.add(right);
                tree.addChild(stackRoot, left);
                tree.addChild(stackRoot, right);
                branch = true;
            }

            // Once we find the end of the tree, just break out.
            if (t.getLeft() == null && t.getRight() == null) {
                branch = true;
                if (q.isEmpty()) {
                    break;
                }
            }
        }

        return tree;
    }

    /**
     * This class provides the attributes for the tree library - it determines
     * the positioning and sizing of each node in the GUI.
     */
    private static class TruthTreeExtentProvider implements NodeExtentProvider<TruthTreeGuiNode> {

        @Override
        public double getWidth(TruthTreeGuiNode treeNode) {
            return treeNode.width;
        }

        @Override
        public double getHeight(TruthTreeGuiNode treeNode) {
            return treeNode.height;
        }
    }

    /**
     * Creates a TruthTree node for display in the GUI.
     * <p>
     * Hopefully, by encapsulating this in a class, we can add listeners or whatever else easily.
     */
    private static class TruthTreeGuiNode {

        /**
         *
         */
        private static final int RESIZE_WIDTH_DELTA = 24;

        /**
         *
         */
        private static final int RESIZE_HEIGHT_DELTA = 12;

        /**
         * Pane to attach this TruthTreeGuiNode to and its border children (lines).
         */
        private final Pane PANE;

        /**
         *
         */
        private final LinkedList<TruthTree> truthTrees;

        /**
         *
         */
        private String text;

        /**
         *
         */
        private double width;

        /**
         *
         */
        private double height;

        public TruthTreeGuiNode(TruthTree _tree, Pane _pane) {
            this.truthTrees = new LinkedList<>();
            this.truthTrees.add(_tree);
            this.PANE = _pane;
            this.text = _tree.getWff().getStringRep() + "\n";
            this.height = 12;
            this.width = this.text.length() * 10;
        }

        /**
         * @param _tree
         */
        public void stackNode(TruthTree _tree) {
            this.truthTrees.add(_tree);
            this.text += _tree.getWff().getStringRep() + "\n";
            this.width += RESIZE_WIDTH_DELTA;
            this.height += RESIZE_HEIGHT_DELTA;
        }

        public TruthTree getLeft() {
            return this.truthTrees.get(0).getLeft();
        }

        public TruthTree getRight() {
            return this.truthTrees.get(0).getRight();
        }

        public boolean isClosed() {
            for (TruthTree t : this.truthTrees) {
                if (t.isClosed()) {
                    return true;
                }
            }
            return false;
        }

        public Pane getPane() {
            return this.PANE;
        }

        /**
         * @return
         */
        public Tooltip createTooltip() {
            StringBuilder sb = new StringBuilder();
            for (TruthTree t : this.truthTrees) {
                if (t.getDerivedParent() != null) {
                    sb.append(t.getIdentityNumber());
                    sb.append(". (");
                    sb.append(t.getDerivedParent().getIdentityNumber());
                    sb.append(") ");
                    sb.append(t.getDerivedParent().getWff().getSymbol());
                } else {
                    sb.append("1. ROOT");
                }
                sb.append("\n");
            }

            return new Tooltip(sb.toString());
        }
    }
}

