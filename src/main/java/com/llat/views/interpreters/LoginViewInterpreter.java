package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.DatabaseAdapter;
import com.llat.database.UserObject;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.LoginView;

public class LoginViewInterpreter implements Listener {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final LoginView loginView;

    public LoginViewInterpreter(Controller _controller, LoginView _registerView) {
        this.controller = _controller;
        this.loginView = _registerView;
        this.controller.loginOnAction(this.loginView.getLoginButton(), this.loginView.getUserNameField(), this.loginView.getPasswordField());
        this.loginView.getReturnButton().setOnAction((event) -> {
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
    }

}
