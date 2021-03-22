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

    Controller controller;
    Stage stage;
    AnchorPane parentPane = new AnchorPane();
    VBox inputButtonVBox = new VBox();
    GridPane propositionalLogicPane = new GridPane();
    /*
    UIDescriptionObject obj = (UIDescriptionObject) (new UIDescriptionAdaptor()).getData().getObject();
    Button conjunctionButton = new Button(this.obj.getConjunction().getSymbol().getApplied());
    Button disjunctionButton = new Button(this.obj.getDisjunction().getSymbol().getApplied());
    Button negationButton = new Button(this.obj.getNegation().getSymbol().getApplied());
    Button exclusiveDisjunction = new Button(this.obj.getExclusiveDisjunction().getSymbol().getApplied());
    Button equivalenceButton = new Button(this.obj.getEquivalence().getSymbol().getApplied());
    Button implicationButton = new Button(this.obj.getImplication().getSymbol().getApplied());
    Button turnstileButton = new Button(this.obj.getTurnstile().getSymbol().getApplied());
    Button doubleTurnstile = new Button(this.obj.getDoubleTurnstile().getSymbol().getApplied());
    ArrayList<Button> propositionalLogicButtons = new ArrayList<Button>() {{ add(conjunctionButton); add(disjunctionButton); add(negationButton); add(exclusiveDisjunction);
                                                                             add(equivalenceButton); add(implicationButton); add(turnstileButton); add(doubleTurnstile); }};

     */
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
        System.out.println(obj.getImplication().getSymbol().getApplied());


        /*
        int rowCount = 0;
        int colCount = 0;
        for (Button curButton : this.propositionalLogicButtons) {
            curButton.setId("propositionalLogicButton");
            GridPane.setRowIndex(curButton,rowCount / 4);
            GridPane.setColumnIndex(curButton,rowCount % 4);
            this.propositionalLogicPane.getChildren().add(curButton);
        }

        this.propositionalLogicButtons.forEach((curButton) -> {
            curButton.setId("propositionalLogicButton");
            GridPane.setRowIndex(curButton,rowCountArray.size());
            GridPane.setColumnIndex(curButton,this.propositionalLogicPane.getChildren().size() % 4);
            this.propositionalLogicPane.getChildren().add(curButton);
            if (this.propositionalLogicPane.getChildren().size() % 4 == 0) { rowCountArray.add(""); }
        });
        ArrayList<String> rowCountArray = new ArrayList<>();
        this.propositionalLogicSymbols.forEach((buttonId, buttonText) -> {
            Button curButton = new Button(buttonText);
            curButton.setId("propositionalLogicSymbolButtons");
            GridPane.setRowIndex(curButton,rowCountArray.size());
            GridPane.setColumnIndex(curButton,this.propositionalLogicPane.getChildren().size() % 4);
            this.propositionalLogicPane.getChildren().add(curButton);
            if (this.propositionalLogicPane.getChildren().size() % 4 == 0) { rowCountArray.add(""); }
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
            });
        });*/
        this.inputButtonVBox.getChildren().addAll(this.propositionalLogicPane, this.predicateLogicPane);
        this.inputButtonVBox.setId("test2");
        this.parentPane.getChildren().addAll(this.inputButtonVBox);
    }

    public Pane getParentPane() { return this.parentPane; }

}
