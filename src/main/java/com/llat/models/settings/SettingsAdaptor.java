package com.llat.models.settings;

import com.llat.models.gson.GsonIO;
import com.llat.models.gson.GsonObject;

public class SettingsAdaptor implements SettingsInterface {

    private final String jsonFileName = "settings.json";

    private Object SettingsObject;

    /**
     *
     */
    SettingsInterface settings = (SettingsInterface) new GsonIO(jsonFileName, SettingsObject, SettingsObject.class);


    @Override
    public void update(Object _obj, String _jsonFilePath) {
        settings.update(_obj, _jsonFilePath);
    }

    public void update(Object _obj) {
        settings.update(_obj, jsonFileName);
    }

    @Override
    public GsonObject getData() {
        return settings.getData();
    }
}
