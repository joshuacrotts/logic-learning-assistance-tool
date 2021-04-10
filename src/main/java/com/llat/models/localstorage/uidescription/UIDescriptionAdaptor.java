package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;

public class UIDescriptionAdaptor implements UIDescriptionInterface {

    /**
     *
     */
    private final SettingsAdaptor sa = new SettingsAdaptor();

    /**
     *
     */
    private final SettingsObject so = (SettingsObject) this.sa.getData();

    /**
     *
     */
    private final String code = this.so.getLanguage().getApplied().getCode();

    /**
     *
     */
    private final String UI_DESCRIPTION = "UID/UIDescription_" + this.code + ".json";

    /**
     *
     */
    private final UIDescriptionInterface obj = new GsonIO(this.UI_DESCRIPTION, UIDescriptionObject.class);

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
