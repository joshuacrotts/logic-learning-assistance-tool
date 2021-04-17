package com.llat.models.localstorage.uidescription.settingsview;

public class Language {
    public String label;
    public LanguageContent languageContent;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LanguageContent getLanguageContent() {
        return this.languageContent;
    }

    public void setLanguageContent(LanguageContent language) {
        this.languageContent = language;
    }

    @Override
    public String toString() {
        return "Language{" +
                "label='" + this.label + '\'' +
                ", LangaugeObject=" + this.languageContent +
                '}';
    }
}
