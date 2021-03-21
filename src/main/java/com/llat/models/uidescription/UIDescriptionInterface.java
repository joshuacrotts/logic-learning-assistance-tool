package com.llat.models.uidescription;

import com.llat.models.gson.GsonObject;

public interface UIDescriptionInterface {

    void update(Object _obj, String _jsonFilePath);

    GsonObject getData();
}
