package com.llat.models.localstorage.settings;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;

public class SettingsAdaptor implements SettingsInterface {

    /**
     *
     */
    private final String SETTINGS_FILE_PATH = "settings.json";

    /**
     *
     */
    SettingsInterface settings = new GsonIO(this.SETTINGS_FILE_PATH, SettingsObject.class);

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        this.settings.update(_obj, _jsonFilePath);
    }

    public void update(LocalStorage _obj) {
        this.settings.update(_obj, this.SETTINGS_FILE_PATH);
    }

    @Override
    public LocalStorage getData() {
        return this.settings.getData();
    }
}
