package com.llat.views;

import com.llat.controller.Controller;
import com.llat.models.localstorage.uidescription.LoginViewObject;
import com.llat.views.interpreters.LoginViewInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LoginView {

    private final Controller controller;
    private final LoginViewObject uiLoginObject;
    private final Region aboveLogoRegion = new Region();
    private final Region belowLogoRegion = new Region();
    private final Region belowUserNameRegion = new Region();
    private final Region belowPasswordFieldRegion = new Region();
    private final Region belowLoginButtonRegion = new Region();
    private final HBox parentPane = new HBox();
    private final VBox loginVBox = new VBox();
    private final Label logoImage = new Label();
    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final LoginViewInterpreter loginViewInterpreter;
    private Label userNameInputLabel;
    private Label passwordInputLabel = new Label("Password");
    private Button loginButton = new Button("Login");
    private Button returnButton = new Button("Return to Application");

    public LoginView(Controller _controller) {
        this.controller = _controller;
        this.uiLoginObject = this.controller.getUiObject().loginView;

        userNameInputLabel = new Label(this.uiLoginObject.userNameLabel);
        passwordInputLabel = new Label(this.uiLoginObject.passwordLabel);
        loginButton = new Button(this.uiLoginObject.loginButton);
        returnButton = new Button(this.uiLoginObject.returnButton);
        // Setting HBox parentPane properties.
        this.parentPane.setId("loginViewParentPane");
        this.parentPane.setAlignment(Pos.CENTER);

        // Setting VBox loginVBox properties.
        this.loginVBox.setId("loginVBox");
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.loginVBox.setMinWidth(newVal.doubleValue() * .30);
        });

        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.loginVBox.setMinHeight(newVal.doubleValue());
        });
        this.loginVBox.setLayoutX(this.parentPane.getWidth() / 2);
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.loginVBox.setLayoutX((newVal.doubleValue() * .50) - this.loginVBox.getMinWidth() / 2);
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.loginVBox.setLayoutY((newVal.doubleValue() * .50) - this.loginVBox.getMinHeight() / 2);
        });

        // Setting Region aboveLogoRegion properties.
        this.loginVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.aboveLogoRegion.setMinHeight(newVal.doubleValue() * .05);
        });

        // Setting Label logoImage properties.
        this.logoImage.setId("loginLogoLabel");
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (this.loginVBox.getHeight() > this.loginVBox.getWidth()) {
                this.logoImage.setMinWidth(newVal.doubleValue() * .40);
                this.logoImage.setMinHeight(newVal.doubleValue() * .40);
            } else {
                this.logoImage.setMinWidth(this.loginVBox.getHeight() * .40);
                this.logoImage.setMinHeight(this.loginVBox.getHeight() * .40);
            }
        });

        this.loginVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (this.loginVBox.getHeight() > this.loginVBox.getWidth()) {
                this.logoImage.setMinWidth(this.loginVBox.getWidth() * .40);
                this.logoImage.setMinHeight(this.loginVBox.getWidth() * .40);
            } else {
                this.logoImage.setMinWidth(newVal.doubleValue() * .40);
                this.logoImage.setMinHeight(newVal.doubleValue() * .40);
            }
        });

        // Setting Region belowLogoRegion properties.
        this.loginVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.belowLogoRegion.setMinHeight(newVal.doubleValue() * .05);
        });

        // Setting Label emailInputLabel properties.
        this.userNameInputLabel.setId("emailInputLabel");

        // Setting TextField emailField properties.
        this.userNameField.setPromptText(this.uiLoginObject.userNamePromptText);
        this.userNameField.setFocusTraversable(false);
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.userNameField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Region belowEmailRegion properties.
        this.loginVBox.heightProperty().addListener(((obs, oldVal, newVal) -> {
            this.belowUserNameRegion.setMinHeight(newVal.doubleValue() * .10);
        }));

        // Setting Label passwordInputLabel properties.
        this.passwordInputLabel.setId("passwordInputLabel");

        // Setting PasswordField passwordField properties.
        this.passwordField.setPromptText(this.uiLoginObject.passwordPromptText);
        this.passwordField.setFocusTraversable(false);
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.passwordField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Region belowPasswordFieldRegion properties.
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.belowPasswordFieldRegion.setMinHeight(newVal.doubleValue() * .10);
        });

        // Setting Button loginButton properties.
        this.loginButton.setId("loginButton");
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.loginButton.setMinWidth(newVal.doubleValue() * .30);
        });

        // Setting Region belowLoginButtonRegion properties.
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.belowLoginButtonRegion.setMinHeight(newVal.doubleValue() * .10);
        });

        // Setting Button returnButton properties.
        this.returnButton.setId("returnButton");
        this.loginVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.returnButton.setMinWidth(newVal.doubleValue() * .30);
        });
        this.loginVBox.getChildren().addAll(this.aboveLogoRegion, this.logoImage, this.belowLogoRegion, this.userNameInputLabel,
                this.userNameField, this.belowUserNameRegion, this.passwordInputLabel, this.passwordField,
                this.belowPasswordFieldRegion, this.loginButton, this.belowLoginButtonRegion, this.returnButton);

        // Adding children nodes to their parents nodes.
        this.parentPane.getChildren().addAll(this.loginVBox);
        // Creating interpreter to handle events and actions.
        this.loginViewInterpreter = new LoginViewInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public Button getReturnButton() {
        return this.returnButton;
    }

    public Button getLoginButton() {
        return this.loginButton;
    }

    public TextField getUserNameField() {
        return this.userNameField;
    }

    public TextField getPasswordField() {
        return this.passwordField;
    }
}
