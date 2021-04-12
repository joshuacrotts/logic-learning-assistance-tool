package com.llat.views.events;

import com.llat.database.UserObject;

public class LoginStatusEvent {

    /**
     *
     */
    private UserObject user;

    public LoginStatusEvent(UserObject user) {

    }

    public UserObject getUser() {
        return this.user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
}
