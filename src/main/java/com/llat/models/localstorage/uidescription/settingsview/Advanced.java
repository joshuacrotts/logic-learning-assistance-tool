package com.llat.models.localstorage.uidescription.settingsview;

public class Advanced {
    public String label;
    public TimeOut timeOut;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TimeOut getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(TimeOut timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "Advanced{" +
                "label='" + label + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }
}
