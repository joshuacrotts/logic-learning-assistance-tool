package translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GoogleTranslatorAPI implements TranslatorInterface{

    public static final String GOOGLE_TRANSLATE_API = "https://script.google.com/macros/s/AKfycbxMv7Hc_lCWSvOXU1jbiDvAXv36PJRVdOII3z-3kH1H5KRjA4tkyt5p5Psqz52UUw2p/exec";


    public String translate(String langFrom, String langTo, String text)  {
        // INSERT YOU URL HERE
        String urlStr = null;
        StringBuilder response = null;
        try {
            urlStr = GOOGLE_TRANSLATE_API +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;

        URL url = new URL(urlStr);
        response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public List<String> translateList(String langFrom, String langTo, List<String> textList)  {
        List<String> transList = new ArrayList<String>();

        for (int i = 0; i < textList.size(); i++) {
            transList.add(translate(langFrom, langTo, textList.get(i)));
        }
        return transList;
    }
}
