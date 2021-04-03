package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationView {
    Controller controller;
    BorderPane parentPane = new BorderPane();

    public ApplicationView(Controller _controller) {
        this.controller = _controller;
        parentPane.setTop(new MenuBarView(this.controller).getMenuBar());
        parentPane.setLeft(new InputButtonsView(this.controller).getParentPane());
//        parentPane.setCenter(new Pane());
        parentPane.setCenter(new CenterView(this.controller).getParentPane());
        parentPane.setRight(new RightView(this.controller).getParentPane());
        parentPane.setBottom(new FormulaInputView(this.controller).getParentPane());
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
