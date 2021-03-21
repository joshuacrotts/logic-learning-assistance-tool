package com.llat.models.settings;

import com.llat.models.Gson.GsonObject;

/**
 *
 */
public interface SettingsInterface {


    void update(Object _obj, String _jsonFilePath);

    GsonObject getData();
}
