package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.localstorage.uidescription.UIObject;
import com.llat.views.interpreters.InputButtonInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class InputButtonsView {

    private final Controller controller;
    //    private final UIDescriptionObject obj = (UIDescriptionObject) new UIDescriptionAdaptor().getData();
    private final UIObject obj;
    private final VBox inputButtonVBox = new VBox();
    private final Region abovePropositionalLogicLabel = new Region();
    private final GridPane propositionalLogicPane = new GridPane();
    private final SymbolButton conjunctionButton;
    private final SymbolButton disjunctionButton;
    private final SymbolButton negationButton;
    private final SymbolButton exclusiveDisjunctionButton;
    private final SymbolButton equivalenceButton;
    private final SymbolButton implicationButton;
    private final SymbolButton turnstileButton;
    private final SymbolButton doubleTurnstileButton;
    private final ArrayList<SymbolButton> propositionalLogicButtons;
    private final Region belowPropositionalLogicPane = new Region();
    private final GridPane predicateLogicPane = new GridPane();
    private final SymbolButton existentialQuantifierButton;
    private final SymbolButton universalQuantifierButton;
    private final ArrayList<SymbolButton> predicateLogicButtons;
    private final InputButtonInterpreter inputButtonInterpreter;
    private Label propositionalLogicLabel = new Label("Propositional Logic");
    private Label predicateLogicLabel = new Label("Predicate Logic");

    public InputButtonsView(Controller _controller) {
        this.controller = _controller;
        this.obj = this.controller.getUiObject();

        this.propositionalLogicLabel = new Label(this.obj.getMainView().getMainViewLabels().getPropositionalLabel());
        this.predicateLogicLabel = new Label(this.obj.getMainView().getMainViewLabels().getPredicateLabel());

        this.conjunctionButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getConjunction());
        this.disjunctionButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getDisjunction());
        this.negationButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getNegation());
        this.exclusiveDisjunctionButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getExclusiveDisjunction());
        this.equivalenceButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getBiconditional());
        this.implicationButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getImplication());
        this.turnstileButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getTurnstile());
        this.doubleTurnstileButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPropositional().getDoubleTurnstile());
        this.propositionalLogicButtons = new ArrayList<SymbolButton>() {{
            this.add(InputButtonsView.this.conjunctionButton);
            this.add(InputButtonsView.this.disjunctionButton);
            this.add(InputButtonsView.this.negationButton);
            this.add(InputButtonsView.this.exclusiveDisjunctionButton);
            this.add(InputButtonsView.this.equivalenceButton);
            this.add(InputButtonsView.this.implicationButton);
            this.add(InputButtonsView.this.turnstileButton);
            this.add(InputButtonsView.this.doubleTurnstileButton);
        }};
        this.existentialQuantifierButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPredicate().getExistential());
        this.universalQuantifierButton = new SymbolButton(this.obj.getMainView().getLogicSymbols().getPredicate().getUniversal());
        this.predicateLogicButtons = new ArrayList<SymbolButton>() {{
            this.add(InputButtonsView.this.existentialQuantifierButton);
            this.add(InputButtonsView.this.universalQuantifierButton);
        }};

        // Setting VBox inputButtonVBox properties.
        this.inputButtonVBox.setId("inputButtonVBox");
        this.inputButtonVBox.setSpacing(0);
        this.inputButtonVBox.setAlignment(Pos.TOP_CENTER);

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
            curButton.setContextMenu(new SymbolContextMenu(curButton));
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
