package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        dummyData();

        this.parentPane.getChildren().addAll(this.table);
    }

    public Node getParentPane() {
        return parentPane;
    }

    private void dummyData() {
        TableColumn<String, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<String, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
    }
}
