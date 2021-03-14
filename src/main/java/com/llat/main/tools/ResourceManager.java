package com.llat.main.tools;

import java.io.File;

public class ResourceManager {
    public static String RESOURCEPATH = "src/main/java/com/llat/main/css/";

    public static String getSymbolInputViewCSS () {
        File styleSheet = new File(ResourceManager.RESOURCEPATH + "SymbolInputViewStyle.css");
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }

    public static String getRulesAxiomsViewCSS () {
        File styleSheet = new File(ResourceManager.RESOURCEPATH + "RulesAxiomsViewStyle.css");
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }

    public static String getFormulaInputViewCSS () {
        File styleSheet = new File(ResourceManager.RESOURCEPATH + "FormulaInputView.css");
        return "file:///" + styleSheet.getAbsolutePath().replace("\\", "/").replaceAll("\\u0020", "%20");
    }
}
