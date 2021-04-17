package com.llat.models.localstorage.uidescription.mainview;

import java.util.List;

public class Content {
    public String label;
    public List<String> content;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getContent() {
        return this.content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" +
                "label='" + this.label + '\'' +
                ", content=" + this.content +
                '}';
    }
}
