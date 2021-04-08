package com.llat.models.gson;

import com.google.gson.Gson;
import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.credentials.CredentialsInterface;
import com.llat.models.localstorage.settings.SettingsInterface;
import com.llat.models.localstorage.uidescription.TranslateUIDO;
import com.llat.models.localstorage.uidescription.UIDescriptionInterface;
import com.llat.models.localstorage.uidescription.UIDescriptionObject;

import java.io.*;
import java.lang.reflect.Type;

/**
 *
 */
public class GsonIO implements UIDescriptionInterface, SettingsInterface, CredentialsInterface {

    private final static String RESOURCES_PATH = "src/main/resources/";
    private final static String DEFAULT_UIDO_FILE = "UID/UIDescription_en.json";
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

    public GsonIO() {
    }

    /**
     * This method is reading a giving file name that is stored in the `resources` folder and return it as
     * a string.
     */
    public static String readJsonFile(String _fileName) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(RESOURCES_PATH + _fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (NullPointerException | FileNotFoundException e) {
            GsonIO g = new GsonIO(DEFAULT_UIDO_FILE, UIDescriptionObject.class);

            // create the missing file
            g.createMissingFile(_fileName);
            // language code
            String code = g.getLanguageFromFileName(_fileName);

            // translate it
            TranslateUIDO tuido = new TranslateUIDO();
            UIDescriptionObject obj = (UIDescriptionObject) g.getData();
            obj = tuido.translateUIDO(obj, code);
            g.update(obj, "UID/UIDescription" + "_" + code + ".json");
            try {
                BufferedReader br = new BufferedReader(new FileReader(RESOURCES_PATH + _fileName));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                result = sb.toString();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        String filePath = null;
        try {

//            filePath = App.class.getResource("/" + _jsonFilePath).getPath();
            filePath = new File(RESOURCES_PATH + _jsonFilePath).getPath();
            System.out.println(filePath);
        } catch (NullPointerException e) {
            System.out.println("File is not exist. Empty file will be generated");
            createMissingFile(_jsonFilePath);
        }
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(_obj, writer);
            writer.close();
        } catch (NullPointerException | IOException e) {
            try {
                Writer writer = new FileWriter(RESOURCES_PATH + _jsonFilePath);
                gson.toJson(_obj, writer);
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public LocalStorage getData() {
        String jsonString = readJsonFile(json);
        LocalStorage localStorage = gson.fromJson(jsonString, aClass);
        return localStorage;
    }

    private String getLanguageFromFileName(String fileName) {

        return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
    }

    private void createMissingFile(String _path) {
        File f = new File(RESOURCES_PATH, _path);
        try {
            if (f.createNewFile()) {
                System.out.println("File has been created.");
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
