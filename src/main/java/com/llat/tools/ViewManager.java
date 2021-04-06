package com.llat.tools;

import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;

import java.io.File;

public class ViewManager {
    public final static int MAINAPPLICATION = 0;
    public final static int LOGIN = 1;
    public final static int SETTINGS = 2;

    public final static String RESOURCEPATH = "src/main/resources/assets/stylesheets/";

    public static String getDefaultStyle() {
        SettingsObject so = (SettingsObject) new SettingsAdaptor().getData();
        File styleSheet = new File(ViewManager.RESOURCEPATH + so.getTheme().getApplied().getCode());
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }
    public static String getDefaultStyle(String _theme) {
        File styleSheet = new File(ViewManager.RESOURCEPATH + _theme);
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }
    public static String getStyle(){
        SettingsObject so = (SettingsObject) new SettingsAdaptor().getData();
        String theme = so.getTheme().getApplied().getCode();
        return getDefaultStyle(theme);
    }
}
