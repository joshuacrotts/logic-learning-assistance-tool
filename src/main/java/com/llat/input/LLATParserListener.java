package com.llat.input;

import com.llat.LLATBaseListener;
import com.llat.LLATParser;
import com.llat.input.treenode.*;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.Stack;

/**
 * LLATParserListener is the connector that defines the syntax and,
 * more importantly, the semantic analysis - how errors are defined
 * and reported. Each method inherited from LLATBaseListener comes
 * from the grammar LLAT.g4 file and correspond to parser rules.
 *
 * @author Joshua Crotts
 * @date 2/20/2021
 */
public class LLATParserListener extends LLATBaseListener {

    /**
     * Map to keep track of nodes across the different listener
     * methods.
     */
    private final ParseTreeProperty<WffTree> PARSE_TREE;

    /**
     * LLATParser object brought from the ParserTest.
     */
    private final LLATParser LLAT_PARSER;

    /**
     * Current root of the wff tree being constructed.
     */
    private WffTree wffTree;

    /**
     * Stack to keep track of all in-progress subwffs.
     */
    private Stack<WffTree> treeRoots;

    public LLATParserListener(LLATParser _llatParser) {
        super();

        this.LLAT_PARSER = _llatParser;
        this.PARSE_TREE = new ParseTreeProperty<>();
        this.wffTree = new WffTree();
        this.treeRoots = new Stack<>();
    }

    @Override
    public void enterAtom(LLATParser.AtomContext ctx) {
        WffTree atomNode = new AtomNode(ctx.ATOM().getText());
        this.wffTree.addChild(atomNode);
    }

    @Override
    public void enterWff(LLATParser.WffContext ctx) {
        // If a wff is defined then we're using the recursive
        // definition and it MUST have balanced parentheses.
        if (ctx.wff() != null) {
            if (ctx.OPEN_PAREN() == null) {
                LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in wff definition.");
            }

            if (ctx.CLOSE_PAREN() == null) {
                LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in wff definition.");
            }
        }
    }

    @Override
    public void enterNegRule(LLATParser.NegRuleContext ctx) {
        NegNode negNode = new NegNode();
        this.treeRoots.push(this.wffTree);
        this.wffTree = negNode;
    }

    @Override
    public void exitNegRule(LLATParser.NegRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterAndRule(LLATParser.AndRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in AND operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in AND operator.");
        }

        AndNode andNode = new AndNode();
        this.treeRoots.push(this.wffTree);
        this.wffTree = andNode;
    }

    @Override
    public void exitAndRule(LLATParser.AndRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterOrRule(LLATParser.OrRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in OR operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in OR operator.");
        }

        OrNode orNode = new OrNode();
        this.treeRoots.push(this.wffTree);
        this.wffTree = orNode;
    }

    @Override
    public void exitOrRule(LLATParser.OrRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterImpRule(LLATParser.ImpRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in IMPLICATION operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in IMPLICATION operator.");
        }

        ImpNode impNode = new ImpNode();
        this.treeRoots.push(this.wffTree);
        this.wffTree = impNode;
    }

    @Override
    public void exitImpRule(LLATParser.ImpRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterBicondRule(LLATParser.BicondRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in BICONDITIONAL operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in BICONDITIONAL operator.");
        }

        BicondNode bicondNode = new BicondNode();
        this.treeRoots.push(this.wffTree);
        this.wffTree = bicondNode;
    }

    @Override
    public void exitBicondRule(LLATParser.BicondRuleContext ctx) {
        this.popTreeRoot();
    }

    public WffTree getSyntaxTree() {
        return LLATErrorListener.sawError() ? null : this.wffTree;
    }

    /**
     * Pops the root of the tree - each time a node with a potentially
     * left-recursive child is called (namely wff), we need to start adding
     * onto that specific WffTree. So, we save the old root into a stack,
     * and continue to add onto the new running "root". When we finish, we pop the
     * stack, add the running root as a child of the old root, and finally
     * reassign the links.
     */
    private void popTreeRoot() {
        WffTree oldRoot = this.treeRoots.pop(); // Remove the old root from the stack.
        oldRoot.addChild(this.wffTree); // Add the current running-node as a child of the old root.
        this.wffTree = oldRoot; // Reassign the root to be the old one.
    }
}
