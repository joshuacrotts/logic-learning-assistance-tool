package com.llat.models.localstorage.uidescription.settingsview;

public class Categories {
    public Appearance appearance;
    public Language language;
    public Advanced advanced;


    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Advanced getAdvanced() {
        return advanced;
    }

    public void setAdvanced(Advanced advanced) {
        this.advanced = advanced;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "appearance=" + appearance +
                ", language=" + language +
                ", advanced=" + advanced +
                '}';
    }
}
