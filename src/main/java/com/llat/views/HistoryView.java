package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.HistoryViewInterpreter;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    private final HistoryViewInterpreter historyViewInterpreter;

    /**
     *
     */
    private TableView<String> table = new TableView();

    public HistoryView(Controller _controller) {
        this.controller = _controller;
        VBox.setVgrow(this.table, Priority.ALWAYS);
        this.parentPane.getChildren().add(this.table);
        this.historyViewInterpreter = new HistoryViewInterpreter(this.controller, this);
        this.historyViewInterpreter.updateHistory();
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public Controller getController() {
        return this.controller;
    }

    public void setTable(TableView<String> _table) {
        this.parentPane.getChildren().remove(this.table);
        this.parentPane.getChildren().add(_table);
        this.table = _table;
        VBox.setVgrow(this.table, Priority.ALWAYS);
    }
}
