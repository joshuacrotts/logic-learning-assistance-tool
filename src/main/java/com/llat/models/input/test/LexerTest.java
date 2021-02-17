package com.llat.models.input.test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import com.llat.LLATLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;


/**
 * Basic Lexical Analyzer tester. Has a main() so can be run from the command
 * line, with one optional command line parameter. If provided, this is a
 * filename to user for input. Otherwise, input is taken from standard input.
 * More importantly, the lexFromFile and lexFromStdin methods are public static
 * methods and can be called from automated tests.
 * <p>
 * The token print loop code is taken from the ANTLR "grun" tool, but omits
 * printing absolute character offsets, which are not portable across Windows
 * and Linux systems. This version should be portable, allowing the same
 * test cases to work, regardless of whether they are run on a Windows or a
 * Linux system.
 *
 * @author Steve Tate (srtate@uncg.edu)
 */

public class LexerTest {
    /**
     * Produces a "portable" string representation of the token and position.
     * This is necessary due to differences between Windows and Linux/Unix
     * end of line conventions, which makes the character positions of tokens
     * different, depending on what kind of system it is run on. The
     * difference between this output and the "grun" output is simply that
     * this one does not print absolute character token position offsets.
     *
     * @param lexer the recognizer (really the Lexer)
     * @param tok the CommonToken to be printed
     * @return the string representation
     */
    private static String portable(LLATLexer lexer, CommonToken tok) {
        String channelStr = "";
        int channel = tok.getChannel();
        if ( channel>0 ) {
            channelStr=",channel="+channel;
        }
        String txt = tok.getText();
        if ( txt!=null ) {
            txt = txt.replace("\n","\\n");
            txt = txt.replace("\r","\\r");
            txt = txt.replace("\t","\\t");
        }
        else {
            txt = "<no text>";
        }
        int type = tok.getType();
        String typeString = String.valueOf(type);
        if ( lexer!=null ) {
            typeString = lexer.getVocabulary().getDisplayName(type);
        }

        return  "[@"+tok.getTokenIndex()+","+"'"+txt+"',<"+typeString+">"
           +channelStr+","+tok.getLine()+":"+tok.getCharPositionInLine()+"]";
    }

    /**
     * Internal lexer tokenizer/print loop. Called from one of the public
     * methods after establishing a CharStream.
     *
     * @param input an initialized CharStream
     */
    private static void lexStream(CharStream input) {
        LLATLexer lexer = new LLATLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        for (Token tok : tokens.getTokens()) {
            if (tok instanceof CommonToken) {
                System.out.println(portable(lexer, (CommonToken) tok));
            } else {
                System.out.println(tok.toString());
            }
        }
    }

    /**
     * Public static method to run the lexical analyzer on an input file.
     *
     * @param fileName the name of the file to use for input
     */
    public static void lexFromFile(String fileName) {
        try {
            lexStream(CharStreams.fromFileName(fileName));
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                System.err.println("Could not open file " + fileName);
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * Public static method to run the lexical analyzer on the standard input
     * stream.
     */
    public static void lexFromStdin() {
        try {
            lexStream(CharStreams.fromStream(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Command line interface -- one argument is filename, and if omitted then
     * input is taken from standard input.
     *
     * @param argv command line arguments
     */
    public static void main(String[] argv) {
        if (argv.length > 1) {
            System.err.println("Can provide at most one command line argument (an input filename)");
        } else if (argv.length == 1) {
            lexFromFile(argv[0]);
        } else {
            lexFromStdin();
        }
    }
}