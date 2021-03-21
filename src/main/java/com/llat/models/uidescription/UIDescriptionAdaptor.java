package com.llat.models.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.gson.GsonObject;

public class UIDescriptionAdaptor implements UIDescriptionInterface {

    private static final String UI_DESCRIPTION = "UIDescription.json";

    private Object UIObj;
    UIDescriptionInterface obj = (UIDescriptionInterface) new GsonIO(UI_DESCRIPTION, UIObj, UIDescriptionObject.class);

    @Override
    public void update(Object _obj, String _jsonFilePath) {
        obj.update(_obj, _jsonFilePath);
    }

    public void update(Object _obj) {
        obj.update(_obj, UI_DESCRIPTION);
    }

    @Override
    public GsonObject getData() {
        return obj.getData();
    }
}
