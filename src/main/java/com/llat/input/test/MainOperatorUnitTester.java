package com.llat.input.test;

import com.llat.input.LLATParserListener;
import com.llat.models.treenode.WffTree;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing file for the parser. This tester is designed to run test cases
 * that are located in the "tests" subdirectory of the main project directory.
 * Valid LittleC programs (which should parse and produce a syntax tree) are
 * tested by method goodFileTest(), and invalid LittleC program (which should
 * produce null for the syntax tree, indicating an error) are tested by method
 * badFileInput(). See those methods for more information.
 */
public class MainOperatorUnitTester {

    /**
     * Helper function to count number of newlines in a string
     * @param s the string
     * @return the number of newlines
     */
    private static int countNLs(String s) {
        if (s == null) return 0;
        int count = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '\n')
                count++;
        }
        return count;
    }

    /**
     * Compares to byte array token by token, where a "token" is either a
     * C-style identifier, a number, or an individual character. All whitespace
     * is skipped over and not used for the comparison, so the outputs can
     * be formatted/spaced entirely differently.
     *
     * @param got the bytes printed out by the program under test
     * @param expect the expected output
     */
    private static void compare(byte[] got, byte[] expect) {
        String result = null;
        Scanner gotScanner = new Scanner(new ByteArrayInputStream(got));
        Scanner expScanner = new Scanner(new ByteArrayInputStream(expect));
        expScanner.useDelimiter("\\n");
        int gotLine = 1;
        int expLine = 1;

        Pattern tokPattern = Pattern.compile("([A-Za-z_][A-Za-z_0-9]*)|([0-9]+)|(.)");
        Pattern skipPattern = Pattern.compile("[ \\r\\t\\n]*");
        Pattern nlPattern = Pattern.compile("\\n");

        boolean done = false;
        while (!done) {
            String skipped = expScanner.findWithinHorizon(skipPattern, 1000);
            expLine += countNLs(skipped);
            String expToken = expScanner.findWithinHorizon(tokPattern, 1000);

            skipped = gotScanner.findWithinHorizon(skipPattern, 1000);
            gotLine += countNLs(skipped);
            String gotToken = gotScanner.findWithinHorizon(tokPattern, 1000);
            if (expToken != null) {
                if (gotToken != null) {
                    if (!expToken.equals(gotToken)) {
                        result = "Error. Got line " + gotLine + " has \"" + gotToken
                                + "\"; expected line " + expLine + " is \"" + expToken + "\"";
                        done = true;
                    }
                } else {
                    result = "Produced output ended too early - expected \""
                            +expToken+"\" (line "+expLine+")";
                    done = true;
                }
            } else {
                if (gotToken != null) {
                    result = "Got extra output: unexpected \""+gotToken
                            +"\" (line "+gotLine+")";
                }
                done = true;
            }
        }

        assertNull(result, result);
    }

    /**
     * The testing engine for a valid LittleC program (which should parse and
     * produce a LCSyntaxTree). Both the input LittleC program and the expected
     * syntax tree output file must be provided as files with ".in" and ".out"
     * extensions, respectively. Runs input file through the
     * ParserTest.parseFromFile() method, gets the syntax tree and calls the
     * user-written printSyntaxTree() method to get a text representation,
     * which is matched token-by-token with the expected output.
     *
     * @param testName the base name of the test case; files are stored in the
     *                 tests project directory, with ".in" and ".out"
     *                 extensions.
     */
    private static void goodFileTest(String testName) {
        String inName = "tests/" + testName + ".in";
        String expName = "tests/" + testName + ".out";

        PrintStream origOut = System.out;
        PrintStream origErr = System.err;
        ByteArrayOutputStream captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        System.setErr(new PrintStream(captureOut));
        LLATParserListener parser = ParserTest.parseFromFile(inName);
        if (parser == null)
            throw new AssertionFailedError("Failed reading test input file "+inName);
        WffTree syntaxTree = parser.getSyntaxTree();
        syntaxTree.printSyntaxTree();
        System.setErr(origErr);
        System.setOut(origOut);
        byte[] actual = captureOut.toByteArray();

        byte[] expected;
        try {
            expected = Files.readAllBytes(Paths.get(expName));
        } catch (IOException e) {
            throw new AssertionFailedError("Missing expected output file " + expName);
        }
        compare(actual, expected);
    }

    /**
     * The testing engine for a invalid LittleC program (the error should be
     * detected, resulting in null being returned for the syntax tree. Any
     * non-null result means the error was missed, so the test fails. Since
     * there is not supposed to be any output, only the input file (with
     * extension ".in") is required.
     *
     * @param testName the base name of the test case; files are stored in the
     *                 tests project directory, with an ".in" extensions.
     */
    private static void errorFileTest(String testName) {
        String inName = "tests/" + testName + ".in";

        PrintStream origOut = System.out;
        PrintStream origErr = System.err;
        ByteArrayOutputStream captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        System.setErr(new PrintStream(captureOut));
        LLATParserListener parser = ParserTest.parseFromFile(inName);
        if (parser == null)
            return;

        WffTree result = parser.getSyntaxTree();
        System.setErr(origErr);
        System.setOut(origOut);
        if (result != null)
            throw new AssertionFailedError("Did not catch the error in input "+inName);
    }

    @Test
    public void testOne() {
        goodFileTest("test001");
    }
}
