package com.llat.views.events;

import com.llat.database.UserObject;
import com.llat.tools.Event;

public class LoginEvent implements Event {

    /**
     *
     */
    private UserObject user;

    public LoginEvent(String _userName, String _pass) {
        this.user = new UserObject(_userName, _pass);
    }

    public UserObject getUser() {
        return this.user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
}
