package com.llat.models.localstorage.uidescription.settingsview;

public class Categories {
    public Appearance appearance;
    public Language language;
    public Advanced advanced;


    public Appearance getAppearance() {
        return this.appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Advanced getAdvanced() {
        return this.advanced;
    }

    public void setAdvanced(Advanced advanced) {
        this.advanced = advanced;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "appearance=" + this.appearance +
                ", language=" + this.language +
                ", advanced=" + this.advanced +
                '}';
    }
}
