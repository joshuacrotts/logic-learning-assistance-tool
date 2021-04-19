package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.database.DatabaseAdapter;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.RegisterView;
import com.llat.views.events.RegistrationStatusEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
        this.controller.registerAction(this.registerView.getRegisterButton(), this.registerView.getUserNameField(), this.registerView.getFirstNameField(), this.registerView.getLastNameField(), this.registerView.getPasswordField());
        this.registerView.getReturnButton().setOnAction((event) -> {
            this.controller.changeViewTo(ViewManager.MAINAPPLICATION);
        });

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof RegistrationStatusEvent) {
            Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
            alert.setTitle("Warning");
            switch (((RegistrationStatusEvent) _event).getStatus()) {
                case DatabaseAdapter.REGISTERED_SUCCESSFULLY:
                    System.out.println("User Registered");
                    break;
                case DatabaseAdapter.REGISTERED_EMPTY_INPUT:
                    alert.setContentText("You need to fill all the required fields");
                    alert.show();
                    break;
                case DatabaseAdapter.REGISTERED_DUP_USER:
                    alert.setContentText("User name already exists");
                    alert.show();
                    break;
            }
        }
    }
}
