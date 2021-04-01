package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;

public class UIDescriptionAdaptor implements UIDescriptionInterface {

    private static final String UI_DESCRIPTION = "UIDescription.json";

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
