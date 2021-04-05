package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


public class AlgorithmsView {
    Controller controller;
    Pane parentPane = new Pane();
    HBox hBox = new HBox();
    Region region1 = new Region();

    SplitMenuButton propositionalLogicMenu = new SplitMenuButton();
    SplitMenuButton predicateLogicMenu = new SplitMenuButton();



    public AlgorithmsView(Controller _controller) {
        this.controller = _controller;
        this.propositionalLogicMenu.setText("Propositional Logic");
        this.predicateLogicMenu.setText("Predicate Logic");

        this.hBox.getChildren().addAll(propositionalLogicMenu, region1, predicateLogicMenu);
        this.parentPane.getChildren().addAll(this.hBox);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
