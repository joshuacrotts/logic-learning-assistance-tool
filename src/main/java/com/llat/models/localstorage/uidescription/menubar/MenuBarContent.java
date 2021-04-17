package com.llat.models.localstorage.uidescription.menubar;

import com.llat.models.localstorage.uidescription.mainview.Content;

import java.util.List;

public abstract class MenuBarContent {
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
        return "MenuBarContent{" +
                "label='" + this.label + '\'' +
                ", content=" + this.content +
                '}';
    }
}
