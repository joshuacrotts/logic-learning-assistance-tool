package com.llat.views.events;

import com.llat.tools.Event;

public class InputEvent implements Event {

    /**
     *
     */
    private String parameter;

    public InputEvent(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
