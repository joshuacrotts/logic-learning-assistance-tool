package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationView {
    Controller controller;
    BorderPane parentPane = new BorderPane();

    public ApplicationView(Controller _controller) {
        this.controller = _controller;
        parentPane.setCenter(new ParseTreeView(this.controller).getParentPane());
        parentPane.setTop(new MenuBarView(this.controller).getMenuBar());
        parentPane.setLeft(new InputButtonsView(this.controller).getParentPane());
        parentPane.setRight(new RulesAxiomsView(this.controller).getParentPane());
        parentPane.setBottom(new FormulaInputView(this.controller).getParentPane());
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
