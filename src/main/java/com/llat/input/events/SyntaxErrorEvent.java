package com.llat.input.events;

import com.llat.tools.Event;

public class SyntaxErrorEvent implements Event {
    String errorMessage;

    public SyntaxErrorEvent(String _errorMessage) {
        this.errorMessage = _errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
