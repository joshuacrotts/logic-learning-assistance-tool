package com.llat.models.localstorage.settings.language;

import com.llat.models.localstorage.settings.ItemObject;
import com.llat.models.localstorage.settings.SettingsObject;

public class LanguageObject extends ItemObject {

    /**
     *
     */
    private static final String[] RIGHT_TO_LEFT_LANGUAGES = {"ar", "az", "dv", "fa", "he", "ku", "ur"};

    /**
     * @return
     */
    public static boolean isUsingRightToLeftLanguage() {
        for (String code : RIGHT_TO_LEFT_LANGUAGES) {
            if (SettingsObject.languageObject.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param _obj
     * @return
     */
    public static boolean isRightToLeftLanguage(LanguageObject _obj) {
        for (String code : LanguageObject.RIGHT_TO_LEFT_LANGUAGES) {
            if (_obj.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }
}
