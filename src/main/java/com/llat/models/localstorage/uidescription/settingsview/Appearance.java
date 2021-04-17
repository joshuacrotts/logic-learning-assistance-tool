package com.llat.models.localstorage.uidescription.settingsview;

public class Appearance {
    public String label;
    public Theme theme;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Appearance{" +
                "label='" + this.label + '\'' +
                ", theme=" + this.theme +
                '}';
    }
}
