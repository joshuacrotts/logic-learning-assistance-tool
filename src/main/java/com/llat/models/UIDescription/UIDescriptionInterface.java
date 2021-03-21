package com.llat.models.UIDescription;

import com.llat.models.Gson.GsonObject;
import com.llat.models.settings.SettingsObject;

public interface UIDescriptionInterface {

    void update(Object _obj, String _jsonFilePath);

    GsonObject getData();
}
