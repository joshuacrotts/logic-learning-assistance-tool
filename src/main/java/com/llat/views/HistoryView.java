package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.HistoryViewInterpreter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    private final VBox parentPane = new VBox();

    /**
     *
     */
    private TableView<String> table = new TableView();
    private final HistoryViewInterpreter historyViewInterpreter;
    public HistoryView(Controller _controller) {
        this.controller = _controller;
        this.parentPane.setStyle("-fx-background-color: yellow");
        VBox.setVgrow(this.table, Priority.ALWAYS);
        this.parentPane.getChildren().add(this.table);
        this.historyViewInterpreter = new HistoryViewInterpreter(this.controller, this);
        historyViewInterpreter.updateHistory();
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public Controller getController() {
        return controller;
    }

    public void setTable(TableView<String> _table) {
        this.parentPane.getChildren().remove(this.table);
        this.parentPane.getChildren().add(_table);
        this.table = _table;
        VBox.setVgrow(this.table, Priority.ALWAYS);
    }

}
