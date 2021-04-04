package com.llat.models.localstorage.uidescription;

import com.llat.models.localstorage.LocalStorage;

public interface UIDescriptionInterface {

    void update(LocalStorage _obj, String _jsonFilePath);

    LocalStorage getData();
}
