package com.llat.models.settings;

import com.llat.models.settings.gson.GsonSettings;

public class SettingsAdaptor implements SettingsInterface {

    /**
     *
     */
    public SettingsInterface settings = new GsonSettings();

    @Override
    public void update(SettingsObject settingsObject) {
        settings.update(settingsObject);
    }

    @Override
    public SettingsObject getData() {
        return settings.getData();
    }
}
