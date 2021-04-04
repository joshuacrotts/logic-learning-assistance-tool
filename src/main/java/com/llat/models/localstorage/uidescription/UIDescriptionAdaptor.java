package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;

public class UIDescriptionAdaptor implements UIDescriptionInterface {
    SettingsAdaptor sa = new SettingsAdaptor();
    SettingsObject so = (SettingsObject) sa.getData();

    String code = so.getLanguage().getApplied().getCode();

    private final String UI_DESCRIPTION = "UID/UIDescription_" + code + ".json";

    UIDescriptionInterface obj = new GsonIO(UI_DESCRIPTION, UIDescriptionObject.class);

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        obj.update(_obj, _jsonFilePath);
    }

    public void update(LocalStorage _obj) {
        obj.update(_obj, UI_DESCRIPTION);
    }

    @Override
    public LocalStorage getData() {
        return obj.getData();
    }
}
