package com.llat.models.localstorage.settings;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.language.LanguageObject;
import com.llat.models.localstorage.settings.theme.ThemeObject;

import java.util.List;

/**
 *
 */
public class SettingsObject extends LocalStorage {

    /**
     *
     */
    public static LanguageObject languageObject;

    /**
     *
     */
    private Language language;

    /**
     *
     */
    private Theme theme;

    public SettingsObject(Language language, Theme theme) {
        this.language = language;
        this.theme = theme;
    }

    public Language getLanguage() {
        SettingsObject.languageObject = this.language.applied;
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

    /**
     *
     */
    public class Theme {
        private ThemeObject applied;
        private List<ThemeObject> allThemes;

        public Theme(ThemeObject applied, List<ThemeObject> allThemes) {
            this.applied = applied;
            this.allThemes = allThemes;
        }

        public ThemeObject getApplied() {
            return applied;
        }

        public void setApplied(ThemeObject applied) {
            this.applied = applied;
        }

        public List<ThemeObject> getAllThemes() {
            return allThemes;
        }

        public void setAllThemes(List<ThemeObject> allThemes) {
            this.allThemes = allThemes;
        }
    }

    /**
     *
     */
    public class Language {
        private LanguageObject applied;
        private List<LanguageObject> allLanguages;

        public Language(LanguageObject applied, List<LanguageObject> allLanguages) {
            this.applied = applied;
            this.allLanguages = allLanguages;
        }

        public LanguageObject getApplied() {
            return applied;
        }

        public void setApplied(LanguageObject applied) {
            this.applied = applied;
        }

        public List<LanguageObject> getAllLanguages() {
            return allLanguages;
        }

        public void setAllLanguages(List<LanguageObject> allLanguages) {
            this.allLanguages = allLanguages;
        }
    }

}