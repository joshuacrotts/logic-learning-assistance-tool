package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.localstorage.uidescription.UIDescriptionAdaptor;
import com.llat.models.localstorage.uidescription.UIDescriptionObject;
import com.llat.views.interpreters.InputButtonInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InputButtonsView {

    private final Controller controller;
    private final Stage stage;
    private final UIDescriptionObject obj = (UIDescriptionObject) new UIDescriptionAdaptor().getData();
    private final VBox inputButtonVBox = new VBox();
    private final Region abovePropositionalLogicLabel = new Region();
    private final Label propositionalLogicLabel = new Label("Propositional Logic");
    private final GridPane propositionalLogicPane = new GridPane();
    private final SymbolButton conjunctionButton = new SymbolButton(this.obj.getConjunction());
    private final SymbolButton disjunctionButton = new SymbolButton(this.obj.getDisjunction());
    private final SymbolButton negationButton = new SymbolButton(this.obj.getNegation());
    private final SymbolButton exclusiveDisjunctionButton = new SymbolButton(this.obj.getExclusiveDisjunction());
    private final SymbolButton equivalenceButton = new SymbolButton(this.obj.getBiconditional());
    private final SymbolButton implicationButton = new SymbolButton(this.obj.getImplication());
    private final SymbolButton turnstileButton = new SymbolButton(this.obj.getTurnstile());
    private final SymbolButton doubleTurnstileButton = new SymbolButton(this.obj.getDoubleTurnstile());
    private final ArrayList<SymbolButton> propositionalLogicButtons = new ArrayList<SymbolButton>() {{
        add(conjunctionButton);
        add(disjunctionButton);
        add(negationButton);
        add(exclusiveDisjunctionButton);
        add(equivalenceButton);
        add(implicationButton);
        add(turnstileButton);
        add(doubleTurnstileButton);
    }};
    private final Region belowPropositionalLogicPane = new Region();
    private final Label predicateLogicLabel = new Label("Predicate Logic");
    private final GridPane predicateLogicPane = new GridPane();
    private final SymbolButton existentialQuantifierButton = new SymbolButton(this.obj.getExistential());
    private final SymbolButton universalQuantifierButton = new SymbolButton(this.obj.getUniversal());
    private final ArrayList<SymbolButton> predicateLogicButtons = new ArrayList<SymbolButton>() {{
        add(existentialQuantifierButton);
        add(universalQuantifierButton);
    }};
    private final InputButtonInterpreter inputButtonInterpreter;

    public InputButtonsView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();

        // Setting VBox inputButtonVBox properties.
        this.inputButtonVBox.setId("inputButtonVBox");
        this.inputButtonVBox.setSpacing(0);
        this.inputButtonVBox.setAlignment(Pos.TOP_CENTER);
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.inputButtonVBox.setMinWidth(newVal.doubleValue() * .20);
            this.inputButtonVBox.setMaxWidth(newVal.doubleValue() * .20);
        });

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
        this.inputButtonVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.propositionalLogicPane.setMaxSize(newVal.doubleValue() / 2, newVal.doubleValue() / 4);
        });

        this.propositionalLogicPane.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
        this.propositionalLogicPane.getRowConstraints().addAll(new RowConstraints(), new RowConstraints());
        this.propositionalLogicPane.getColumnConstraints().forEach((constraint) -> {
            constraint.setPercentWidth(25);
        });

        this.propositionalLogicPane.getRowConstraints().forEach((constraint) -> {
            constraint.setPercentHeight(50);
        });

        this.propositionalLogicPane.setAlignment(Pos.TOP_CENTER);

        // Setting Buttons propositionalLogicButtons properties.
        int rowCount = 0;
        for (SymbolButton curButton : this.propositionalLogicButtons) {
            curButton.setId("propositionalLogicButton");
            curButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            curButton.setTooltip(new Tooltip(curButton.getDefaultSymbol().getTooltip()));
            GridPane.setRowIndex(curButton, rowCount / 4);
            GridPane.setColumnIndex(curButton, rowCount % 4);
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
        this.inputButtonVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.predicateLogicPane.setMaxSize(newVal.doubleValue() / 2, newVal.doubleValue() / 4);
        });

        this.predicateLogicPane.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints());
        this.predicateLogicPane.getRowConstraints().addAll(new RowConstraints());
        this.predicateLogicPane.getColumnConstraints().forEach((constraint) -> {
            constraint.setPercentWidth(25);
        });

        this.predicateLogicPane.getRowConstraints().forEach((constraint) -> {
            constraint.setPercentHeight(50);
        });

        this.predicateLogicPane.setAlignment(Pos.TOP_CENTER);

        // Setting Label predicateLogicLabel settings.
        this.predicateLogicLabel.setId("predicateLogicLabel");

        // Setting Buttons predicateLogicButtons properties.
        rowCount = 0;
        for (SymbolButton curButton : this.predicateLogicButtons) {
            curButton.setId("predicateLogicButton");
            curButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            curButton.setTooltip(new Tooltip(curButton.getDefaultSymbol().getTooltip()));
            GridPane.setRowIndex(curButton, rowCount / 4);
            GridPane.setColumnIndex(curButton, rowCount % 4);
            this.predicateLogicPane.getChildren().add(curButton);
            rowCount++;
        }

        // Adding children nodes to their parents nodes.
        this.inputButtonVBox.getChildren().addAll(this.abovePropositionalLogicLabel, this.propositionalLogicLabel,
                this.propositionalLogicPane, this.belowPropositionalLogicPane, this.predicateLogicLabel, this.predicateLogicPane);

        // Creating interpreter to handle events and actions.
        this.inputButtonInterpreter = new InputButtonInterpreter(this.controller, this);
    }

    public ArrayList<SymbolButton> getPropositionalLogicButtons() {
        return this.propositionalLogicButtons;
    }

    public ArrayList<SymbolButton> getPredicateLogicButtons() {
        return this.predicateLogicButtons;
    }

    public Pane getParentPane() {
        return this.inputButtonVBox;
    }
}
