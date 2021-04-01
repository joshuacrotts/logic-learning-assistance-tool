package com.llat.models.gson;

import com.google.gson.Gson;
import com.llat.main.App;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.credentials.CredentialsInterface;
import com.llat.models.localstorage.settings.SettingsInterface;
import com.llat.models.localstorage.uidescription.UIDescriptionInterface;

import java.io.*;
import java.lang.reflect.Type;

/**
 *
 */
public class GsonIO implements UIDescriptionInterface, SettingsInterface, CredentialsInterface {

    private static Gson gson = new Gson();

    /**
     *
     */
    private String json;
    private Type aClass;

    public GsonIO(String _jsonFileName, Type _objectClass) {
        this.json = _jsonFileName;
        this.aClass = _objectClass;
    }

    public GsonIO(){
    }
    /**
     * This method is reading a giving file name that is stored in the `resources` folder and return it as
     * a string.
     */
    public static String readJsonFile(String _fileName) {
        String result = "";
        try {
            String var = App.class.getResource("/" + _fileName).getPath().replace("\\", "/").replaceAll("%20", " ");
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

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        String filePath = App.class.getResource("/" + _jsonFilePath).getPath();
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(_obj, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LocalStorage getData() {
        String jsonString = readJsonFile(json);
        LocalStorage localStorage = gson.fromJson(jsonString, aClass);
        return localStorage;
    }
}
