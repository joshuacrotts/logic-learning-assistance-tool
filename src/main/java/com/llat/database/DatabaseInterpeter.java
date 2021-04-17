package com.llat.database;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.RegisterEvent;
import com.llat.views.events.RegistrationStatusEvent;

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
                    String Input = tree.getStringRep();
                });

/*                String text = this.test.get(0);
                this.databaseAdapter.InsertQuery(this.USERID, text);*/
            }

            if (_event instanceof RegisterEvent) {
                String userName = ((RegisterEvent) _event).getUser().getUserName();
                String firstName = ((RegisterEvent) _event).getUser().getFname();
                String lastName = ((RegisterEvent) _event).getUser().getLname();
                String pass = ((RegisterEvent) _event).getUser().getPword();
                int user = this.databaseAdapter.Register(userName, pass, firstName, lastName);
                EventBus.throwEvent(new RegistrationStatusEvent(user));
            }
        }
    }
}
