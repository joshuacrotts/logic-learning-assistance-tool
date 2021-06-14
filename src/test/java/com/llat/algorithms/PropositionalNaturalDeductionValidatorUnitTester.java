package com.llat.algorithms;

import com.llat.algorithms.models.NDWffTree;
import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.input.LLATParserListener;
import com.llat.input.tests.ParserTest;
import com.llat.models.treenode.WffTree;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNull;

public class PropositionalNaturalDeductionValidatorUnitTester {

    private static PropositionalNaturalDeductionValidator naturalDeductionValidator;

    /**
     * Helper function to count number of newlines in a string
     *
     * @param s the string
     * @return the number of newlines
     */
    private static int countNLs(String s) {
        if (s == null) return 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\n')
                count++;
        }
        return count;
    }

    /**
     * Compares to byte array token by token, where a "token" is either a
     * individual character. All whitespace is skipped over and not used
     * for the comparison, so the outputs can be formatted/spaced entirely
     * differently.
     *
     * @param got    the bytes printed out by the program under test
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
                            + expToken + "\" (line " + expLine + ")";
                    done = true;
                }
            } else {
                if (gotToken != null) {
                    result = "Got extra output: unexpected \"" + gotToken
                            + "\" (line " + gotLine + ")";
                }
                done = true;
            }
        }

        assertNull(result, result);
    }

    /**
     * The testing engine for a valid LLAT well-formed formula (which should parse and
     * produce a WffTree object). Both the input wff and the expected
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
        String inName = "tests/propositionalnd/" + testName + ".in";
        String expName = "tests/propositionalnd/" + testName + ".out";

        PrintStream origOut = System.out;
        PrintStream origErr = System.err;
        ByteArrayOutputStream captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        System.setErr(new PrintStream(captureOut));
        LLATParserListener parser = ParserTest.parseFromFile(inName);
        if (parser == null)
            throw new AssertionFailedError("Failed reading test input file " + inName);
        naturalDeductionValidator = new PropositionalNaturalDeductionValidator(parser.getSyntaxTrees());
        LinkedList<NDWffTree> ndArgs = naturalDeductionValidator.getNaturalDeductionProof();
        for (int i = 0; i < ndArgs.size(); i++) {
            NDWffTree wff = ndArgs.get(i);
            System.out.println((i + 1) + ": " + wff);
        }
        System.out.println("∴ " + ndArgs.getLast().getWffTree().getStringRep() + "\t\t■");
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

    @Test
    public void test001() {
        goodFileTest("test001");
    }

    @Test
    public void test002() {
        goodFileTest("test002");
    }

    @Test
    public void test003() {
        goodFileTest("test003");
    }

    @Test
    public void test004() {
        goodFileTest("test004");
    }

    @Test
    public void test005() {
        goodFileTest("test005");
    }

    @Test
    public void test006() {
        goodFileTest("test006");
    }

    @Test
    public void test007() {
        goodFileTest("test007");
    }

    @Test
    public void test008() {
        goodFileTest("test008");
    }

    @Test
    public void test009() {
        goodFileTest("test009");
    }

    @Test
    public void test010() {
        goodFileTest("test010");
    }

    @Test
    public void test011() {
        goodFileTest("test011");
    }

    @Test
    public void test012() {
        goodFileTest("test012");
    }

    @Test
    public void test013() {
        goodFileTest("test013");
    }

    @Test
    public void test014() {
        goodFileTest("test014");
    }

    @Test
    public void test015() {
        goodFileTest("test015");
    }

    @Test
    public void test016() {
        goodFileTest("test016");
    }

    @Test
    public void test017() {
        goodFileTest("test017");
    }

    @Test
    public void test018() {
        goodFileTest("test018");
    }

//    @Test
//    public void test019() {
//        goodFileTest("test019");
//    }
//
//    @Test
//    public void test020() {
//        goodFileTest("test020");
//    }
//
//    @Test
//    public void test021() {
//        goodFileTest("test021");
//    }
//
//    @Test
//    public void test022() {
//        goodFileTest("test022");
//    }
//
//    @Test
//    public void test023() {
//        goodFileTest("test023");
//    }
//
//    @Test
//    public void test024() {
//        goodFileTest("test024");
//    }
//
//    @Test
//    public void test025() {
//        goodFileTest("test025");
//    }
//
//    @Test
//    public void test026() {
//        goodFileTest("test026");
//    }
//
//    @Test
//    public void test027() {
//        goodFileTest("test027");
//    }
//    @Test
//    public void test028() {
//        goodFileTest("test028");
//    }
//
//    @Test
//    public void test029() {
//        goodFileTest("test029");
//    }
//
//    @Test
//    public void test030() {
//        goodFileTest("test030");
//    }
}
