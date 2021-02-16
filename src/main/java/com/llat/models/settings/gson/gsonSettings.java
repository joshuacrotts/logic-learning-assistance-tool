package com.llat.models.settings.gson;

import com.google.gson.Gson;
import com.llat.main.App;
import com.llat.models.settings.SettingsInterface;
import com.llat.models.settings.SettingsObject;

import java.io.*;

;

public class gsonSettings implements SettingsInterface {


    public static final String SETTINGS_JSON = "settings.json";
    public static Gson gson = new Gson();

    @Override
    public void update(SettingsObject settingsObject) {
        String filePath = App.class.getResource("/" + SETTINGS_JSON).getPath();
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(settingsObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SettingsObject getData() {
        Gson gson = new Gson();
        String jsonString = readJsonFile(SETTINGS_JSON);
        SettingsObject setting = gson.fromJson(jsonString, SettingsObject.class);
        return setting;
    }

    /*
    This method is reading a giving file name that is stored in the `resources` folder and return it as
    a string.
     */
    public static String readJsonFile(String _fileName) {
        String result = "";
        try {
            String var = App.class.getResource("/" + _fileName).getPath();
            BufferedReader br = new BufferedReader(new FileReader(var));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
