package com.llat.models.localstorage.uidescription;

public class LoginView {
    public String userNameLabel;
    public String userNamePromptText;
    public String passwordLabel;
    public String passwordPromptText;
    public String loginButton;
    public String returnButton;

    public String getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(String userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public String getUserNamePromptText() {
        return userNamePromptText;
    }

    public void setUserNamePromptText(String userNamePromptText) {
        this.userNamePromptText = userNamePromptText;
    }

    public String getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(String passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public String getPasswordPromptText() {
        return passwordPromptText;
    }

    public void setPasswordPromptText(String passwordPromptText) {
        this.passwordPromptText = passwordPromptText;
    }

    public String getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(String loginButton) {
        this.loginButton = loginButton;
    }

    public String getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(String returnButton) {
        this.returnButton = returnButton;
    }

    @Override
    public String toString() {
        return "LoginView{" +
                "userNameLabel='" + userNameLabel + '\'' +
                ", userNamePromptText='" + userNamePromptText + '\'' +
                ", passwordLabel='" + passwordLabel + '\'' +
                ", passwordPromptText='" + passwordPromptText + '\'' +
                ", loginButton='" + loginButton + '\'' +
                ", returnButton='" + returnButton + '\'' +
                '}';
    }
}
