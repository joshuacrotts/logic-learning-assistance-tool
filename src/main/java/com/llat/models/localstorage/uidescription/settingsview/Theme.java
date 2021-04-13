package com.llat.models.localstorage.uidescription.settingsview;

import com.llat.models.localstorage.ItemObject;
import com.llat.models.localstorage.settings.theme.ThemeObject;

import java.util.List;

public class Theme {
    public String label;
    public Applied applied;
    public List<ThemeObject> allThemes;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Applied getApplied() {
        return applied;
    }

    public void setApplied(Applied applied) {
        this.applied = applied;
    }

    public List<ThemeObject> getAllThemes() {
        return allThemes;
    }

    public void setAllThemes(List<ThemeObject> allThemes) {
        this.allThemes = allThemes;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "label='" + label + '\'' +
                ", applied=" + applied +
                ", allThemes=" + allThemes +
                '}';
    }
}
