package com.llat.input.tests;

import com.llat.LLATLexer;
import com.llat.LLATParser;
import com.llat.algorithms.*;
import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.*;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.input.LLATErrorListener;
import com.llat.input.LLATErrorStrategy;
import com.llat.input.LLATParserAdapter;
import com.llat.input.LLATParserListener;
import com.llat.models.treenode.WffTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;

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
 * @date 3/30/2021
 */
public class ParserTest {

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
     * Command line interface -- one argument is filename, and if omitted then input
     * is taken from standard input.
     *
     * @param argv command line arguments
     */
    public static void main(String[] argv) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<WffTree> resultList = LLATParserAdapter.getAbstractSyntaxTree(reader.readLine());

        if (resultList.size() == 1) {
            WffTree result = resultList.get(0);
            result.printSyntaxTree();
            BaseTruthTreeGenerator truthTreeGenerator;
            if (result.isPredicateWff()) {
                System.out.println("Bound variables: " + new BoundVariableDetector(result).get());
                System.out.println("Free variables: " + new FreeVariableDetector(result).get());
                System.out.println("Open sentence: " + new OpenSentenceDeterminer(result).get());
                System.out.println("Closed sentence: " + new ClosedSentenceDeterminer(result).get());
                System.out.println("Ground sentence: " + new GroundSentenceDeterminer(result).get());
                truthTreeGenerator = new PredicateTruthTreeGenerator(result);
            } else {
                truthTreeGenerator = new PropositionalTruthTreeGenerator(result);
            }
            // Generate the truth tree and print it to the console.
            TruthTree truthTree = truthTreeGenerator.get();
            System.out.println("Truth Tree: " + truthTreeGenerator.print(truthTree));

            // Print the tree in LaTeX.
            TexTreePrinter texTreePrinter = new TexTreePrinter(truthTree, "latex_tree.tex");
            texTreePrinter.outputToFile();

            // Display the main operator.
            System.out.println("Main operator: " + new MainOperatorDetector(result).get());

            // Determine if it's a tautology.
            LogicalTautologyDeterminer tautologyDet = new LogicalTautologyDeterminer(result);
            System.out.println("Logical Tautology: " + tautologyDet.isTautology());

            // Determine if it's contingent.
            LogicallyContingentDeterminer consistentDet = new LogicallyContingentDeterminer(result);
            System.out.println("Logical Contingent: " + consistentDet.isContingent());
        } else {
            // Pull the two children from their root.
            WffTree ch1 = resultList.get(0);
            WffTree ch2 = resultList.get(1);
            LogicallyEquivalentDeterminer logDet = new LogicallyEquivalentDeterminer(ch1, ch2);
            System.out.println("Logically Equivalent: " + logDet.isEquivalent());

            LogicallyConsistentDeterminer consistentDet = new LogicallyConsistentDeterminer(ch1, ch2);
            System.out.println("Logically Consistent: " + consistentDet.isConsistent());

            LogicallyContradictoryDeterminer contradictoryDet = new LogicallyContradictoryDeterminer(ch1, ch2);
            System.out.println("Logically Contradictory: " + contradictoryDet.isContradictory());

            LogicallyContraryDeterminer contraryDet = new LogicallyContraryDeterminer(ch1, ch2);
            System.out.println("Logically Contrary: " + contraryDet.isContrary());

            LogicallyImpliedDeterminer impliedDet = new LogicallyImpliedDeterminer(ch1, ch2);
            System.out.println("Logically Implied: " + impliedDet.isImplied());
        }
    }

    /**
     * Runs the parser and syntax tree constructor for the provided input stream.
     * The returned object can be used to access the syntax tree and the symbol table
     * for either further processing or for checking results in automated tests.
     *
     * @param input an initialized CharStream
     */
    private static LLATParserListener parseStream(CharStream input) {
        // "input" is the character-by-character input - connect to lexer
        LLATLexer lexer = new LLATLexer(input);
        LLATErrorListener errorListener = new LLATErrorListener();
        LLATErrorStrategy errorStrategy = new LLATErrorStrategy();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        // Connect token stream to lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Connect parser to token stream
        LLATParser parser = new LLATParser(tokens);
        parser.removeErrorListeners();
        parser.setErrorHandler(errorStrategy);
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        // Now do the parsing, and walk the parse tree with our listeners
        ParseTreeWalker walker = new ParseTreeWalker();
        LLATParserListener compiler = new LLATParserListener(parser);
        walker.walk(compiler, tree);

        return compiler;
    }
}
