package com.llat.views.events;

import com.llat.database.UserObject;
import com.llat.tools.Event;

public class RegisterEvent implements Event {

    private UserObject user;

    public RegisterEvent(String _userName, String _firstName, String _lastName, String _pass) {
        this.user = new UserObject(_userName, _firstName, _lastName, _pass);
    }

    public UserObject getUser() {
        return this.user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
}
