package com.llat.views;

import com.llat.models.symbols.Symbol;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SymbolDescriptionView {
    Symbol symbol;
    VBox symbolDetailsVBox = new VBox();
    Label symbolNameLabel = new Label("Symbol Name");
    Text symbolNameText;
    Label formalNameLabel = new Label("Formal Name");
    Text formalNameText;
    Label alternativeSymbolsLabel = new Label("Alternative Symbols");
    Text alternativeSymbolsText;
    Label exampleLabel = new Label("Examples");
    Text exampleText;
    TextArea explanationText;
    public SymbolDescriptionView (Symbol _symbol) {
        this.symbol = _symbol;
        // Setting VBox symbolDetailsVBox settings.
        this.symbolDetailsVBox.setId("symbolDetailsVBox");
        this.symbolDetailsVBox.setSpacing(0);
        this.symbolDetailsVBox.setAlignment(Pos.TOP_CENTER);
        // Setting Text readAsText settings.
        this.symbolNameText = new Text(symbol.getReadAs());
        this.symbolNameText.setId("rulesAxiomsText");
        // Setting Text toolTipText settings.
        this.formalNameText = new Text(symbol.getTooltip());
        this.formalNameText.setId("rulesAxiomsText");
        // Setting Text allSymbolsText settings.
        this.alternativeSymbolsText = new Text(symbol.getSymbol().getAllSymbols().toString());
        this.alternativeSymbolsText.setId("rulesAxiomsText");
        // Setting Text exampleText settings.
        this.exampleText = new Text(symbol.getAxioms().getExample());
        this.exampleText.setId("rulesAxiomsText");
        // Setting Text exampleText settings.
        this.explanationText = new TextArea(symbol.getAxioms().getExplanation());
        this.explanationText.setWrapText(true);
        this.explanationText.setEditable(false);
        this.explanationText.setDisable(true);
        this.explanationText.setId("rulesAxiomsText");
        this.symbolDetailsVBox.widthProperty().addListener((obs, oldVal, newVal) -> this.explanationText.setMaxWidth(newVal.doubleValue() * .80));
        // Adding children nodes to their parents nodes.
        this.symbolDetailsVBox.getChildren().addAll(this.symbolNameLabel, this.symbolNameText, this.formalNameLabel, this.formalNameText, this.alternativeSymbolsLabel, this.alternativeSymbolsText, this.exampleLabel, this.exampleText, this.explanationText);
    }
    public VBox getParentPane () { return this.symbolDetailsVBox; }
}
