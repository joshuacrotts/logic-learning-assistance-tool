package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.LoginView;
import com.llat.views.events.LoginFailEvent;
import com.llat.views.events.LoginSuccessEvent;
import com.llat.views.events.UpdateHistoryEvent;

public class LoginViewInterpreter implements Listener {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final LoginView loginView;

    public LoginViewInterpreter(Controller _controller, LoginView _loginView) {
        this.controller = _controller;
        this.loginView = _loginView;
        this.controller.loginOnAction(this.loginView.getLoginButton(), this.loginView.getUserNameField(), this.loginView.getPasswordField());
        this.loginView.getReturnButton().setOnAction((event) -> {
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof LoginSuccessEvent) {
            System.out.println("User logged in");
        }
        if (_event instanceof LoginFailEvent) {
            System.out.println("Username or Password is Wrong");
        }
    }

}
