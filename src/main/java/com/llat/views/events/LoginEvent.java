package com.llat.views.events;

import com.llat.controller.Controller;
import com.llat.database.UserObject;
import com.llat.tools.Event;

public class LoginEvent implements Event {
    UserObject user;


    public LoginEvent(String _userName, String _pass) {

        user = new UserObject(_userName, _pass);
    }

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
}
