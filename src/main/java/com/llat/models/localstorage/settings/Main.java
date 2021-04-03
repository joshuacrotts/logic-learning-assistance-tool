package com.llat.models.localstorage.settings;

public class Main {
    public static void main(String[] args) {
        SettingsAdaptor sa = new SettingsAdaptor();
        SettingsObject so = (SettingsObject) sa.getData();

        String str = so.getLanguage().getApplied().getCode();
        System.out.println(str);
    }
}
