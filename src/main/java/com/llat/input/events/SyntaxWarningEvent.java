package com.llat.input.events;

import com.llat.tools.Event;

public class SyntaxWarningEvent implements Event {
    String warningMessage;

    public SyntaxWarningEvent(String _warningMessage) {
        this.warningMessage = _warningMessage;
    }

    public String getWarningMessage() {
        return this.warningMessage;
    }

}
