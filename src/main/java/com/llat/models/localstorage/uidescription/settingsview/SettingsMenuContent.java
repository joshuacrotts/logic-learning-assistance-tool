package com.llat.models.localstorage.uidescription.settingsview;

public class SettingsMenuContent {
    public String name;
    public String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AllLanguage{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
