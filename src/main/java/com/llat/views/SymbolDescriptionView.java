package com.llat.views;

import com.llat.models.localstorage.settings.language.LanguageObject;
import com.llat.models.symbols.Symbol;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SymbolDescriptionView {

    /* Global JavaFX elements for this view. */
    private final Symbol symbol;
    private final VBox symbolDetailsVBox = new VBox();

    /* Label, button, and view for the symbol name that we're going to use. */
    private final Label symbolNameLabel = new Label("Symbol Name");
    private final Button symbolNameText;
    private final Region belowSymbolNameText = new Region();

    /* Label, button, and region for the formal name of the symbol. */
    private final Label formalNameLabel = new Label("Formal Name");
    private final Button formalNameText;
    private final Region belowFormalNameText = new Region();

    /* Label, button, and region for the alternative symbols of our symbol. */
    private final Label alternativeSymbolsLabel = new Label("Alternative Symbols");
    private final Button alternativeSymbolsText;
    private final Region belowAlternativeSymbolsText = new Region();

    /* Label, button, and region for the explanation that we're going to use. */
    private final Label explanationLabel = new Label("Explanation");
    private final Button explanationText;
    private final Region belowExplanationText = new Region();

    /* Label, button, and region for the example that we're going to use. */
    private final Label exampleLabel = new Label("Examples");
    private final Button exampleText;

    public SymbolDescriptionView(Symbol _symbol) {
        this.symbol = _symbol;

        // Settings labels id (linked via CSS).
        this.symbolNameLabel.setId("symbolNameLabel");
        this.formalNameLabel.setId("formalNameLabel");
        this.explanationLabel.setId("explanationLabel");
        this.exampleLabel.setId("exampleLabel");
        this.alternativeSymbolsLabel.setId("alternativeSymbolsLabel");

        // Setting VBox symbolDetailsVBox settings.
        this.symbolDetailsVBox.setId("symbolDetailsVBox");
        this.symbolDetailsVBox.setSpacing(0);
        this.symbolDetailsVBox.setAlignment(Pos.TOP_CENTER);

        // Setting Text readAsText settings.
        this.symbolNameText = new Button(symbol.getReadAs());
        this.symbolNameText.setWrapText(true);
        this.symbolNameText.setId("rulesAxiomsText");
        this.symbolNameText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.symbolNameText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowSymbolNameText settings.
        this.belowSymbolNameText.setMinHeight(25);

        // Setting Text toolTipText settings.
        this.formalNameText = new Button(symbol.getTooltip());
        this.formalNameText.setWrapText(true);
        this.formalNameText.setId("rulesAxiomsText");
        this.formalNameText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.formalNameText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowFormulaNameText settings.
        this.belowFormalNameText.setMinHeight(25);

        // Setting Text allSymbolsText settings.
        this.alternativeSymbolsText = new Button(symbol.getSymbol().getAllSymbols().toString());
        this.alternativeSymbolsText.setWrapText(true);
        this.alternativeSymbolsText.setId("rulesAxiomsText");
        this.alternativeSymbolsText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.alternativeSymbolsText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowAlternativeSymbols settings.
        this.belowAlternativeSymbolsText.setMinHeight(25);

        // Setting Text exampleText settings.
        this.explanationText = new Button(symbol.getAxioms().getExplanation());
        this.explanationText.setNodeOrientation(LanguageObject.isUsingRightToLeftLanguage() ?
                NodeOrientation.RIGHT_TO_LEFT :
                NodeOrientation.LEFT_TO_RIGHT);
        this.explanationText.setTextAlignment(TextAlignment.JUSTIFY);
        this.explanationText.setWrapText(true);
        this.explanationText.setId("rulesAxiomsText");
        this.explanationText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.explanationText.setMaxWidth(newVal.doubleValue() * .80));

        // Setting Region belowExplanationText settings.
        this.belowExplanationText.setMinHeight(25);

        // Setting Text exampleText settings.
        this.exampleText = new Button(symbol.getAxioms().getExample().get(0));
        this.exampleText.setTextAlignment(TextAlignment.JUSTIFY);
        this.exampleText.setNodeOrientation(LanguageObject.isUsingRightToLeftLanguage() ?
                NodeOrientation.RIGHT_TO_LEFT :
                NodeOrientation.LEFT_TO_RIGHT);
        this.exampleText.setWrapText(true);
        this.exampleText.setId("rulesAxiomsText");
        this.exampleText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.exampleText.setMaxWidth(newVal.doubleValue() * .80));

        // Adding children nodes to their parents nodes.
        this.symbolDetailsVBox.getChildren().addAll(this.symbolNameLabel, this.symbolNameText, this.belowSymbolNameText,
                this.formalNameLabel, this.formalNameText, this.belowFormalNameText, this.alternativeSymbolsLabel,
                this.alternativeSymbolsText, this.belowAlternativeSymbolsText, this.explanationLabel,
                this.explanationText, this.belowExplanationText, this.exampleLabel, this.exampleText);
    }

    public VBox getParentPane() {
        return this.symbolDetailsVBox;
    }

}
