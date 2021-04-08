package com.llat.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GoogleTranslatorAPI implements TranslatorInterface {

    /**
     *
     */
    public static final String GOOGLE_TRANSLATE_API = "https://script.google.com/macros/s/AKfycbwZFMtrXdXWwLzdj7XgOshinFaDDC6GGPcffhZamyPrsgek1h0SLNk297TaNGn9OuE/exec";

    /**
     *
     */
    public String translate(String langFrom, String langTo, String text) {
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

        assert response != null;
        return response.toString();
    }

    /**
     *
     */
    public List<String> translateList(String langFrom, String langTo, List<String> textList) {
        List<String> transList = new ArrayList<String>();

        for (String s : textList) {
            transList.add(translate(langFrom, langTo, s));
        }
        return transList;
    }
}
