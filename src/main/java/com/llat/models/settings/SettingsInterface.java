package com.llat.models.settings;

/**
 *
 */
public interface SettingsInterface {

    /**
     * @param settingsObject
     */
    void update(SettingsObject settingsObject);

    SettingsObject getData();
}
