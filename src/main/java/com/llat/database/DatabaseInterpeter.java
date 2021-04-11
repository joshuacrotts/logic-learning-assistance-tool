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
    int UserID = 0;

    public DatabaseInterpeter(DatabaseAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            this.test = new ArrayList<>();
            ((SolvedFormulaEvent) _event).getWffTree().forEach((tree) -> {
                this.test.add(tree.getStringRep());
            });
            System.out.println(this.test);
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
