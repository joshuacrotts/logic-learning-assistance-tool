package com.llat.input;

import com.llat.LLATBaseListener;
import com.llat.LLATParser;
import com.llat.input.treenode.WffNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 */
public class LLATParserListener extends LLATBaseListener {

    /**
     *
     */
    private final ParseTreeProperty<WffNode> PARSE_TREE;

    public LLATParserListener() {
        super();

        this.PARSE_TREE = new ParseTreeProperty<>();
    }

    @Override
    public void enterWff(LLATParser.WffContext ctx) {

    }

    @Override
    public void exitWff(LLATParser.WffContext ctx) {

    }

    @Override
    public void enterAtom(LLATParser.AtomContext ctx) {

    }

    @Override
    public void exitAtom(LLATParser.AtomContext ctx) {

    }

    @Override
    public void enterNegRule(LLATParser.NegRuleContext ctx) {

    }

    @Override
    public void exitNegRule(LLATParser.NegRuleContext ctx) {

    }

    @Override
    public void enterAndRule(LLATParser.AndRuleContext ctx) {

    }

    @Override
    public void exitAndRule(LLATParser.AndRuleContext ctx) {

    }

    @Override
    public void enterOrRule(LLATParser.OrRuleContext ctx) {

    }

    @Override
    public void exitOrRule(LLATParser.OrRuleContext ctx) {

    }

    @Override
    public void enterImpRule(LLATParser.ImpRuleContext ctx) {

    }

    @Override
    public void exitImpRule(LLATParser.ImpRuleContext ctx) {

    }

    @Override
    public void enterBicondRule(LLATParser.BicondRuleContext ctx) {

    }

    @Override
    public void exitBicondRule(LLATParser.BicondRuleContext ctx) {

    }
}
