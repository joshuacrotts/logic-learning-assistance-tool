package com.llat.input;

import com.llat.LLATLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

public class LLATRecoverableLexer extends LLATLexer {

    public LLATRecoverableLexer(CharStream input) {
        super(input);
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        super.recover(e);
    }
}
