package com.llat.models.localstorage.uidescription.mainview;

import java.util.List;

public abstract class MainMenu {
    public String label;
    public List<Content> content;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MainMenu{" +
                "label='" + label + '\'' +
                ", content=" + content +
                '}';
    }
}
