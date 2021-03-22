package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.uidescription.UIDescriptionAdaptor;
import com.llat.models.uidescription.UIDescriptionObject;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InputButtonsView {
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

    Controller controller;
    Stage stage;
    AnchorPane parentPane = new AnchorPane();
    VBox inputButtonVBox = new VBox();
    GridPane propositionalLogicPane = new GridPane();

    GridPane predicateLogicPane = new GridPane();

    public InputButtonsView (Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        // Setting VBox inputButtonVBox properties.
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> { this.inputButtonVBox.setMinWidth(newVal.doubleValue() * .20); });
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> { this.inputButtonVBox.setMinHeight(newVal.doubleValue()); });
        this.inputButtonVBox.setLayoutX(0);
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> { this.inputButtonVBox.setLayoutY((newVal.doubleValue() * .50) - this.inputButtonVBox.getMinHeight() / 2); });
        // Setting Buttons propositionalLogicSymbols properties.
        UIDescriptionAdaptor buttonsInformation = new UIDescriptionAdaptor();
        UIDescriptionObject obj = (UIDescriptionObject) buttonsInformation.getData().getObject();
        obj.getImplication().getSymbol().getApplied();

        Button conjunctionButton = new Button(obj.getConjunction().getSymbol().getApplied());
        Button disjunctionButton = new Button(obj.getDisjunction().getSymbol().getApplied());
        Button negationButton = new Button(obj.getNegation().getSymbol().getApplied());






        ArrayList<String> rowCountArray = new ArrayList<>();
        this.propositionalLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = new Button(buttonText);
            curButton.setId("propositionalLogicSymbolButtons");
            GridPane.setRowIndex(curButton,rowCountArray.size());
            GridPane.setColumnIndex(curButton,this.propositionalLogicPane.getChildren().size() % 4);
            this.propositionalLogicPane.getChildren().add(curButton);
            if (this.propositionalLogicPane.getChildren().size() % 4 == 0) { rowCountArray.add(""); }
            /*
            this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                if(this.loginVBox.getHeight() > this.loginVBox.getWidth()) {
                    this.logoImage.setMinWidth(newVal.doubleValue() * .40);
                    this.logoImage.setMinHeight(newVal.doubleValue() * .40);
                }
                else {
                    this.logoImage.setMinWidth(this.loginVBox.getHeight() * .40);
                    this.logoImage.setMinHeight(this.loginVBox.getHeight() * .40);
                }
            });
            this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            }); */
        });
        this.inputButtonVBox.getChildren().addAll(this.propositionalLogicPane, this.predicateLogicPane);
        this.inputButtonVBox.setId("test2");
        this.parentPane.getChildren().addAll(this.inputButtonVBox);
    }

    public Pane getParentPane() { return this.parentPane; }

}
