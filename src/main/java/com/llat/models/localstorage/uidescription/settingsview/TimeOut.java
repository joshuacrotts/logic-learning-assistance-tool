package com.llat.models.localstorage.uidescription.settingsview;

public class TimeOut {
    public String label;
    public int value;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TimeOut{" +
                "label='" + this.label + '\'' +
                ", value=" + this.value +
                '}';
    }
}