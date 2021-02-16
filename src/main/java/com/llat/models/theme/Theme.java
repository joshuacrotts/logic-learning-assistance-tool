package com.llat.models.theme;

import com.llat.models.settings.SettingsAdaptor;
import com.llat.models.settings.SettingsObject;

public class Theme {
    private static final String STYLE_PATH = "/style/";

    //Get the theme that been saved from setting file
    public static String getTheme() {
        SettingsAdaptor settings = new SettingsAdaptor();
        String appliedTheme = settings.getData().getTheme().getApplied();
        String theme = Theme.class.getResource(STYLE_PATH + appliedTheme).toExternalForm();
        return theme;
    }
}
