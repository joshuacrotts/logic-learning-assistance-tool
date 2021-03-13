package com.llat.main.view;

import com.llat.main.tools.ResourceManager;
import com.llat.main.tools.ViewAssets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.llat.main.controller.Controller;
import com.llat.main.tools.NodeManager;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private AnchorPane parentPane = new AnchorPane();



    private VBox parentVBox = new VBox();

    private VBox propositionalLogicVBox = new VBox();
    private Label propositionalLogicLabel = new Label("Propositional Logic");
    private GridPane propositionalLogicGrid = new GridPane();
    private ArrayList<Button> propositionalLogicButtons = new ArrayList<Button>();
    private Separator propositionalLogicSeparator = new Separator();

    private VBox predicateLogicVBox = new VBox();
    private Label predicateLogicLabel = new Label("Predicate Logic");
    private GridPane predicateLogicGrid = new GridPane();
    private ArrayList<Button> predicateLogicButtons = new ArrayList<>();
    public SymbolInputView(Controller _controller) {
        this.controller = _controller;

        ArrayList<String> rowCountArray = new ArrayList<>();
        propositionalLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = NodeManager.createGridButton(buttonId, buttonText,propositionalLogicButtons.size() % 4, rowCountArray.size(), ResourceManager.getSymbolInputButtonCSS());
            propositionalLogicGrid.getChildren().add(curButton);
            propositionalLogicButtons.add(curButton);
            if (propositionalLogicButtons.size() % 4 == 0) { rowCountArray.add(""); }

        });

        rowCountArray.clear();
        predicateLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = NodeManager.createGridButton(buttonId, buttonText, predicateLogicButtons.size() % 4, rowCountArray.size(), ResourceManager.getSymbolInputButtonCSS());
            predicateLogicGrid.getChildren().add(curButton);
            predicateLogicButtons.add(curButton);
            if (predicateLogicButtons.size() % 4 == 0) { rowCountArray.add(""); }
        });

        propositionalLogicVBox.getChildren().add(propositionalLogicLabel);
        propositionalLogicVBox.getChildren().add(propositionalLogicGrid);
        propositionalLogicSeparator.setOrientation(Orientation.HORIZONTAL);
        propositionalLogicVBox.getChildren().add(propositionalLogicSeparator);

        predicateLogicVBox.getChildren().add(predicateLogicLabel);
        predicateLogicVBox.getChildren().add(predicateLogicGrid);

        parentVBox.getChildren().add(propositionalLogicVBox);
        parentVBox.getChildren().add(predicateLogicVBox);
        this.parentPane.getChildren().add(parentVBox);
    }

    public AnchorPane getParentPane() {
        return this.parentPane;
    }
}
