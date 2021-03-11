package com.llat.input.tests;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import com.llat.LLATLexer;
import com.llat.LLATParser;
import com.llat.algorithms.*;
import com.llat.input.LLATErrorListener;
import com.llat.input.LLATParserListener;
import com.llat.models.treenode.WffTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Basic parser tester. Has a main() so can be run from the command line, with
 * one optional command line parameter. If provided, this is a filename to use
 * for input. Otherwise, input is taken from standard input. More importantly,
 * the parseFromFile and parseFromStdin methods are public static methods and
 * can be called from automated tests. They return the
 * LLATParserListener object that was used in parsing, giving
 * access to both the final syntax tree and the final symbol table.
 *
 * @author Steve Tate (srtate@uncg.edu)
 * @modified Joshua Crotts
 * @date 2/20/2021
 */
public class ParserTest {

    /**
     * Runs the parser and syntax tree constructor for the provided input stream.
     * The returned object can be used to access the syntax tree and the symbol table
     * for either futher processing or for checking results in automated tests.
     *
     * @param input an initialized CharStream
     */
    private static LLATParserListener parseStream(CharStream input) {
        // "input" is the character-by-character input - connect to lexer
        LLATLexer lexer = new LLATLexer(input);
        LLATErrorListener errorListener = new LLATErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        // Connect token stream to lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Connect parser to token stream
        LLATParser parser = new LLATParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        // Now do the parsing, and walk the parse tree with our listeners
        ParseTreeWalker walker = new ParseTreeWalker();
        LLATParserListener compiler = new LLATParserListener(parser);
        walker.walk(compiler, tree);

        return compiler;
    }

    /**
     * Public static method to run the parser on an input file.
     *
     * @param fileName the name of the file to use for input
     */
    public static LLATParserListener parseFromFile(String fileName) {
        try {
            return parseStream(CharStreams.fromFileName(fileName));
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                System.err.println("Could not open file " + fileName);
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Public static method to run the parser on the standard input stream.
     */
    public static LLATParserListener parseFromStdin() {
        try {
            return parseStream(CharStreams.fromStream(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Command line interface -- one argument is filename, and if omitted then input
     * is taken from standard input.
     *
     * @param argv command line arguments
     */
    public static void main(String[] argv) {
        LLATParserListener parser;
        if (argv.length > 1) {
            System.err.println("Can provide at most one command line argument (an input filename)");
            return;
        } else if (argv.length == 1) {
            parser = parseFromFile(argv[0]);
        } else {
            parser = parseFromStdin();
        }

        // For now, the errors are just printed in the tester class - if
        // JUnit is integrated, these should be removed so they align with
        // the tests.
        LLATErrorListener.printErrors();
        LLATErrorListener.printWarnings();

        WffTree result = null;
        if (parser != null) {
            result = parser.getSyntaxTree();
        }

        if (result != null) {
            result.printSyntaxTree();
            System.out.println("Main operator: " + MainOperatorDetector.get(result));
            System.out.println("Bound variables: " + BoundVariableDetector.get(result));
            System.out.println("Free variables: " + FreeVariableDetector.get(result));
            System.out.println("Open sentence: " + OpenSentenceDetector.get(result));
            System.out.println("Closed sentence: " + ClosedSentenceDetector.get(result));
            System.out.println("Ground sentence: " + GroundSentenceDetector.get(result));
            System.out.println("Truth Table: ");
            TruthTableGenerator.get(result);
        }
    }
}
