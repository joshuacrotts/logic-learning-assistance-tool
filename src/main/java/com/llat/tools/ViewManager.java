package com.llat.tools;

import java.io.File;

public class ViewManager {
    public final static int LOGIN = 0;
    public final static int MAINAPPLICATION = 1;

    public final static String RESOURCEPATH = "src/main/java/com/llat/assets/stylesheets/";

    public static String getDefaultStyle () {
        File styleSheet = new File(ViewManager.RESOURCEPATH + "defaultStyle.css");
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }

}
