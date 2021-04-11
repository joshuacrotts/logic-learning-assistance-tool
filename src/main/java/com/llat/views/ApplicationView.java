package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.menu.LeftView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationView {
    Controller controller;
    BorderPane parentPane = new BorderPane();

    public ApplicationView(Controller _controller) {
        this.controller = _controller;
        parentPane.setCenter(new CenterView(this.controller).getParentPane());
        parentPane.setTop(new MenuBarView(this.controller).getMenuBar());
        parentPane.setLeft(new LeftView(this.controller).getParentPane());
        parentPane.setRight(new RightView(this.controller).getParentPane());
        parentPane.setBottom(new FormulaInputView(this.controller).getParentPane());
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
