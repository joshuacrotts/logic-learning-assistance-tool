/*package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.UserObject;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.LoginView;
import com.llat.views.events.LoginEvent;
import com.llat.views.events.LoginStatusEvent;
import com.llat.views.events.RegistrationStatusEvent;


public class LoginInterpreter implements Listener {
    Controller controller;
    LoginView loginview;

    public LoginInterpreter(Controller _controller, LoginView _registerView) {
        this.controller = _controller;
        this.loginview = _registerView;
        loginview.getLoginButton().setOnAction((node) -> {
            LoginEvent Le = new LoginEvent(this.loginview.getUserNameField().getText(),this.loginview.getPasswordField().getText());
            EventBus.throwEvent(Le);
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {

            }

}*/
