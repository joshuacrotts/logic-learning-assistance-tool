package com.llat.models.localstorage.settings;

import com.llat.models.localstorage.uidescription.UIObject;
import com.llat.models.localstorage.uidescription.UIObjectAdaptor;
import com.llat.models.localstorage.uidescription.UIObject;

public class Main {
    public static void main(String[] args) {
        SettingsAdaptor sa = new SettingsAdaptor();
        SettingsObject so = (SettingsObject) sa.getData();
        System.out.println(so);

        UIObject uio = (UIObject) new UIObjectAdaptor().getData();
//        so.getTheme().setApplied(uio.getSettingsView().getCategories().getAppearance().getTheme().getApplied());
        String str = so.getLanguage().getApplied().getCode();
    }
}
