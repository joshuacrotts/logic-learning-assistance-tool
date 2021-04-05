package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TruthTableView {
    private Controller controller;
    private Stage stage;
    private AnchorPane parentPane = new AnchorPane();

    private TableView table = new TableView();
    public TruthTableView(Controller _controller) {

        this.controller = _controller;
        this.stage = _controller.getStage();


        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            table.setMinWidth(newVal.doubleValue() * .6);
            table.setMaxWidth(newVal.doubleValue()* .6);
        });


        this.stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.parentPane.setMinHeight((newVal.doubleValue() * .4) - MenuBarView.menuBarHeight);
            this.parentPane.setMaxHeight((newVal.doubleValue() * .4) - MenuBarView.menuBarHeight);
        });

        this.parentPane.getChildren().addAll(this.table);
    }

    public Node getParentPane() {
        return parentPane;
    }
}
