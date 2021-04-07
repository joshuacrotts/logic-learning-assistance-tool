package com.llat.database;

import com.llat.input.events.SolvedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.RegisterEvent;
import com.llat.views.events.RegistrationStatusEvent;

public class DatabaseInterpeter implements Listener {
    DatabaseAdapter databaseAdapter;
    public DatabaseInterpeter(DatabaseAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent){
            String result = ((SolvedFormulaEvent) _event).getWffTree().getChild(0).getStringRep();
            System.out.println(result);
        }
        if (_event instanceof RegisterEvent){

            String userName = ((RegisterEvent) _event).getUser().getUserName();
            String firstName = ((RegisterEvent) _event).getUser().getFname();
            String lastName = ((RegisterEvent) _event).getUser().getLname();
            String pass = ((RegisterEvent) _event).getUser().getPword();
            int user = this.databaseAdapter.Register(userName, pass, firstName, lastName);
            EventBus.throwEvent(new RegistrationStatusEvent(user));

        }
    }
}
