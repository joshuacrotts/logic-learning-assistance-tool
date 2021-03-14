package com.llat.main.view;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import  com.llat.main.controller.Controller;

public class MainApplicationView {
    private Controller controller;
    private BorderPane parentPane = new BorderPane();
    private SymbolInputView symbolInputview = new SymbolInputView(this.controller);
    private RulesAxiomsView rulesAxiomsView = new RulesAxiomsView(this.controller);
    private FormulaInputView formulaInputView = new FormulaInputView(this.controller);

    public MainApplicationView (Controller _controller) {
        this.controller = _controller;
        this.parentPane.setLeft(this.symbolInputview.getParentPane());
        this.parentPane.setRight(this.rulesAxiomsView.getParentPane());
        this.parentPane.setBottom(this.formulaInputView.getParentPane());
        this.parentPane.setCenter(new AnchorPane());
    }

    public Pane getParentPane() {
        return this.parentPane;
    }
}
