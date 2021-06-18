package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class PDFPrinter {

    /**
     * Truth tree to print to the output file.
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
     * Opens a URL stream and downloads the data from it to the fileName provided.
     *
     * @param url      - URL to download from.
     * @param fileName - filename to save data from url to.
     * @throws Exception - exception thrown if something occurs.
     */
    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Outputs the algorithm or whatever subclass extends this to the output file.
     */
    public abstract void outputToFile();

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
