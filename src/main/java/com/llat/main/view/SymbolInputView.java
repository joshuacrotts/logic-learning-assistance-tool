package com.llat.main.view;

import com.llat.main.tools.ResourceManager;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.llat.main.controller.Controller;
import com.llat.main.tools.NodeManager;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SymbolInputView {
    LinkedHashMap<String, String> propositionalLogicSymbols = new LinkedHashMap<String, String>() {
        {
            put("andButton", "∧");
            put("orButton", "∨");
            put("negationButton", "¬");
            put("xorButton", "^");
            put("biconditionalButton", "⇔");
            put("implicationButton", "⇒");
            put("thereforeButton", "⊢");
            put("modelButton", "|=");
        }
    };
    LinkedHashMap<String, String> predicateLogicSymbols = new LinkedHashMap<String, String>() {
        {
            put("existentialQuantifierSymbol", "∃");
            put("universalQuantifierSymbol", "∀");
        }
    };

    private Controller controller;
    private AnchorPane parentPane;
    private VBox parentVBox;
    // Propositional logic attributes
    private VBox propositionalLogicVBox;
    private Label propositionalLogicLabel = NodeManager.createLabel("propositionalLogicLabel", "Propositional Logic", ResourceManager.getSymbolInputViewCSS());
    private GridPane propositionalLogicGrid;
    private ArrayList<Node> propositionalLogicButtons = new ArrayList<Node>();
    private Separator propositionalLogicSeparator;
    // Predicate logic attributes
    private VBox predicateLogicVBox;
    private Label predicateLogicLabel = NodeManager.createLabel("predicateLogicLabel", "Predicate Logic", ResourceManager.getSymbolInputViewCSS());
    private GridPane predicateLogicGrid;
    private ArrayList<Node> predicateLogicButtons = new ArrayList<Node>();

    public SymbolInputView(Controller _controller) {
        this.controller = _controller;

        ArrayList<String> rowCountArray = new ArrayList<>();
        propositionalLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = NodeManager.createGridButton(buttonId, buttonText,propositionalLogicButtons.size() % 4, rowCountArray.size(), ResourceManager.getSymbolInputViewCSS());
            propositionalLogicButtons.add(curButton);
            if (propositionalLogicButtons.size() % 4 == 0) { rowCountArray.add(""); }
        });
        this.propositionalLogicGrid = NodeManager.createGridPane("propositionalLogicGrid", propositionalLogicButtons, ResourceManager.getSymbolInputViewCSS());

        rowCountArray.clear();
        predicateLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = NodeManager.createGridButton(buttonId, buttonText, predicateLogicButtons.size() % 4, rowCountArray.size(), ResourceManager.getSymbolInputViewCSS());
            predicateLogicButtons.add(curButton);
            if (predicateLogicButtons.size() % 4 == 0) { rowCountArray.add(""); }
        });
        this.predicateLogicGrid = NodeManager.createGridPane("predicateLogicGrid", predicateLogicButtons, ResourceManager.getSymbolInputViewCSS());

        // Adding the propositional logic label and grid w/ buttons to their vbox.
        this.propositionalLogicSeparator = NodeManager.createSeparator("propositionalLogicSeparator", Orientation.HORIZONTAL, ResourceManager.getSymbolInputViewCSS());
        this.propositionalLogicVBox = NodeManager.createVBox("propositionalLogicVBox", 250, new ArrayList<Node>(){{add(propositionalLogicLabel); add(propositionalLogicGrid); add(propositionalLogicSeparator);}}, ResourceManager.getSymbolInputViewCSS());
        // Adding the predicate logic label and grid w/ buttons to their VBox.
        this.predicateLogicVBox = NodeManager.createVBox("predicateLogicVBox", 250, new ArrayList<Node>(){{add(predicateLogicLabel); add(predicateLogicGrid);}}, ResourceManager.getSymbolInputViewCSS());
        // Adding propositional and predicate logic VBox to the parent Anchor Pane.
        this.parentVBox = NodeManager.createVBox("parentVBox", 250, new ArrayList<Node>(){{add(propositionalLogicVBox); add(predicateLogicVBox);}}, ResourceManager.getSymbolInputViewCSS());
        this.parentPane = NodeManager.createAnchorPane("parentPane", new ArrayList<Node>(){{add(parentVBox);}}, ResourceManager.getSymbolInputViewCSS());
    }

    public AnchorPane getParentPane() { return this.parentPane; }

}
