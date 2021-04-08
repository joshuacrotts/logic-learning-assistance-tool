package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.DatabaseAdapter;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.RegisterView;
import com.llat.views.events.RegisterEvent;
import com.llat.views.events.RegistrationStatusEvent;

public class RegisterInterpreter implements Listener {
    Controller controller;
    RegisterView registerView;

    public RegisterInterpreter(Controller _controller, RegisterView _registerView) {
        this.controller = _controller;
        this.registerView = _registerView;
        registerView.getRegisterButton().setOnAction((node) -> {
            RegisterEvent re = new RegisterEvent(this.registerView.getUserNameField().getText(), this.registerView.getFirstNameField().getText(), this.registerView.getLastNameField().getText()
                    , this.registerView.getPasswordField().getText());
            EventBus.throwEvent(re);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof RegistrationStatusEvent){
            switch (((RegistrationStatusEvent) _event).getStatus()) {
                case DatabaseAdapter.REGISTERED_SUCCESSFULLY:
                    System.out.println("User Registered");
                    this.controller.changeViewTo(ViewManager.LOGIN);
                    break;
                case DatabaseAdapter.REGISTERED_EMPTY_INPUT:
                    System.out.println("You need to fill all the required fields");
                    break;
                case DatabaseAdapter.REGISTERED_DUP_USER:
                    System.out.println("User name is already exist");
                    break;
            }

        }

    }
}
