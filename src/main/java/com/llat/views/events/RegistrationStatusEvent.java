package com.llat.views.events;

import com.llat.tools.Event;

public class RegistrationStatusEvent implements Event {
    private int status;

    public RegistrationStatusEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
