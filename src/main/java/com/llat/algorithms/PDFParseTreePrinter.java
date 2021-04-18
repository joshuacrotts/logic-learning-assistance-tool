package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 */
public class PDFParseTreePrinter extends PDFPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TREE_TEMPLATE = "src/main/resources/tex_parse_tree_template.tex";

    public PDFParseTreePrinter(WffTree _tree, String _outputFile) {
        super(_tree, _outputFile);
    }

    /**
     * Outputs the parse tree to a .tex file. The preamble information/template data is
     * copied to the output file first, then we recursively traverse the TruthTree object
     * calling the getTexLiteralCommand() methods on each node.
     */
    public void outputToFile() {
        try {
            this.setBufferedReader(new BufferedReader(new FileReader(TEX_TREE_TEMPLATE)));
            StringBuilder httpTex = new StringBuilder();

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                httpTex.append((char) ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Append the table code to this request.
            httpTex.append(this.getTexParseTree());
            httpTex.append("\n\\end{forest}\n\\end{document}\n");

            // Build the URL and HTTP request.
            String texURL = "https://latex.ytotech.com/builds/sync?content=";
            String paramURL = URLEncoder.encode(httpTex.toString(), "UTF-8");
            URL url = new URL(texURL + paramURL);
            PDFPrinter.downloadFile(url, getOutputFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private String getTexParseTree() {
        StringBuilder sb = new StringBuilder();
        this.getTexParseTreeHelper(this.getWffTree().getChild(0), sb, 0);
        return sb.toString();
    }

    /**
     * @param _tree
     * @param _sb
     * @param _indent
     */
    private void getTexParseTreeHelper(WffTree _tree, StringBuilder _sb, int _indent) {
        _sb.append("\t".repeat(_indent));
        _sb.append("[");
        _sb.append(_tree.getTexParseCommand());
        for (WffTree ch : _tree.getChildren()) {
            // The parse tree prints predicates as how they are e.g. Pxyz instead of branching
            // off three times for each variable/constant.
            if (!_tree.isPredicate()) {
                _sb.append("\n");
                this.getTexParseTreeHelper(ch, _sb, _indent + 1);
            }
        }
        _sb.append("\n");
        _sb.append("\t".repeat(_indent));
        _sb.append("]");
    }
}
