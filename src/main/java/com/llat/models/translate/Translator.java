package com.llat.models.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Translator {

    public static final String GOOGLE_TRANSLATE_API = "https://script.google.com/macros/s/AKfycbxMv7Hc_lCWSvOXU1jbiDvAXv36PJRVdOII3z-3kH1H5KRjA4tkyt5p5Psqz52UUw2p/exec";

    public static void main(String[] args) throws IOException {
        List<String> textList = new ArrayList<>();
        textList.add("I love");
        textList.add("spicy");
        textList.add("food");

        List<String> result = translateList("en", "ar", textList);
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = GOOGLE_TRANSLATE_API +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static List<String> translateList(String langFrom, String langTo, List<String> textList) throws IOException {
        List<String> transList = new ArrayList<String>();

        for (int i = 0; i < textList.size(); i++) {
            transList.add(translate(langFrom, langTo, textList.get(i)));
        }
        return transList;
    }
}
