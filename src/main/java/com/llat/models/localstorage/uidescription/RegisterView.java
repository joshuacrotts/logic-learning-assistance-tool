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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(String registerButton) {
        this.registerButton = registerButton;
    }

    public String getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(String returnButton) {
        this.returnButton = returnButton;
    }

    public String getFirstNamePromptText() {
        return firstNamePromptText;
    }

    public void setFirstNamePromptText(String firstNamePromptText) {
        this.firstNamePromptText = firstNamePromptText;
    }

    public String getLastNamePromptText() {
        return lastNamePromptText;
    }

    public void setLastNamePromptText(String lastNamePromptText) {
        this.lastNamePromptText = lastNamePromptText;
    }

    @Override
    public String toString() {
        return "RegisterView{" +
                "userNameLabel='" + userNameLabel + '\'' +
                ", userNamePromptText='" + userNamePromptText + '\'' +
                ", firstNamePromptText='" + firstNamePromptText + '\'' +
                ", lastNamePromptText='" + lastNamePromptText + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passwordLabel='" + passwordLabel + '\'' +
                ", passwordPromptText='" + passwordPromptText + '\'' +
                ", registerButton='" + registerButton + '\'' +
                ", returnButton='" + returnButton + '\'' +
                '}';
    }
}
