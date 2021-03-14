package com.llat.main.view;

import com.llat.main.controller.Controller;
import com.llat.main.tools.NodeManager;
import com.llat.main.tools.ResourceManager;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class FormulaInputView {
    Controller controller;
    AnchorPane parentPane;
    TextField formulaInput = NodeManager.createTextField("formulaInputField", 720, ResourceManager.getFormulaInputViewCSS());
    Button formulaInputButton = NodeManager.createButton("formulaInputButton", "Solve", ResourceManager.getFormulaInputViewCSS());
    HBox parentHBox;
    public FormulaInputView (Controller _controller) {
        this.controller = _controller;
        this.parentHBox = NodeManager.createHBox("parentHBox", 1280, 100, new ArrayList<Node>() {{add(formulaInput); add(formulaInputButton);}}, ResourceManager.getFormulaInputViewCSS());
        this.parentPane = NodeManager.createAnchorPane("parentPane", new ArrayList<Node>() {{add(parentHBox);}}, ResourceManager.getFormulaInputViewCSS());
    }
    public AnchorPane getParentPane() { return this.parentPane; }
}
