package com.llat.models.gson;

import com.google.gson.Gson;
import com.llat.main.App;
import com.llat.models.uidescription.UIDescriptionInterface;
import com.llat.models.settings.SettingsInterface;
import com.llat.tools.ViewManager;

import java.io.*;
import java.lang.reflect.Type;

/**
 *
 */
public class GsonIO implements UIDescriptionInterface, SettingsInterface {

    public static Gson gson = new Gson();

    /**
     *
     */
    public String json;
    public Object obj;
    public Type aClass;

    public GsonIO(String _jsonFileName, Object _obj, Type _objectClass) {
        this.json = _jsonFileName;
        this.obj = _obj;
        this.aClass = _objectClass;
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
    public void update(Object _obj, String _jsonFilePath) {
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
    public GsonObject getData() {
        String jsonString = readJsonFile(json);
        Object x = gson.fromJson(jsonString, aClass);
        GsonObject<Object> gobj = new GsonObject<>(x);
        return gobj;
    }
}
