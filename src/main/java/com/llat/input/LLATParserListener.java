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
     * Stack to keep track of all in-progress subwffs.
     */
    private final Stack<WffTree> treeRoots;

    /**
     * LinkedList to return to the user of all WffTrees that were inputted.
     */
    private final LinkedList<WffTree> currentTrees;

    /**
     * Current root of the wff tree being constructed.
     */
    private WffTree wffTree;

    public LLATParserListener(LLATParser _llatParser) {
        super();

        this.LLAT_PARSER = _llatParser;
        this.PARSE_TREE = new ParseTreeProperty<>();
        this.treeRoots = new Stack<>();
        this.currentTrees = new LinkedList<>();
    }

    @Override
    public void enterPropositionalWff(LLATParser.PropositionalWffContext ctx) {
        if (this.wffTree != null && this.wffTree.isPredicateWff()) {
            LLATErrorListener.syntaxError(ctx, "Wff cannot be both propositional and predicate.");
            return;
        }

        this.wffTree = new WffTree();
        this.wffTree.setFlags(NodeFlag.PROPOSITIONAL);
    }

    @Override
    public void exitPropositionalWff(LLATParser.PropositionalWffContext ctx) {
        this.currentTrees.add(this.wffTree.copy());
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
        BicondNode bicondNode = new BicondNode(ctx.BICOND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = bicondNode;
    }

    @Override
    public void exitPropBicondRule(LLATParser.PropBicondRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPropExclusiveOrRule(LLATParser.PropExclusiveOrRuleContext ctx) {
        ExclusiveOrNode xorNode = new ExclusiveOrNode(ctx.XOR().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = xorNode;
    }

    @Override
    public void exitPropExclusiveOrRule(LLATParser.PropExclusiveOrRuleContext ctx) {
        this.popTreeRoot();
    }

//========================== PREDICATE LOGIC LISTENERS =============================//

    @Override
    public void enterPredicateWff(LLATParser.PredicateWffContext ctx) {
        if (this.wffTree != null && this.wffTree.isPropositionalWff()) {
            LLATErrorListener.syntaxError(ctx, "Wff cannot be both propositional and predicate.");
            return;
        }

        this.wffTree = new WffTree();
        this.wffTree.setFlags(NodeFlag.PREDICATE);
    }

    @Override
    public void exitPredicateWff(LLATParser.PredicateWffContext ctx) {
        this.currentTrees.add(this.wffTree.copy());
    }

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
    public void enterPredQuantifier(LLATParser.PredQuantifierContext ctx) {

    }

    @Override
    public void exitPredQuantifier(LLATParser.PredQuantifierContext ctx) {
        this.popTreeRoot();
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

        UniversalQuantifierNode uqn = new UniversalQuantifierNode(symbol, variableNode.getSymbol());
        this.treeRoots.push(wffTree);
        this.wffTree = uqn;
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

        ExistentialQuantifierNode uqn = new ExistentialQuantifierNode(symbol, variableNode.getSymbol());
        this.treeRoots.push(wffTree);
        this.wffTree = uqn;
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
        BicondNode bicondNode = new BicondNode(ctx.BICOND().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = bicondNode;
    }

    @Override
    public void exitPredBicondRule(LLATParser.PredBicondRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void enterPredExclusiveOrRule(LLATParser.PredExclusiveOrRuleContext ctx) {
        ExclusiveOrNode xorNode = new ExclusiveOrNode(ctx.XOR().getText());
        this.treeRoots.push(this.wffTree);
        this.wffTree = xorNode;
    }

    @Override
    public void exitPredExclusiveOrRule(LLATParser.PredExclusiveOrRuleContext ctx) {
        this.popTreeRoot();
    }

    @Override
    public void exitPredIdentityRule(LLATParser.PredIdentityRuleContext ctx) {
        IdentityNode identityNode = new IdentityNode();
        identityNode.addChild(this.PARSE_TREE.get(ctx.getChild(0)));
        identityNode.addChild(this.PARSE_TREE.get(ctx.getChild(2)));

        this.wffTree.addChild(identityNode);
    }

//========================== LOGIC RELATIONSHIP LISTENERS =============================//


    public LinkedList<WffTree> getSyntaxTrees() {
        return LLATErrorListener.sawError() ? null : this.currentTrees;
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
