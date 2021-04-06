package com.llat.views;

import com.llat.controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HistoryView {
    Controller controller;
    Stage stage;
    AnchorPane parentPane = new AnchorPane();
    private TableView<String> table = new TableView();


    public HistoryView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();

        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            table.setMinWidth(newVal.doubleValue() * .2);
            table.setMaxWidth(newVal.doubleValue()* .2);
        });
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            table.setMinHeight(newVal.doubleValue() * .82);
            table.setMaxHeight(newVal.doubleValue()* .82);
        });
        if (this.controller.getUser() != null){
            List<String> history =  this.controller.getUser().getHistory();
            ObservableList<String> names = FXCollections.observableArrayList(history);
            TableView<String> tv = new TableView(FXCollections.observableArrayList(new ArrayList<String>(history)));
            TableColumn<String, String> formulaColumn = new TableColumn<>("Formula");
            this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                formulaColumn.setMinWidth(newVal.doubleValue()* .18);
            });
            formulaColumn.setCellValueFactory((p) -> {
                return new ReadOnlyStringWrapper(p.getValue());
            });
            tv.getColumns().add(formulaColumn);
            table = tv;
        }
        parentPane.getChildren().add(table);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
