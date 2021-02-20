package com.llat.input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * This class can be added to either the lexer or the parser error reporting
 * chains (or both). All it does is keep track of whether an error was detected,
 * so after parsing you can call sawError() to see if there was a problem.
 *
 * @author Joshua Crotts
 */
public class LLATErrorListener extends BaseErrorListener {

    /**
     * Set of all compiler errors generated while parsing.
     */
    private static Set<Message> errors = new HashSet<>();

    /**
     * Set of all warning errors generated while parsing.
     */
    private static Set<Message> warnings = new HashSet<>();

    /**
     * Keeps track of whether we have encountered an error or not.
     */
    private static boolean gotError = false;

    /**
     * Keeps track of whether we have encountered a warning or not.
     */
    private static boolean gotWarning = false;

    public LLATErrorListener() {
        super();
    }

    /**
     * Prints an error message to the console with the line and column number
     * specified by the ParserRuleContext. The error flag is also set.
     *
     * @param ctx
     * @param errorMsg
     */
    public static void syntaxError(ParserRuleContext ctx, String errorMsg) {
        LLATErrorListener.gotError = true;
        int lineNo = -1;
        int colNo = -1;

        if (ctx != null) {
            lineNo = ctx.start.getLine();
            colNo = ctx.start.getCharPositionInLine();
        } else {
            throw new IllegalArgumentException(
                    "Internal compiler error - ParserRuleContext cannot be null in ErrorListener.");
        }

        LLATErrorListener.errors.add(new Message(errorMsg, lineNo, colNo));
    }

    /**
     * Prints an warning message to the console with the line and column number
     * specified by the ParserRuleContext.
     *
     * @param ctx
     * @param errorMsg
     *
     * @return void.
     */
    public static void syntaxWarning(ParserRuleContext ctx, String warningMsg) {
        LLATErrorListener.gotWarning = true;
        int lineNo = -1;
        int colNo = -1;

        if (ctx != null) {
            lineNo = ctx.start.getLine();
            colNo = ctx.start.getCharPositionInLine();
        } else {
            throw new IllegalArgumentException(
                    "Internal compiler error - ParserRuleContext cannot be null in ErrorListener.");
        }

        LLATErrorListener.warnings.add(new Message(warningMsg, lineNo, colNo));
    }

    /**
     * Prints error messages generated through parsing the syntax tree to standard
     * error.
     *
     * @param void.
     *
     * @return void.
     */
    public static void printErrors() {
        List<Message> errorList = new ArrayList<Message>(LLATErrorListener.errors);
        errorList.sort(Comparator.comparing(Message::getLineNo).thenComparing(Message::getColNo));
        System.err.print("ERRORS(" + LLATErrorListener.errors.size() + "):\n");
        for (Message error : errorList) {
            System.err.println(error);
        }
    }

    /**
     * Prints warning messages generated through parsing the syntax tree to standard
     * out.
     *
     * @param void.
     *
     * @return void.
     */
    public static void printWarnings() {
        List<Message> warningList = new ArrayList<Message>(LLATErrorListener.warnings);
        warningList.sort(Comparator.comparing(Message::getLineNo).thenComparing(Message::getColNo));
        System.out.print("WARNINGS(" + LLATErrorListener.warnings.size() + "):\n");
        for (Message warning : warningList) {
            System.out.println(warning);
        }
    }

    /**
     * Was an error encountered?
     *
     * @return true if an error was seen.
     */
    public static boolean sawError() {
        return gotError;
    }

    /**
     * Was a warning encountered? This probably serves little use.
     *
     * @return true if a warning was seen.
     */
    public static boolean sawWarning() {
        return gotWarning;
    }

    /**
     * Since this is a static error listener, we need to reset the warnings and
     * errors each time we use this in a unit testing environment or we'll have
     * false positives.
     */
    public static void reset() {
        LLATErrorListener.warnings.clear();
        LLATErrorListener.errors.clear();
        LLATErrorListener.gotError = false;
        LLATErrorListener.gotWarning = false;
    }

    /**
     * This is the syntaxError method from the BaseErrorListener ANTLR class. We have
     * overridden it to set the error flag and add a new Message to our running set of
     * objects.
     */
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int col, String errorMsg,
                            RecognitionException e) {
        gotError = true;
        LLATErrorListener.errors.add(new Message(errorMsg, line, col));
    }

    /**
     *
     * @author joshuacrotts
     */
    private static class Message {
        private final String text;
        private final int lineNo;
        private final int colNo;

        public Message(String text, int lineNo, int colNo) {
            this.text = text;
            this.lineNo = lineNo;
            this.colNo = colNo;
        }

        @Override
        public boolean equals(Object msg) {
            Message oMsg = (Message) msg;
            return this.text.equals(oMsg.text) && this.lineNo == oMsg.lineNo && this.colNo == oMsg.colNo;
        }

        @Override
        public int hashCode() {
            return this.text.hashCode() + lineNo + colNo;
        }

        public int getLineNo() {
            return this.lineNo;
        }

        public int getColNo() {
            return this.colNo;
        }

        @Override
        public String toString() {
            return "line " + lineNo + ":" + colNo + " " + text;
        }
    }
}