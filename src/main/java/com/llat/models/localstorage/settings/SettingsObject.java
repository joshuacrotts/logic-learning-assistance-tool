package com.llat.models.localstorage.settings;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.langague.LangaugeObject;

import java.util.List;

/**
 *
 */
public class SettingsObject extends LocalStorage {

    //    public String test;
    private Language language;
    private Theme theme;

    public SettingsObject(Language language, Theme theme) {
        this.language = language;
        this.theme = theme;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     *
     */
    public class Theme {
        private String applied;
        private List<String> allThemes;

        public String getApplied() {
            return applied;
        }

        public void setApplied(String applied) {
            this.applied = applied;
        }

        public List<String> getAllThemes() {
            return allThemes;
        }

        public void setAllThemes(List<String> allThemes) {
            this.allThemes = allThemes;
        }
    }

    /**
     *
     */
    public class Language {
        private LangaugeObject applied;
        private List<LangaugeObject> allLanguages;

        public Language(LangaugeObject applied, List<LangaugeObject> allLanguages) {
            this.applied = applied;
            this.allLanguages = allLanguages;
        }

        public LangaugeObject getApplied() {
            return applied;
        }

        public void setApplied(LangaugeObject applied) {
            this.applied = applied;
        }

        public List<LangaugeObject> getAllLanguages() {
            return allLanguages;
        }

        public void setAllLanguages(List<LangaugeObject> allLanguages) {
            this.allLanguages = allLanguages;
        }
    }

}