package com.llat.algorithms.propositional;

import com.llat.algorithms.TexPrinter;
import com.llat.models.treenode.WffTree;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public final class TexTablePrinter extends TexPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TABLE_TEMPLATE = "src/main/resources/tex_truth_table_template.tex";

    public TexTablePrinter(WffTree _tree, String _outputFile) {
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
        try {
            this.setBufferedReader(new BufferedReader(new FileReader(TEX_TABLE_TEMPLATE)));
            this.setBufferedWriter(new BufferedWriter(new FileWriter(this.getOutputFile())));

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                this.getBufferedWriter().write(ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Now print out the tree.
            TruthTableGenerator ttg = new TruthTableGenerator(this.getWffTree());
            LinkedHashSet<WffTree> postOrderTraversal = ttg.postorder();
            this.getBufferedWriter().write(this.getTexTable(postOrderTraversal));

            // Output the closing latex commands.
            this.getBufferedWriter().write("\n\\end{array}\n$\n]\n\\end{forest}\n\\end{document}\n");
            this.getBufferedWriter().close();
        } catch (IOException e) {
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
        sb.append("$\\begin{array}{");
        sb.append("C|".repeat(_set.size() - 1));
        sb.append("C}\n");

        // First print the headers.
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i).getTexCommand());
            sb.append(" & ");
        }

        // Output the hline separator.
        sb.append(list.get(list.size() - 1).getTexCommand());
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
}
