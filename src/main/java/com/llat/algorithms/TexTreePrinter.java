package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;

import java.io.*;

public class TexTreePrinter {

    /**
     * Truth tree to print to the output file.
     */
    private final TruthTree TRUTH_TREE;

    /**
     * Output file to print to.
     */
    private final String OUTPUT_FILE;

    /**
     * BufferedWriter object.
     */
    private BufferedWriter writer;

    /**
     * BufferedReader object to read the template in.
     */
    private BufferedReader reader;

    public TexTreePrinter(TruthTree _tree, String _outputFile) {
        this.TRUTH_TREE = _tree;
        this.OUTPUT_FILE = _outputFile;
        try {
            this.writer = new BufferedWriter(new FileWriter(this.OUTPUT_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void outputToFile() {
        try {
            this.reader = new BufferedReader(new FileReader("src/main/resources/tex_template.tex"));
            this.writer = new BufferedWriter(new FileWriter(this.OUTPUT_FILE));

            // First copy the template over.
            int ch = this.reader.read();
            while (ch != -1) {
                this.writer.write(ch);
                ch = this.reader.read();
            }
            this.reader.close();

            // Now print out the tree.
            this.writer.write(this.TRUTH_TREE.getTexTree());
            this.writer.write("\n\\end{forest}\n\\end{document}\n");
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
