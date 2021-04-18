package com.llat.algorithms.propositional;

import com.llat.algorithms.PDFPrinter;
import com.llat.algorithms.TexPrinter;
import com.llat.models.treenode.WffTree;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public final class PDFTablePrinter extends PDFPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TABLE_TEMPLATE = "src/main/resources/tex_truth_table_template.tex";

    public PDFTablePrinter(WffTree _tree, String _outputFile) {
        super(_tree, _outputFile);
    }

    /**
     * Outputs the truth table for a propositional logic formula in LaTeX to a file.
     * We first create a TruthTableGenerator algorithm, then traverse its contents
     * for the truth values. These [nodes] are inserted in post-order, so atomic/
     * non-complex sentences are inserted first. The ordering isn't guaranteed to
     * put the plain atoms first, but that's a feature for another time.
     */
    public void outputToFile() {
        // First make sure that we actually can generate this tree.
        TruthTableGenerator ttg = new TruthTableGenerator(this.getWffTree());
        if (!ttg.getTruthTable()) {
            System.err.println("Could not create truth table.");
            return;
        }
        LinkedHashSet<WffTree> postOrderTraversal = ttg.postorder();

        try {
            this.setBufferedReader(new BufferedReader(new FileReader(TEX_TABLE_TEMPLATE)));
            StringBuilder httpTex = new StringBuilder();

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                httpTex.append((char) ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Append the table code to this request.
            httpTex.append(this.getTexTable(postOrderTraversal));
            httpTex.append("\n\\end{tabular}\n\n\\end{document}\n");
            // Build the URL and HTTP request.
            String texURL = "https://latexonline.cc/compile?text=";
            String paramURL = URLEncoder.encode(httpTex.toString(), "UTF-8");
            System.out.println(texURL + paramURL);
            downloadFile(new URL("https://latexonline.cc/compile?text=%5Cdocumentclass%5Bborder%3D10pt%5D%7Bstandalone%7D%0D%0A%5Cusepackage%7Bamsmath%7D%0D%0A%5Cusepackage%7Barray%7D%0D%0A%0D%0A%5Cnewcommand%7B%5Cvarlnot%7D%7B%5Cmathord%7B%5Csim%7D%7D%0D%0A%5Cnewcommand%7B%5Cvarland%7D%7B%5Cmathbin%7B%5C%26%7D%7D%0D%0A%5Cnewcommand%7B%5Cvarliff%7D%7B%5Cleftrightarrow%7D%0D%0A%5Cnewcommand%7B%5Cdneg%7D%7B%5Cvarlnot%5Cvarlnot%7D%0D%0A%5Cnewcommand*%5Clif%7B%5Cmathbin%7B%5Cto%7D%7D%25%20added%20thanks%20to%20egreg%27s%20suggestion%0D%0A%5Cnewcommand%7B%5Cltrue%7D%7B%5Cmathrm%7Btrue%7D%7D%0D%0A%0D%0A%5Cbegin%7Bdocument%7D%0D%0A%5Cbegin%7Btabular%7D%7Bc%7Cc%7Cc%7D%0A%24%5Cmathrm%7BA%7D%24%20%26%20%24%5Cmathrm%7BB%7D%24%20%26%20%24(%5Cmathrm%7BA%7D%20%5Cmathbin%7B%5C%26%7D%20%5Cmathrm%7BB%7D)%24%5C%5C%0A%5Chline%0Atrue%20%26%20true%20%26%20true%20%5C%5C%20%0Atrue%20%26%20false%20%26%20false%20%5C%5C%20%0Afalse%20%26%20true%20%26%20false%20%5C%5C%20%0Afalse%20%26%20false%20%26%20false%20%5C%5C%20%0A%5Cend%7Btabular%7D%0A%0A%5Cend%7Bdocument%7D%0A&trackId=1618711349802"), "test.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds the truth table. Initializes the string builder with the column
     * sizes, headers, then populates the rows with the boolean values. We substitute
     * true for T and false for F.
     *
     * @param _set - set of WffTree nodes that are collected in postorder prior to
     *             this method.
     * @return String representation of the TeX file.
     */
    private String getTexTable(LinkedHashSet<WffTree> _set) {
        StringBuilder sb = new StringBuilder();
        LinkedList<WffTree> list = new LinkedList<>(_set);
        int rows = list.get(0).getTruthValues().size();

        // Print the preamble stuff.
        sb.append("\\begin{tabular}{");
        sb.append("c|".repeat(_set.size() - 1));
        sb.append("c}\n");

        // First print the headers.
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append("$" + list.get(i).getTexCommand() + "$");
            sb.append(" & ");
        }

        // Output the hline separator.
        sb.append("$" + list.get(list.size() - 1).getTexCommand() + "$");
        sb.append("\\\\\n\\hline\n");

        // Now print the truth values.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                sb.append(list.get(j).getTruthValues().get(i));
                sb.append(" & ");
            }
            // Output a new line on all rows except for the last.
            sb.append(list.get(list.size() - 1).getTruthValues().get(i));
            sb.append(" \\\\ ");
            if (i != rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
