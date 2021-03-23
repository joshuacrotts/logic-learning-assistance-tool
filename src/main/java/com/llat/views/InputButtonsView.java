package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.uidescription.UIDescriptionAdaptor;
import com.llat.models.uidescription.UIDescriptionObject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InputButtonsView {

    Controller controller;
    Stage stage;
    UIDescriptionObject obj = (UIDescriptionObject) (new UIDescriptionAdaptor()).getData().getObject();
    AnchorPane parentPane = new AnchorPane();
    VBox inputButtonVBox = new VBox();
    Region abovePropositionalLogicLabel = new Region();
    Label propositionalLogicLabel = new Label("Propositional Logic");
    GridPane propositionalLogicPane = new GridPane();
    Button conjunctionButton = new Button(this.obj.getConjunction().getSymbol().getApplied());
    Button disjunctionButton = new Button(this.obj.getDisjunction().getSymbol().getApplied());
    Button negationButton = new Button(this.obj.getNegation().getSymbol().getApplied());
    Button exclusiveDisjunctionButton = new Button(this.obj.getExclusiveDisjunction().getSymbol().getApplied());
    Button equivalenceButton = new Button(this.obj.getEquivalence().getSymbol().getApplied());
    Button implicationButton = new Button(this.obj.getImplication().getSymbol().getApplied());
    Button turnstileButton = new Button(this.obj.getTurnstile().getSymbol().getApplied());
    Button doubleTurnstileButton = new Button(this.obj.getDoubleTurnstile().getSymbol().getApplied());
    ArrayList <Button> propositionalLogicButtons = new ArrayList <Button>() {{ add(conjunctionButton); add(disjunctionButton); add(negationButton); add(exclusiveDisjunctionButton);
                                                                             add(equivalenceButton); add(implicationButton); add(turnstileButton); add(doubleTurnstileButton); }};
    Region belowPropositionalLogicPane = new Region();
    Label predicateLogicLabel = new Label("Predicate Logic");
    GridPane predicateLogicPane = new GridPane();
    Button existentialQuantifierButton = new Button(this.obj.getExistential().getSymbol().getApplied());
    Button universalQuantifierButton = new Button(this.obj.getUniversal().getSymbol().getApplied());
    ArrayList <Button> predicateLogicButtons = new ArrayList <Button> () {{ add(existentialQuantifierButton); add(universalQuantifierButton); }};

    public InputButtonsView (Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        // Setting VBox inputButtonVBox properties.
        this.inputButtonVBox.setId("inputButtonVBox");
        this.inputButtonVBox.setSpacing(0);
        this.inputButtonVBox.setAlignment(Pos.TOP_CENTER);
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> { this.inputButtonVBox.setMinWidth(newVal.doubleValue() * .20); });
        this.inputButtonVBox.setLayoutX(0);
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> { this.inputButtonVBox.setLayoutY((newVal.doubleValue() * .50) - this.inputButtonVBox.getMinHeight() / 2); });
        // Setting Region abovePropositionalLogicLabel settings.
        this.inputButtonVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.abovePropositionalLogicLabel.setMinHeight(newVal.doubleValue() * .03);
            this.abovePropositionalLogicLabel.setMaxHeight(newVal.doubleValue() * .03);
        });
        // Setting Label propositionalLogicLabel settings.
        this.propositionalLogicLabel.setId("propositionalLogicLabel");
        // Setting GridPane propositionalLogicPane settings.
        this.propositionalLogicPane.setHgap(4);
        this.propositionalLogicPane.setVgap(4);
        this.inputButtonVBox.widthProperty().addListener((obs, oldVal, newVal) -> { this.propositionalLogicPane.setMaxSize(newVal.doubleValue() / 2, newVal.doubleValue() / 4); });
        this.propositionalLogicPane.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
        this.propositionalLogicPane.getRowConstraints().addAll(new RowConstraints(), new RowConstraints());
        this.propositionalLogicPane.getColumnConstraints().forEach((constraint) -> { constraint.setPercentWidth(25); });
        this.propositionalLogicPane.getRowConstraints().forEach((constraint) -> { constraint.setPercentHeight(50); });
        this.propositionalLogicPane.setAlignment(Pos.TOP_CENTER);
        // Setting Buttons propositionalLogicButtons properties.
        int rowCount = 0;
        for (Button curButton : this.propositionalLogicButtons) {
            curButton.setId("propositionalLogicButton");
            curButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setRowIndex(curButton,rowCount / 4);
            GridPane.setColumnIndex(curButton,rowCount % 4);
            this.propositionalLogicPane.getChildren().add(curButton);
            rowCount++;
        }
        // Setting Region belowPropositionalLogicPane settings.
        this.inputButtonVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.belowPropositionalLogicPane.setMaxHeight(newVal.doubleValue() * .02);
            this.belowPropositionalLogicPane.setMinHeight(newVal.doubleValue() * .02);
        });
        // Setting GridPane predicateLogicPane settings.
        this.predicateLogicPane.setHgap(4);
        this.predicateLogicPane.setVgap(4);
        this.inputButtonVBox.widthProperty().addListener((obs, oldVal, newVal) -> { this.predicateLogicPane.setMaxSize(newVal.doubleValue() / 2, newVal.doubleValue() / 4); });
        this.predicateLogicPane.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints());
        this.predicateLogicPane.getRowConstraints().addAll(new RowConstraints());
        this.predicateLogicPane.getColumnConstraints().forEach((constraint) -> { constraint.setPercentWidth(25); });
        this.predicateLogicPane.getRowConstraints().forEach((constraint) -> { constraint.setPercentHeight(50); });
        this.predicateLogicPane.setAlignment(Pos.TOP_CENTER);
        // Setting Label predicateLogicLabel settings.
        this.predicateLogicLabel.setId("predicateLogicLabel");
        // Setting Buttons predicateLogicButtons properties.
        rowCount = 0;
        for (Button curButton : this.predicateLogicButtons) {
            curButton.setId("predicateLogicButton");
            curButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setRowIndex(curButton,rowCount / 4);
            GridPane.setColumnIndex(curButton,rowCount % 4);
            this.predicateLogicPane.getChildren().add(curButton);
            rowCount++;
            GridPane.setVgrow(curButton, Priority.ALWAYS);
            GridPane.setHgrow(curButton, Priority.ALWAYS);
        }
        // Adding children nodes to their parents nodes.
        this.inputButtonVBox.getChildren().addAll(this.abovePropositionalLogicLabel, this.propositionalLogicLabel, this.propositionalLogicPane, this.belowPropositionalLogicPane, this.predicateLogicLabel, this.predicateLogicPane);
        this.parentPane.getChildren().addAll(this.inputButtonVBox);

    }

    public Pane getParentPane() { return this.inputButtonVBox; }

}
