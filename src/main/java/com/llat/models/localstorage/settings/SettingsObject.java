package com.llat.models.localstorage.settings;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.language.LanguageObject;
import com.llat.models.localstorage.settings.theme.ThemeObject;

/**
 *
 */
public class SettingsObject extends LocalStorage {

    //    public String test;
    public static LanguageObject languageObject;

    private Language language;
    private Theme theme;

    public SettingsObject(Language language, Theme theme) {
        SettingsObject.languageObject = this.language.applied;
        this.language = language;
        this.theme = theme;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "SettingsObject{" +
                "language=" + this.language +
                ", theme=" + this.theme +
                '}';
    }

    /**
     *
     */
    public class Theme {
        private ThemeObject applied;

        public Theme(ThemeObject applied) {
            this.applied = applied;
        }

        public ThemeObject getApplied() {
            return this.applied;
        }

        public void setApplied(ThemeObject applied) {
            this.applied = applied;
        }

        @Override
        public String toString() {
            return "Theme{" +
                    "applied=" + this.applied +
                    '}';
        }
    }

    /**
     *
     */
    public class Language {
        private LanguageObject applied;

        public Language(LanguageObject applied) {
            this.applied = applied;
        }

        public LanguageObject getApplied() {
            return this.applied;
        }

        public void setApplied(LanguageObject applied) {
            this.applied = applied;
        }

        @Override
        public String toString() {
            return "Language{" +
                    "applied=" + this.applied +
                    '}';
        }
    }
}