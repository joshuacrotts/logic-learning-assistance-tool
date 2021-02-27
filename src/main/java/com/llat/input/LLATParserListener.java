package com.llat.input;

import com.llat.LLATBaseListener;
import com.llat.LLATParser;
import com.llat.models.treenode.*;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.LinkedList;
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
        this.PARSE_TREE.put(ctx, atomNode);
    }

    @Override
    public void enterPropWff(LLATParser.PropWffContext ctx) {
    }

    @Override
    public void exitPropWff(LLATParser.PropWffContext ctx) {
        if (ctx.atom() != null) {
            this.wffTree.addChild(this.PARSE_TREE.get(ctx.atom()));
        }
    }

    @Override
    public void enterPropNegRule(LLATParser.PropNegRuleContext ctx) {
        String symbol = ctx.NEG().getText();
        NegNode negNode = new NegNode(symbol);
        this.treeRoots.push(this.wffTree);
        this.wffTree = negNode;
    }

    @Override
    public void exitPropNegRule(LLATParser.PropNegRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPropAndRule(LLATParser.PropAndRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional AND operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional AND operator.");
        }

        AndNode andNode = new AndNode(ctx.AND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = andNode;
    }

    @Override
    public void exitPropAndRule(LLATParser.PropAndRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPropOrRule(LLATParser.PropOrRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional OR operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional OR operator.");
        }

        OrNode orNode = new OrNode(ctx.OR().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = orNode;
    }

    @Override
    public void exitPropOrRule(LLATParser.PropOrRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPropImpRule(LLATParser.PropImpRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional IMPLICATION operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional IMPLICATION operator.");
        }

        ImpNode impNode = new ImpNode(ctx.IMP().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = impNode;
    }

    @Override
    public void exitPropImpRule(LLATParser.PropImpRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPropBicondRule(LLATParser.PropBicondRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional BICONDITIONAL operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional BICONDITIONAL operator.");
        }

        BicondNode bicondNode = new BicondNode(ctx.BICOND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = bicondNode;
    }

    @Override
    public void exitPropBicondRule(LLATParser.PropBicondRuleContext ctx) {
        this.popTreeRoot();
    }

//========================== PREDICATE LOGIC LISTENERS =============================//

    @Override
    public void enterPredicate(LLATParser.PredicateContext ctx) {
    }

    @Override
    public void exitPredicate(LLATParser.PredicateContext ctx) {
        AtomNode atomNode = (AtomNode) PARSE_TREE.get(ctx.atom());

        // Loop through the children and add them to the list.
        // Each parameter is either a constant or variable.
        LinkedList<WffTree> parameters = new LinkedList<>();
        for (int i = 1; i < ctx.children.size(); i++) {
            parameters.add(PARSE_TREE.get(ctx.getChild(i)));
        }

        String atomLetter = atomNode.toString().replaceAll("ATOM: ", "");
        PredicateNode predicate = new PredicateNode(atomLetter, parameters);
        this.wffTree.addChild(predicate);
    }

    @Override
    public void enterConstant(LLATParser.ConstantContext ctx) {
        WffTree constantNode = new ConstantNode(ctx.CONSTANT().getText());
        this.PARSE_TREE.put(ctx, constantNode);
    }

    @Override
    public void exitConstant(LLATParser.ConstantContext ctx) {
    }

    @Override
    public void enterVariable(LLATParser.VariableContext ctx) {
        WffTree variableNode = new VariableNode(ctx.VARIABLE().getText());
        this.PARSE_TREE.put(ctx, variableNode);
    }

    @Override
    public void exitVariable(LLATParser.VariableContext ctx) {
    }

    @Override
    public void enterUniversal(LLATParser.UniversalContext ctx) {

    }

    @Override
    public void exitUniversal(LLATParser.UniversalContext ctx) {
        VariableNode variableNode = null;

        if (ctx.variable() != null) {
            variableNode = (VariableNode) this.PARSE_TREE.get(ctx.variable());
        } else {
            LLATErrorListener.syntaxError(ctx, "missing variable declaration for universal quantifier.");
            return;
        }

        String symbol = "("
                        + (ctx.UNIVERSAL() != null ? ctx.UNIVERSAL().getText() : "")
                        + (ctx.variable().getText())
                        + ")";
        UniversalQuantifierNode universalNode = new UniversalQuantifierNode(symbol,
                                                                            variableNode.getSymbol());
        this.wffTree.addChild(universalNode);
    }

    @Override
    public void enterExistential(LLATParser.ExistentialContext ctx) {
    }

    @Override
    public void exitExistential(LLATParser.ExistentialContext ctx) {
        VariableNode variableNode = null;

        if (ctx.variable() != null) {
            variableNode = (VariableNode) this.PARSE_TREE.get(ctx.variable());
        } else {
            LLATErrorListener.syntaxError(ctx, "missing variable declaration for existential quantifier.");
            return;
        }

        String symbol = "("
                        + ctx.EXISTENTIAL().getText()
                        + ctx.variable().getText()
                        + ")";
        ExistentialQuantifierNode existentialNode = new ExistentialQuantifierNode(symbol,
                                                                                    variableNode.getSymbol());
        this.wffTree.addChild(existentialNode);
    }

    @Override
    public void enterPredWff(LLATParser.PredWffContext ctx) {
    }

    @Override
    public void exitPredWff(LLATParser.PredWffContext ctx) {
    }

    @Override
    public void enterPredNegRule(LLATParser.PredNegRuleContext ctx) {
        NegNode negNode = new NegNode(ctx.NEG().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = negNode;
    }

    @Override
    public void exitPredNegRule(LLATParser.PredNegRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPredAndRule(LLATParser.PredAndRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional AND operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional AND operator.");
        }

        AndNode andNode = new AndNode(ctx.AND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = andNode;
    }

    @Override
    public void exitPredAndRule(LLATParser.PredAndRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPredOrRule(LLATParser.PredOrRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in propositional OR operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in propositional OR operator.");
        }

        OrNode orNode = new OrNode(ctx.OR().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = orNode;
    }

    @Override
    public void exitPredOrRule(LLATParser.PredOrRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPredImpRule(LLATParser.PredImpRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in predicate IMPLICATION operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in predicate IMPLICATION operator.");
        }

        ImpNode impNode = new ImpNode(ctx.IMP().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = impNode;
    }

    @Override
    public void exitPredImpRule(LLATParser.PredImpRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPredBicondRule(LLATParser.PredBicondRuleContext ctx) {
        if (ctx.OPEN_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing opening parenthesis in predicate BICONDITIONAL operator.");
        }

        if (ctx.CLOSE_PAREN() == null) {
            LLATErrorListener.syntaxError(ctx, "missing closing parenthesis in predicate BICONDITIONAL operator.");
        }

        BicondNode bicondNode = new BicondNode(ctx.BICOND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = bicondNode;
    }

    @Override
    public void exitPredBicondRule(LLATParser.PredBicondRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void exitPredIdentityRule(LLATParser.PredIdentityRuleContext ctx) {
        IdentityNode identityNode = new IdentityNode();
        identityNode.addChild(this.PARSE_TREE.get(ctx.getChild(0)));
        identityNode.addChild(this.PARSE_TREE.get(ctx.getChild(2)));

        this.wffTree.addChild(identityNode);
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
