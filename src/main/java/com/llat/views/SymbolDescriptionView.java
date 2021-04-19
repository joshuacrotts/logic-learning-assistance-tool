package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.localstorage.settings.language.LanguageObject;
import com.llat.models.symbols.Symbol;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class SymbolDescriptionView {

    /* Global JavaFX elements for this view. */
    private final Symbol symbol;
    private final VBox symbolDetailsVBox = new VBox();

    /* Label, button, and view for the symbol name that we're going to use. */
    private final Label symbolNameLabel;
    private final Button symbolNameText;
    private final Region belowSymbolNameText = new Region();

    /* Label, button, and region for the formal name of the symbol. */
    private final Label formalNameLabel;
    private final Button formalNameText;
    private final Region belowFormalNameText = new Region();

    /* Label, button, and region for the alternative symbols of our symbol. */
    private final Label alternativeSymbolsLabel;
    private final Button alternativeSymbolsText;
    private final Region belowAlternativeSymbolsText = new Region();
    private final Button explanationText;
    private final Region belowExplanationText = new Region();
    /* Label, button, and region for the example that we're going to use. */
    private final Label exampleLabel;
    private final ArrayList<Button> exampleText = new ArrayList<>();
    private final Region belowExampleText = new Region();
    private final Controller controller;
    /* Label, button, and region for the explanation that we're going to use. */
    private Label explanationLabel = new Label("Explanation");

    public SymbolDescriptionView(Controller _controller, Symbol _symbol) {
        this.symbol = _symbol;
        this.controller = _controller;

        this.symbolNameLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getSymbolNameLabel());
        this.formalNameLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getFormalNameLabel());
        this.alternativeSymbolsLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getAlternativeSymbolsLabel());
        this.explanationLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getExplanationLabel());
        this.exampleLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getExamplesLabel());


        // Settings labels id (linked via CSS).
        this.symbolNameLabel.setId("symbolNameLabel");
        this.formalNameLabel.setId("formalNameLabel");
        this.explanationLabel.setId("explanationLabel");
        this.exampleLabel.setId("exampleLabel");
        this.alternativeSymbolsLabel.setId("alternativeSymbolsLabel");

        // Setting VBox symbolDetailsVBox properties.
        this.symbolDetailsVBox.setId("symbolDetailsVBox");
        this.symbolDetailsVBox.setSpacing(0);
        this.symbolDetailsVBox.setAlignment(Pos.TOP_CENTER);

        // Setting Text readAsText properties.
        this.symbolNameText = new Button(this.symbol.getReadAs());
        this.symbolNameText.setWrapText(true);
        this.symbolNameText.setId("rulesAxiomsText");
        this.symbolNameText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.symbolNameText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowSymbolNameText properties.
        this.belowSymbolNameText.setMinHeight(25);

        // Setting Text toolTipText properties.
        this.formalNameText = new Button(this.symbol.getTooltip());
        this.formalNameText.setWrapText(true);
        this.formalNameText.setId("rulesAxiomsText");
        this.formalNameText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.formalNameText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowFormulaNameText properties.
        this.belowFormalNameText.setMinHeight(25);

        // Setting Text allSymbolsText properties.
        this.alternativeSymbolsText = new Button(this.symbol.getSymbol().getAllSymbols().toString());
        this.alternativeSymbolsText.setWrapText(true);
        this.alternativeSymbolsText.setId("rulesAxiomsText");
        this.alternativeSymbolsText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.alternativeSymbolsText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowAlternativeSymbols properties.
        this.belowAlternativeSymbolsText.setMinHeight(25);

        // Setting Text exampleText properties.
        this.explanationText = new Button(this.symbol.getAxioms().getExplanation());
        this.explanationText.setNodeOrientation(LanguageObject.isUsingRightToLeftLanguage() ?
                NodeOrientation.RIGHT_TO_LEFT :
                NodeOrientation.LEFT_TO_RIGHT);
        this.explanationText.setTextAlignment(TextAlignment.JUSTIFY);
        this.explanationText.setWrapText(true);
        this.explanationText.setId("rulesAxiomsText");
        this.explanationText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.explanationText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowExplanationText properties.
        this.belowExplanationText.setMinHeight(25);

        // Setting Text exampleText properties.
        this.symbol.getAxioms().getExample().forEach((example) -> {
            Button curExample = new Button(example);
            curExample.setTextAlignment(TextAlignment.JUSTIFY);
            curExample.setNodeOrientation(LanguageObject.isUsingRightToLeftLanguage() ?
                    NodeOrientation.RIGHT_TO_LEFT :
                    NodeOrientation.LEFT_TO_RIGHT);
            curExample.setWrapText(true);
            curExample.setId("rulesAxiomsText");
            curExample.setMaxHeight(Double.MAX_VALUE);
            this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> curExample.setMaxWidth(newVal.doubleValue() * .80));
            this.exampleText.add(curExample);
        });

        // Setting Region belowExampleText properties.
        this.belowExampleText.setMinHeight(25);
        // Adding children nodes to their parents nodes.
        this.symbolDetailsVBox.getChildren().addAll(this.symbolNameLabel, this.symbolNameText, this.belowSymbolNameText,
                this.formalNameLabel, this.formalNameText, this.belowFormalNameText,
                this.alternativeSymbolsLabel, this.alternativeSymbolsText, this.belowAlternativeSymbolsText,
                this.explanationLabel, this.explanationText, this.belowExplanationText,
                this.exampleLabel);
        this.exampleText.forEach((exampleText) -> {
            this.symbolDetailsVBox.getChildren().add(exampleText);
        });
        this.symbolDetailsVBox.getChildren().add(this.belowExampleText);
    }

    public VBox getParentPane() {
        return this.symbolDetailsVBox;
    }
}
