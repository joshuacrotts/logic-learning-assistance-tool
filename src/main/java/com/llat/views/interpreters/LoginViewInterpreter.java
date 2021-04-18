package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.UserObject;
import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.LoginView;
import com.llat.views.events.LoginFailEvent;
import com.llat.views.events.LoginSuccessEvent;
import com.llat.views.events.UpdateHistoryEvent;
import com.llat.main.Window;
import javafx.stage.Stage;

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
            String UserTheme = this.controller.getUser().getTheme();
            String UserLanguage = this.controller.getUser().getLanguage();
            SettingsAdaptor sa = new SettingsAdaptor();
            SettingsObject so = (SettingsObject) sa.getData();
            so.getTheme().getApplied().setCode(UserTheme);
            so.getLanguage().getApplied().setCode(UserLanguage);

            sa.update(so);

            this.controller.getStage().close();
            UserObject x = this.controller.getUser();
            Stage st = new Stage();
            Controller c = new Controller(st);
            c.setUser(x);
            new Window(c);


        }
        if (_event instanceof LoginFailEvent) {
            System.out.println("Username or Password is Wrong");

        }
    }

}
