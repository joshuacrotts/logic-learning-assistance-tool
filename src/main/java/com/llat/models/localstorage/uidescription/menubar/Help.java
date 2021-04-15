package com.llat.models.localstorage.uidescription.menubar;

public class Help {
    public String label;
    public About about;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Help{" +
                "label='" + label + '\'' +
                ", about=" + about +
                '}';
    }
}
