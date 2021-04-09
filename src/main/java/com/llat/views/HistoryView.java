package com.llat.views;

import com.llat.controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HistoryView {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final Stage stage;

    /**
     *
     */
    private final VBox parentPane = new VBox();

    /**
     *
     */
    private TableView<String> table = new TableView();

    public HistoryView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.table.setMinHeight(newVal.doubleValue());
            this.table.setMinHeight(newVal.doubleValue());
        });
        if (this.controller.getUser() != null && this.controller.getUser().getHistory() != null) {
            List<String> history = this.controller.getUser().getHistory();
            ObservableList<String> names = FXCollections.observableArrayList(history);
            TableView<String> tv = new TableView(FXCollections.observableArrayList(new ArrayList<String>(history)));
            TableColumn<String, String> formulaColumn = new TableColumn<>("Formula");
            this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                formulaColumn.setMinWidth(newVal.doubleValue() * .18);
            });
            formulaColumn.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue()));
            tv.getColumns().add(formulaColumn);
            this.table = tv;
        }

        this.parentPane.getChildren().add(this.table);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
