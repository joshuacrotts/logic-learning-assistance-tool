package com.llat.main.view;

import com.llat.main.controller.Controller;
import com.llat.main.tools.NodeManager;
import com.llat.main.tools.ResourceManager;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RulesAxiomsView {
    Controller controller;
    private AnchorPane parentPane;
    private VBox parentVBox;
    private Label rulesAxiomsLabel = NodeManager.createLabel("rulesAxiomsLabel", "Rules/Axioms", ResourceManager.getRulesAxiomsViewCSS());

    public RulesAxiomsView(Controller _controller) {
        this.controller = _controller;
        this.parentVBox = NodeManager.createVBox("parentVBox", 250, new ArrayList<Node>() {{add(rulesAxiomsLabel);}}, ResourceManager.getRulesAxiomsViewCSS());
        this.parentPane = NodeManager.createAnchorPane("parentPane", new ArrayList<Node>() {{add(parentVBox);}}, ResourceManager.getRulesAxiomsViewCSS());
    }
    public AnchorPane getParentPane() {
        return this.parentPane;
    }
}
