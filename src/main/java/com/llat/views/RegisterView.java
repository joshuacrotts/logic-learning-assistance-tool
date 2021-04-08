package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.RegisterInterpreter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
    private Region aboveLogoRegion = new Region();
    private Region belowLogoRegion = new Region();
    private Region belowUserNameRegion = new Region();
    private Region belowFirstNameRegion = new Region();
    private Region belowLastNameRegion = new Region();
    private Region belowPasswordFieldRegion = new Region();
    private Region belowLoginButtonRegion = new Region();
    private Controller controller;
    private AnchorPane parentPane = new AnchorPane();
    private Stage stage;
    private VBox registerVBox = new VBox();
    private Label logoImage = new Label();
    private Label userNameInputLabel = new Label("User Name");
    private TextField userNameField = new TextField();
    private Label firstNameInputLabel = new Label("First Name");
    private TextField firstNameField = new TextField();
    private Label lastNameInputLabel = new Label("Last Name");
    private TextField lastNameField = new TextField();
    private Label passwordInputLabel = new Label("Password");
    private PasswordField passwordField = new PasswordField();
    private Button registerButton = new Button("Register");
    private Button returnButton = new Button("Return to Application");

    private RegisterInterpreter registerInterpreter;

    public RegisterView(Controller _controller) {
        this.controller = _controller;
        this.stage = _controller.getStage();

        // Setting VBox loginVBox properties.
        this.registerVBox.setId("registerVBox");
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.registerVBox.setMinWidth(newVal.doubleValue() * .30);
        });

        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.registerVBox.setMinHeight(newVal.doubleValue());
        });

        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.registerVBox.setLayoutX((newVal.doubleValue() * .50) - this.registerVBox.getMinWidth() / 2);
        });

        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.registerVBox.setLayoutY((newVal.doubleValue() * .50) - this.registerVBox.getMinHeight() / 2);
        });

        // Setting Region aboveLogoRegion properties.
        this.registerVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.aboveLogoRegion.setMinHeight(newVal.doubleValue() * .05);
        });

        // Setting Label logoImage properties.
        this.logoImage.setId("registerLogoLabel");
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (this.registerVBox.getHeight() > this.registerVBox.getWidth()) {
                this.logoImage.setMinWidth(newVal.doubleValue() * .40);
                this.logoImage.setMinHeight(newVal.doubleValue() * .40);
            } else {
                this.logoImage.setMinWidth(this.registerVBox.getHeight() * .40);
                this.logoImage.setMinHeight(this.registerVBox.getHeight() * .40);
            }
        });

        this.registerVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (this.registerVBox.getHeight() > this.registerVBox.getWidth()) {
                this.logoImage.setMinWidth(this.registerVBox.getWidth() * .40);
                this.logoImage.setMinHeight(this.registerVBox.getWidth() * .40);
            } else {
                this.logoImage.setMinWidth(newVal.doubleValue() * .40);
                this.logoImage.setMinHeight(newVal.doubleValue() * .40);
            }
        });

        // Setting Region belowLogoRegion properties.
        this.registerVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.belowLogoRegion.setMinHeight(newVal.doubleValue() * .01);
        });

        // Setting Label userNameInputLabel properties.
        this.userNameInputLabel.setId("userNameInputLabel");

        // Setting TextField userNameField properties.
        this.userNameField.setPromptText("Enter your User Name.");
        this.userNameField.setFocusTraversable(false);
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.userNameField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Region belowEmailRegion properties.
        this.registerVBox.heightProperty().addListener(((obs, oldVal, newVal) -> {
            this.belowUserNameRegion.setMinHeight(newVal.doubleValue() * .01);
        }));

        // Setting Label firstNameInputLabel properties.
        this.firstNameInputLabel.setId("firstNameInputLabel");

        // Setting TextField firstNameInputField properties.
        this.firstNameField.setPromptText("Enter your first name.");
        this.firstNameField.setFocusTraversable(false);
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.firstNameField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Label lastNameInputLabel properties.
        this.lastNameInputLabel.setId("lastNameInputLabel");

        // Setting TextField lastNameInputField properties.
        this.lastNameField.setPromptText("Enter your last name.");
        this.lastNameField.setFocusTraversable(false);
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.lastNameField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Region belowEmailRegion properties.
        this.registerVBox.heightProperty().addListener(((obs, oldVal, newVal) -> {
            this.belowLastNameRegion.setMinHeight(newVal.doubleValue() * .01);
        }));

        // Setting Label passwordInputLabel properties.
        this.passwordInputLabel.setId("passwordInputLabel");

        // Setting PasswordField passwordField properties.
        this.passwordField.setPromptText("Enter your password.");
        this.passwordField.setFocusTraversable(false);
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.passwordField.setMaxWidth(newVal.doubleValue() * .70);
        });

        // Setting Region belowPasswordFieldRegion properties.
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.belowPasswordFieldRegion.setMinHeight(newVal.doubleValue() * .05);
        });

        // Setting Button loginButton properties.
        this.registerButton.setId("registerButton");
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.registerButton.setMinWidth(newVal.doubleValue() * .30);
        });

        // Setting Region belowLoginButtonRegion properties.
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.belowLoginButtonRegion.setMinHeight(newVal.doubleValue() * 0.1);
        });

        // Setting Button returnButton properties.
        this.returnButton.setId("returnButton");
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.returnButton.setMinWidth(newVal.doubleValue() * .30);
        });

        this.registerVBox.getChildren().addAll(this.aboveLogoRegion, this.logoImage, belowLogoRegion,
                    this.userNameInputLabel, this.userNameField, this.belowUserNameRegion, this.firstNameInputLabel,
                    this.firstNameField, this.belowFirstNameRegion, this.lastNameInputLabel, this.lastNameField,
                    this.belowLastNameRegion, this.passwordInputLabel, this.passwordField, this.belowPasswordFieldRegion,
                    this.registerButton, this.belowLoginButtonRegion, this.returnButton);

        // Setting AnchorPane parentPane properties.
        this.parentPane.setId("registerViewParentPane");

        // Adding children nodes to their parents nodes.
        this.parentPane.getChildren().addAll(this.registerVBox);

        this.registerInterpreter = new RegisterInterpreter(this.controller, this);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

    public Button getRegisterButton() {
        return this.registerButton;
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(TextField userNameField) {
        this.userNameField = userNameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }
}
