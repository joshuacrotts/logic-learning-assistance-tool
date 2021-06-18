package com.llat.input.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PDFApiTester {

    private static final String TEX_URL = "https://latex.ytotech.com/builds/sync";

    private static final String OUTPUT_PATH = "latex_tester.pdf";

    public static void main(String[] args) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(TEX_URL).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/pdf");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        JsonObject mainObj = new JsonObject();
        JsonArray resourcesArray = new JsonArray();
        JsonObject mainAndContentFields = new JsonObject();

        mainAndContentFields.addProperty("main", true);
        mainAndContentFields.addProperty("content","\\documentclass[12pt]{article}\n" +
                "\\begin{document}\n" +
                "Hello world!\n" +
                "$Hello world!$ %math mode \n" +
                "\\end{document}");
        resourcesArray.add(mainAndContentFields);
        mainObj.addProperty("compiler", "pdflatex");
        mainObj.add("resources", resourcesArray);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(mainObj.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        System.out.println(connection.getResponseCode());
        // Plan is to read the data back from the server as a pdf...
        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, Paths.get(OUTPUT_PATH), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
