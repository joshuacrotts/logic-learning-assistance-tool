package com.llat.models.localstorage.uidescription.mainview;

import java.util.List;

public abstract class MainMenu {
    public String label;
    public List<Content> content;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Content> getContent() {
        return this.content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MainMenu{" +
                "label='" + this.label + '\'' +
                ", content=" + this.content +
                '}';
    }
}
