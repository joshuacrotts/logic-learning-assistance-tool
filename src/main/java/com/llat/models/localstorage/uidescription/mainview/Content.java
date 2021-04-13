package com.llat.models.localstorage.uidescription.mainview;

import java.util.List;

public class Content {
    public String label;
    public List<String> content;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" +
                "label='" + label + '\'' +
                ", content=" + content +
                '}';
    }
}
