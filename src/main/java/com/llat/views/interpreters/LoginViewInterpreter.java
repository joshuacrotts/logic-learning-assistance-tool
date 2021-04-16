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
        this.loginView.getLoginButton().setOnAction((event1 -> {
            DatabaseAdapter ad = new DatabaseAdapter();
            String email = this.loginView.getUserNameField().getText();
            String pass = this.loginView.getPasswordField().getText();
            UserObject user = ad.Login(email, pass);
            if (user != null) {
                System.out.println("User logged");
                this.controller.setUser(user);
                this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
            } else {
                System.out.println("Password or email is wrong");
            }
        }));

        this.loginView.getReturnButton().setOnAction((event) -> {
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {

    }
}
