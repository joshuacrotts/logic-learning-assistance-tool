package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;

import java.io.*;

public class TexTruthTreePrinter extends TexPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TREE_TEMPLATE = "src/main/resources/tex_truth_tree_template.tex";

    /**
     * Truth tree to print.
     */
    private final TruthTree TRUTH_TREE;

    public TexTruthTreePrinter(TruthTree _tree, String _outputFile) {
        super(_tree.getWff(), _outputFile);
        this.TRUTH_TREE = _tree;
    }

    /**
     * Outputs the truth tree to a .tex file. The preamble information/template data is
     * copied to the output file first, then we recursively traverse the TruthTree object
     * calling the getTexCommand() methods on each node.
     */
    public void outputToFile() {
        try {
            this.setBufferedReader(new BufferedReader(new FileReader(TEX_TREE_TEMPLATE)));
            this.setBufferedWriter(new BufferedWriter(new FileWriter(this.getOutputFile())));

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                this.getBufferedWriter().write(ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Now print out the tree.
            this.getBufferedWriter().write(this.TRUTH_TREE.getTexTree());
            this.getBufferedWriter().write("\n\\end{forest}\n\\end{document}\n");
            this.getBufferedWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
