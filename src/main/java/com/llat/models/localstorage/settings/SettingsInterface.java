package com.llat.models.localstorage.settings;

import com.llat.models.localstorage.LocalStorage;

/**
 *
 */
public interface SettingsInterface {


    void update(Object _obj, String _jsonFilePath);

    LocalStorage getData();
}
