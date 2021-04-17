package com.llat.models.localstorage.uidescription;

public class RegisterView {
    public String userNameLabel;
    public String userNamePromptText;
    public String firstNamePromptText;
    public String lastNamePromptText;
    public String firstName;
    public String lastName;
    public String passwordLabel;
    public String passwordPromptText;
    public String registerButton;
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRegisterButton() {
        return this.registerButton;
    }

    public void setRegisterButton(String registerButton) {
        this.registerButton = registerButton;
    }

    public String getReturnButton() {
        return this.returnButton;
    }

    public void setReturnButton(String returnButton) {
        this.returnButton = returnButton;
    }

    public String getFirstNamePromptText() {
        return this.firstNamePromptText;
    }

    public void setFirstNamePromptText(String firstNamePromptText) {
        this.firstNamePromptText = firstNamePromptText;
    }

    public String getLastNamePromptText() {
        return this.lastNamePromptText;
    }

    public void setLastNamePromptText(String lastNamePromptText) {
        this.lastNamePromptText = lastNamePromptText;
    }

    @Override
    public String toString() {
        return "RegisterView{" +
                "userNameLabel='" + this.userNameLabel + '\'' +
                ", userNamePromptText='" + this.userNamePromptText + '\'' +
                ", firstNamePromptText='" + this.firstNamePromptText + '\'' +
                ", lastNamePromptText='" + this.lastNamePromptText + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", passwordLabel='" + this.passwordLabel + '\'' +
                ", passwordPromptText='" + this.passwordPromptText + '\'' +
                ", registerButton='" + this.registerButton + '\'' +
                ", returnButton='" + this.returnButton + '\'' +
                '}';
    }
}
