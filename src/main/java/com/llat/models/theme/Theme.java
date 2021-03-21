package com.llat.models.theme;

import com.llat.models.settings.SettingsAdaptor;

/**
 *
 */
public class Theme {

    /**
     *
     */
    private static final String STYLE_PATH = "/style/";

    /**
     * @return
     */
    public static String getTheme() {
        SettingsAdaptor settings = new SettingsAdaptor();
        String appliedTheme = settings.getData().getTheme().getApplied();
        String theme = Theme.class.getResource(STYLE_PATH + appliedTheme).toExternalForm();
        return theme;
    }
}
