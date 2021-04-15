package com.llat.models.localstorage.uidescription.menubar;

public class Help {
    public String label;
    public About about;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public About getAbout() {
        return this.about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Help{" +
                "label='" + this.label + '\'' +
                ", about=" + this.about +
                '}';
    }
}
