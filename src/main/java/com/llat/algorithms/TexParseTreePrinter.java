package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.io.*;

/**
 *
 */
public class TexParseTreePrinter extends TexPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TREE_TEMPLATE = "src/main/resources/tex_parse_tree_template.tex";

    public TexParseTreePrinter(WffTree _tree, String _outputFile) {
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
            this.setBufferedWriter(new BufferedWriter(new FileWriter(this.getOutputFile())));

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                this.getBufferedWriter().write(ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Now print out the tree.
            this.getBufferedWriter().write(this.getTexParseTree());
            this.getBufferedWriter().write("\n\\end{forest}\n\\end{document}\n");
            this.getBufferedWriter().close();
        } catch (IOException e) {
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
