package com.llat.models.localstorage.credentials;

import com.llat.models.localstorage.LocalStorage;

public class CredentialsObject extends LocalStorage {

    private String userID;
    private String password;

    public CredentialsObject(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCredentialsObject{" +
                "userID='" + this.userID + '\'' +
                ", password='" + this.password + '\'' +
                '}';
    }
}

