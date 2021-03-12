package com.llat.input;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;

/**
 * @TODO...
 */
public class LLATErrorStrategy extends DefaultErrorStrategy {

    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        super.reportNoViableAlternative(recognizer, e);
    }
}
