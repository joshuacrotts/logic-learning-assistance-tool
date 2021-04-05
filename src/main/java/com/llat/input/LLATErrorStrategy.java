package com.llat.input;

import com.llat.LLATLexer;
import org.antlr.v4.runtime.*;

/**
 * @TODO...
 */
public class LLATErrorStrategy extends DefaultErrorStrategy {

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        super.recover(recognizer, e);
        Token t = e.getOffendingToken();
        int tokenIdx = t.getType();
        String offendingToken = t.getText();
        String errorMsg = "";

        // Check to see what type of error it was.
        if (e instanceof NoViableAltException) {
            switch(tokenIdx) {
                case LLATLexer.ATOM: errorMsg = "Are you missing an open parenthesis somewhere?"; break;
                case LLATLexer.OPEN_PAREN: errorMsg = "Are you missing a closing parenthesis somewhere?."; break;
                case LLATLexer.EOF: errorMsg = "Are you missing a closing parenthesis?"; break;
                default: errorMsg = "Unknown character: " + offendingToken;
            }
        } else if (e instanceof InputMismatchException){
            errorMsg = "Unknown character: " + offendingToken ;
        } else {
            errorMsg = "Unknown error.";
        }

        LLATErrorListener.syntaxError(-1, t.getCharPositionInLine() + 1, String.format("right before %s - %s", t.getText(), errorMsg));
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        return super.recoverInline(recognizer);
    }
}
