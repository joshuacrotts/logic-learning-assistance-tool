package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.DatabaseAdapter;
import com.llat.database.UserObject;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.credentials.CredentialsAdaptor;
import com.llat.models.localstorage.credentials.CredentialsObject;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

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
            CredentialsAdaptor ca = new CredentialsAdaptor();
            CredentialsObject co = (CredentialsObject) ca.getData();
            co.setUserID(this.controller.getUser().getUserName());
            co.setPassword(this.controller.getUser().getPword());
            ca.update(co);
            this.controller.getStage().close();
            new Window(new Stage(), true);
        }

        if (_event instanceof LoginFailEvent) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Username or password is wrong. Please try again.", ButtonType.OK);
            alert.setTitle("Warning");
            alert.show();
        }
    }
}
