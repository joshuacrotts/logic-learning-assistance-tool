package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;

public class UIObjectAdaptor implements UIObjectInterface {
    SettingsAdaptor sa = new SettingsAdaptor();
    SettingsObject so = (SettingsObject) this.sa.getData();

    String code = this.so.getLanguage().getApplied().getCode();

    private final String UI_DESCRIPTION = "/UID/UIObject_" + this.code + ".json";

    UIObjectInterface obj = new GsonIO(this.UI_DESCRIPTION, UIObject.class);

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        this.obj.update(_obj, _jsonFilePath);
    }

    public void update(LocalStorage _obj) {
        this.obj.update(_obj, this.UI_DESCRIPTION);
    }

    @Override
    public LocalStorage getData() {
        return this.obj.getData();
    }
}
