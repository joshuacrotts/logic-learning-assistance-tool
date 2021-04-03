package com.llat.models.localstorage.credentials;

import com.llat.models.localstorage.LocalStorage;

public class UserCredentialsObject extends LocalStorage {

    private String User;
    private String Password;


    // Getter Methods

    public String getUser() {
        return User;
    }

    public String getPassword() {
        return Password;
    }

    // Setter Methods

    public void setUser(String User) {
        this.User = User;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}

