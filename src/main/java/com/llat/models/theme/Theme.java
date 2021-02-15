package com.llat.models.theme;

public class Theme {

    //Get the theme that been saved from setting file

    public static String getTheme() {
        String theme = Theme.class.getResource("/style/default.css").toExternalForm();
        return theme;
    }
}
