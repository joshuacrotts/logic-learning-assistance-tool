package com.llat.views;

import com.llat.controller.Controller;
import com.llat.database.DatabaseAdapter;
import com.llat.database.UserObject;
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
    Region aboveLogoRegion = new Region();
    Region belowLogoRegion = new Region();
    Region belowUserNameRegion = new Region();
    Region belowFirstNameRegion = new Region();
    Region belowLastNameRegion = new Region();
    Region belowPasswordFieldRegion = new Region();
    Region belowLoginButtonRegion = new Region();
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


        this.registerButton.setOnAction((event1 -> {
            DatabaseAdapter ad = new DatabaseAdapter();
            String email = this.userNameField.getText();
            String pass = this.passwordField.getText();
            UserObject user = ad.Login(email, pass);
            if (!(user == null)) {
                System.out.println(user.getHistory());
            } else {
                System.out.println("pass or email is wrong");
            }
        }));
        // Setting Region belowLoginButtonRegion properties.
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.belowLoginButtonRegion.setMinHeight(newVal.doubleValue() * 0.1);
        });
        // Setting Button returnButton properties.
        this.returnButton.setId("returnButton");
        this.registerVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.returnButton.setMinWidth(newVal.doubleValue() * .30);
        });
        this.registerVBox.getChildren().addAll(this.aboveLogoRegion, this.logoImage, belowLogoRegion, this.userNameInputLabel, this.userNameField, this.belowUserNameRegion, this.firstNameInputLabel, this.firstNameField, this.belowFirstNameRegion, this.lastNameInputLabel, this.lastNameField, this.belowLastNameRegion, this.passwordInputLabel, this.passwordField, this.belowPasswordFieldRegion, this.registerButton, this.belowLoginButtonRegion, this.returnButton);
        // Setting AnchorPane parentPane properties.
        this.parentPane.setId("registerViewParentPane");
        // Adding children nodes to their parents nodes.
        this.parentPane.getChildren().addAll(this.registerVBox);
    }

    public Pane getParentPane() {
        return this.parentPane;
    }

}
