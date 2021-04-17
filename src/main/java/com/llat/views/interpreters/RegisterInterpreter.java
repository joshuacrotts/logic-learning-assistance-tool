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

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final RegisterView registerView;

    public RegisterInterpreter(Controller _controller, RegisterView _registerView) {
        this.controller = _controller;
        this.registerView = _registerView;
        this.controller.registerAction(this.registerView.getRegisterButton(),this.registerView.getUserNameField(),this.registerView.getFirstNameField(),this.registerView.getLastNameField(), this.registerView.getPasswordField());
        this.registerView.getReturnButton().setOnAction((event) -> {
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof RegistrationStatusEvent) {
            switch (((RegistrationStatusEvent) _event).getStatus()) {
                case DatabaseAdapter.REGISTERED_SUCCESSFULLY:
                    System.out.println("User Registered");
                    this.controller.changeViewTo(ViewManager.LOGIN);
                    break;
                case DatabaseAdapter.REGISTERED_EMPTY_INPUT:
                    System.out.println("You need to fill all the required fields");
                    break;
                case DatabaseAdapter.REGISTERED_DUP_USER:
                    System.out.println("User name already exists");
                    break;
            }
        }
    }
}
