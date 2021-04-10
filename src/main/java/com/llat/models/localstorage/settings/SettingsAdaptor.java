package com.llat.models.localstorage.settings;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;

public class SettingsAdaptor implements SettingsInterface {

    /**
     *
     */
    private final String jsonFileName = "settings.json";

    /**
     *
     */
    private final SettingsInterface settings = new GsonIO(this.jsonFileName, SettingsObject.class);

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        this.settings.update(_obj, _jsonFilePath);
    }

    public void update(LocalStorage _obj) {
        this.settings.update(_obj, this.jsonFileName);
    }

    @Override
    public LocalStorage getData() {
        return this.settings.getData();
    }
}
