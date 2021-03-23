package com.llat.models.settings;

import com.llat.models.gson.GsonObject;

/**
 *
 */
public interface SettingsInterface {


    void update(Object _obj, String _jsonFilePath);

    GsonObject getData();
}
