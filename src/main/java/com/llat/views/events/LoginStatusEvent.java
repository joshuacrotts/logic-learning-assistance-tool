package com.llat.views.events;

import com.llat.database.UserObject;

public class LoginStatusEvent {
    UserObject user;


    public LoginStatusEvent(UserObject user) {


    }

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
}
