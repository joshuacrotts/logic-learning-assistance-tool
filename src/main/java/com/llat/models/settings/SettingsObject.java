package com.llat.models.settings;

import java.util.List;

/**
 *
 */
public class SettingsObject {

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
        private String applied;
        private List<String> allLanguages;

        public Language(String applied, List<String> allLanguages) {
            this.applied = applied;
            this.allLanguages = allLanguages;
        }

        public String getApplied() {
            return applied;
        }

        public void setApplied(String applied) {
            this.applied = applied;
        }

        public List<String> getAllLanguages() {
            return allLanguages;
        }

        public void setAllLanguages(List<String> allLanguages) {
            this.allLanguages = allLanguages;
        }
    }
}