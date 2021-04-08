package com.llat.views;

import com.llat.models.symbols.Symbol;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SymbolDescriptionView {

    private Symbol symbol;
    private VBox symbolDetailsVBox = new VBox();
    private Label symbolNameLabel = new Label("Symbol Name");
    private Button symbolNameText;
    private Region belowSymbolNameText = new Region();
    private Label formalNameLabel = new Label("Formal Name");
    private Button formalNameText;
    private Region belowFormalNameText = new Region();
    private Label alternativeSymbolsLabel = new Label("Alternative Symbols");
    private Button alternativeSymbolsText;
    private Region belowAlternativeSymbolsText = new Region();
    private Label exampleLabel = new Label("Examples");
    private Button exampleText;
    private Button explanationText;
    private Region belowExplanationText = new Region();

    public SymbolDescriptionView(Symbol _symbol) {
        this.symbol = _symbol;
        // Settings labels id
        this.symbolNameLabel.setId("symbolNameLabel");
        this.formalNameLabel.setId("formalNameLabel");
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
        this.exampleText = new Button(symbol.getAxioms().getExample().get(0));
        this.exampleText.setWrapText(true);
        this.exampleText.setId("rulesAxiomsText");
        this.exampleText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.exampleText.setMaxWidth(newVal.doubleValue() * .80));
        // Setting Text exampleText settings.
        this.explanationText = new Button(symbol.getAxioms().getExplanation());
        this.explanationText.setWrapText(true);
        this.explanationText.setId("rulesAxiomsText");
        this.explanationText.setMaxHeight(Double.MAX_VALUE);
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.explanationText.setMaxWidth(newVal.doubleValue() * .80));
        // Setting Region belowExplanationText settings.
        this.belowExplanationText.setMinHeight(25);
        // Adding children nodes to their parents nodes.
        this.symbolDetailsVBox.getChildren().addAll(this.symbolNameLabel, this.symbolNameText, this.belowSymbolNameText, this.formalNameLabel, this.formalNameText, this.belowFormalNameText, this.alternativeSymbolsLabel, this.alternativeSymbolsText, this.belowAlternativeSymbolsText, this.exampleLabel, this.exampleText, this.explanationText, this.belowExplanationText);
    }

    public VBox getParentPane() {
        return this.symbolDetailsVBox;
    }

}
