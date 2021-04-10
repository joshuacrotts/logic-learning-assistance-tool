package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationView {

    private Controller controller;
    private BorderPane parentPane = new BorderPane();

    public ApplicationView(Controller _controller) {
        this.controller = _controller;
        this.parentPane.setCenter(new CenterView(this.controller).getParentPane());
        this.parentPane.setTop(new MenuBarView(this.controller).getMenuBar());
        this.parentPane.setLeft(new InputButtonsView(this.controller).getParentPane());
        this.parentPane.setRight(new RightView(this.controller).getParentPane());
        this.parentPane.setBottom(new FormulaInputView(this.controller).getParentPane());
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
