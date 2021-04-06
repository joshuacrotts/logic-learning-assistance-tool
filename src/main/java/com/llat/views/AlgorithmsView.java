package com.llat.views;

import com.llat.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AlgorithmsView {
    Controller controller;
    Stage stage;
    Pane parentPane = new Pane();
    HBox hBox = new HBox();


    SplitMenuButton propositionalLogicMenu = new SplitMenuButton();
    SplitMenuButton predicateLogicMenu = new SplitMenuButton();



    public AlgorithmsView(Controller _controller) {
        this.controller = _controller;
        this.stage = controller.getStage();

        this.propositionalLogicMenu.setText("Propositional Logic");
        this.predicateLogicMenu.setText("Predicate Logic");
        this.hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(200);

        this.hBox.getChildren().addAll(propositionalLogicMenu, predicateLogicMenu);
        this.parentPane.getChildren().addAll(this.hBox);

        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            hBox.setMinWidth(newVal.doubleValue() * .6);
            hBox.setMaxWidth(newVal.doubleValue()* .6);
        });
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
