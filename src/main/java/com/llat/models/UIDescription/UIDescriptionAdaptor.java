package com.llat.models.UIDescription;

import com.llat.models.Gson.GsonIO;
import com.llat.models.Gson.GsonObject;
import com.llat.models.settings.SettingsObject;

public class UIDescriptionAdaptor implements UIDescriptionInterface{
    private static final String UIDescription = "UIDescription.json";

    private Object UIObj;
    UIDescriptionInterface obj = (UIDescriptionInterface) new GsonIO(UIDescription, UIObj, UIDescriptionObject.class);


    @Override
    public void update(Object _obj, String _jsonFilePath) {
        obj.update(_obj, _jsonFilePath);
    }

    public void update(Object _obj) {
        obj.update(_obj, UIDescription);
    }
    @Override
    public GsonObject getData() {
        return obj.getData();
    }
}
