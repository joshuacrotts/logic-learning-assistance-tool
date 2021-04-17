package com.llat.database;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.LoginSuccessEvent;
import com.llat.views.events.RegistrationStatusEvent;
import com.llat.views.events.UpdateHistoryEvent;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInterpeter implements Listener {
    Controller controller;
    DatabaseAdapter databaseAdapter;
    List<String> test = new ArrayList<>();
    int USERID = 0;
    private UserObject user;

    public DatabaseInterpeter(DatabaseAdapter databaseAdapter, Controller _controller) {
        this.databaseAdapter = databaseAdapter;
        this.controller = _controller;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            if (this.controller.getUser() != null) {
                this.USERID = this.controller.getUser().getUserId();
                this.test = new ArrayList<>();
                ((SolvedFormulaEvent) _event).getWffTree().forEach((tree) -> {
                    this.test.add(tree.getStringRep());
                });

               String text = this.test.get(0);
               this.databaseAdapter.InsertQuery(this.USERID, text);
                List<String> history = this.databaseAdapter.UpdateHistory(this.USERID);
                this.controller.getUser().setHistory(history);
                EventBus.throwEvent(new UpdateHistoryEvent());
            }
        }
    }
}
