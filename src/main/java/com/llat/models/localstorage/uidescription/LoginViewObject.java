package com.llat.models.localstorage.uidescription;

public class LoginViewObject {
    public String userNameLabel;
    public String userNamePromptText;
    public String passwordLabel;
    public String passwordPromptText;
    public String loginButton;
    public String returnButton;

    public String getUserNameLabel() {
        return this.userNameLabel;
    }

    public void setUserNameLabel(String userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public String getUserNamePromptText() {
        return this.userNamePromptText;
    }

    public void setUserNamePromptText(String userNamePromptText) {
        this.userNamePromptText = userNamePromptText;
    }

    public String getPasswordLabel() {
        return this.passwordLabel;
    }

    public void setPasswordLabel(String passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public String getPasswordPromptText() {
        return this.passwordPromptText;
    }

    public void setPasswordPromptText(String passwordPromptText) {
        this.passwordPromptText = passwordPromptText;
    }

    public String getLoginButton() {
        return this.loginButton;
    }

    public void setLoginButton(String loginButton) {
        this.loginButton = loginButton;
    }

    public String getReturnButton() {
        return this.returnButton;
    }

    public void setReturnButton(String returnButton) {
        this.returnButton = returnButton;
    }

    @Override
    public String toString() {
        return "LoginView{" +
                "userNameLabel='" + this.userNameLabel + '\'' +
                ", userNamePromptText='" + this.userNamePromptText + '\'' +
                ", passwordLabel='" + this.passwordLabel + '\'' +
                ", passwordPromptText='" + this.passwordPromptText + '\'' +
                ", loginButton='" + this.loginButton + '\'' +
                ", returnButton='" + this.returnButton + '\'' +
                '}';
    }
}
