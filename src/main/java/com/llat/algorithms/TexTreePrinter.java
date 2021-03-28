package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TexTreePrinter {

    private final TruthTree TRUTH_TREE;

    private final String OUTPUT_FILE;

    private BufferedWriter writer;

    public TexTreePrinter(TruthTree _tree, String _outputFile) {
        this.TRUTH_TREE = _tree;
        this.OUTPUT_FILE = _outputFile;
        try {
            this.writer = new BufferedWriter(new FileWriter(this.OUTPUT_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
