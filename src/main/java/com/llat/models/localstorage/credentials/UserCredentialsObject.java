package com.llat.models.localstorage.credentials;

import com.llat.models.localstorage.LocalStorage;

public class UserCredentialsObject extends LocalStorage {

    private String User;
    private String Password;


    // Getter Methods

    public String getUser() {
        return this.User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    // Setter Methods

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}

