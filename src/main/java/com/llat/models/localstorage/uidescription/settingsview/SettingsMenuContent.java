package com.llat.models.localstorage.uidescription.settingsview;

public class SettingsMenuContent {
    public String name;
    public String code;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AllLanguage{" +
                "name='" + this.name + '\'' +
                ", code='" + this.code + '\'' +
                '}';
    }
}
