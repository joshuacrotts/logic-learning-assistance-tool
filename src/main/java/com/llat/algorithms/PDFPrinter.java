package com.llat.algorithms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.llat.models.treenode.WffTree;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class PDFPrinter {

    /**
     * Tree to print to the output file.
     */
    private final WffTree WFF_TREE;

    /**
     * Output file to print to.
     */
    private final String OUTPUT_FILE;

    /**
     *
     */
    private DataOutputStream writer;

    /**
     * BufferedReader object to read the template in.
     */
    private BufferedReader reader;

    public PDFPrinter(WffTree _tree, String _outputFile) {
        this.WFF_TREE = _tree;
        this.OUTPUT_FILE = _outputFile;
    }

    /**
     * Removes the math mode environment from a string in TeX.
     *
     * @param _s - String to embed in the math mode removal command.
     * @return String inside of \mathrm{...}.
     */
    public static String removeMathMode(String _s) {
        return "\\mathrm{" + _s + "}";
    }

    /**
     * Outputs the algorithm or whatever subclass extends this to the output file.
     */
    public abstract void outputToFile();

    /**
     * Opens a URL stream and downloads the data from it to the fileName provided.
     *
     * @param _url - URL to download from.
     * @param _fileName - filename to save data from url to.
     * @param _payload - tex data to upload via POST.
     * @throws Exception - exception thrown if something occurs.
     */
    public static void downloadFile(String _url, String _fileName, String _payload) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(_url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/pdf");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(createJsonInput(_payload));
            os.flush();
        }

        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, Paths.get(_fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static byte[] createJsonInput(String _payload) {
        JsonObject mainObj = new JsonObject();
        JsonArray resourcesArray = new JsonArray();
        JsonObject mainAndContentFields = new JsonObject();

        mainAndContentFields.addProperty("main", true);
        mainAndContentFields.addProperty("content",_payload);
        resourcesArray.add(mainAndContentFields);
        mainObj.addProperty("compiler", "pdflatex");
        mainObj.add("resources", resourcesArray);
        return mainObj.toString().getBytes(StandardCharsets.UTF_8);
    }

    protected BufferedReader getBufferedReader() {
        return this.reader;
    }

    protected void setBufferedReader(BufferedReader _reader) {
        this.reader = _reader;
    }

    protected DataOutputStream getOutputStream() {
        return this.writer;
    }

    protected void setOutputStream(DataOutputStream _writer) {
        this.writer = _writer;
    }

    protected String getOutputFile() {
        return this.OUTPUT_FILE;
    }

    protected WffTree getWffTree() {
        return this.WFF_TREE;
    }

}
