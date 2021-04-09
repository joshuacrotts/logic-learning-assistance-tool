package com.llat.database;

import com.llat.controller.Controller;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.events.LoginStatusEvent;
import com.llat.views.events.RegisterEvent;
import com.llat.views.events.LoginEvent;
import com.llat.views.events.RegistrationStatusEvent;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInterpeter implements Listener {
    Controller controller;
    DatabaseAdapter databaseAdapter;
    int USERID = 0;
    List<String> test = new ArrayList<>();
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
//            USERID = this.controller.getUser().getUserId();
            /*this.databaseAdapter.InsertQuery(this.);*/
            System.out.println(test);
        }

        if (_event instanceof RegisterEvent) {

            String userName = ((RegisterEvent) _event).getUser().getUserName();
            String firstName = ((RegisterEvent) _event).getUser().getFname();
            String lastName = ((RegisterEvent) _event).getUser().getLname();
            String pass = ((RegisterEvent) _event).getUser().getPword();
            int user = this.databaseAdapter.Register(userName, pass, firstName, lastName);
            EventBus.throwEvent(new RegistrationStatusEvent(user));

        }

/*        if (_event instanceof LoginEvent) {
            String userName = ((LoginEvent) _event).getUser().getUserName();
            String pass = ((LoginEvent) _event).getUser().getPword();
            UserObject User = this.databaseAdapter.Login(userName, pass);
            if (!(User == null)) {
                System.out.println("User Logged In");
                this.controller.setUser(User);
                this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
            } else {
                System.out.println("pass or email is wrong");

            }
        }*/
    }
}
