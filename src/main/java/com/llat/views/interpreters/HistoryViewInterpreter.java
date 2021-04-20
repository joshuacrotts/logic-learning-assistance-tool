package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.HistoryView;
import com.llat.views.events.LoginSuccessEvent;
import com.llat.views.events.UpdateHistoryEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;



public class HistoryViewInterpreter implements Listener {
    private final Controller controller;
    private final HistoryView historyView;

    public HistoryViewInterpreter(Controller _controller, HistoryView _historyView) {
        this.controller = _controller;
        this.historyView = _historyView;
        EventBus.addListener(this);
    }



    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof LoginSuccessEvent)  {
                updateHistory();
            }


        if (_event instanceof UpdateHistoryEvent)  {
                updateHistory();
            }
        }


    public void updateHistory() {
        if (this.controller.getUser() != null && this.controller.getUser().getHistory() != null) {
            List<String> history = this.controller.getUser().getHistory();
            ObservableList<String> names = FXCollections.observableArrayList(history);
            TableView<String> tv = new TableView(FXCollections.observableArrayList(new ArrayList<String>(history)));
            TableColumn<String, String> formulaColumn = new TableColumn<>("Formula");
            formulaColumn.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue()));
            tv.getColumns().add(formulaColumn);
            this.historyView.setTable(tv);
        }
    }

}
