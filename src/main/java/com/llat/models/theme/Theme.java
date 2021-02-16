package com.llat.models.theme;

import com.llat.models.settings.SettingsAdaptor;
import com.llat.models.settings.SettingsObject;

public class Theme {
    private static final String STYLE_PATH = "/style/";
    private static SettingsAdaptor settings = new SettingsAdaptor();

    //Get the theme that been saved from setting file
    public static String getTheme() {
        String appliedTheme = settings.getData().getTheme().getApplied();
        String theme = Theme.class.getResource(STYLE_PATH + appliedTheme).toExternalForm();
        return theme;
    }

    public static void main(String[] args) {
        SettingsObject obj = settings.getData();
        String appliedTheme = settings.getData().getTheme().getApplied();
    }
}
