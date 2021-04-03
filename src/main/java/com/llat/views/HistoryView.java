package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HistoryView {
    Controller controller;
    AnchorPane parentPane = new AnchorPane();

    public HistoryView(Controller _controller) {
        this.controller = _controller;
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
