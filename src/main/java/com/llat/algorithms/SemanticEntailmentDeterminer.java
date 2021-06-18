package com.llat.algorithms;

import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.models.treenode.*;

import java.util.LinkedList;
import java.util.Stack;

/**
 * This should be a relatively easy algorithm to implement:
 * <p>
 * Two wffs A and B, we have the relationship A |= B if and only if
 * (A OR B) results in a tautology.
 * <p>
 * The idea is as follows: A needs to imply B, OR B needs to imply A,
 * but not both. The two statements also cannot be logically equivalent.
 * <p>
 * So, we can do the following: run a test for equivalence on
 * A and B, then run a test for tautology on ((A -> B) OR (B -> A)). If
 * the two are not equivalent, and the tautology test returns true,
 * then A semantically entails B.
 */
public class SemanticEntailmentDeterminer {

    /**
     *
     */
    private final TruthTree truthTree;

    /**
     * We combine the trees A and B as follows:
     * ((A -> B) OR (B -> A))
     */
    private final WffTree combinedTree;

    /**
     *
     */
    private final WffTree entailer;

    /**
     *
     */
    private final WffTree entailee;

    public SemanticEntailmentDeterminer(LinkedList<WffTree> _wffTreeList/*WffTree _treeOne, WffTree _treeTwo*/) {
        this.combinedTree = new WffTree();
        Stack<WffTree> nodes = new Stack<>();
        WffTree leaf = null;

        for (int i = 0; i < _wffTreeList.size() - 1; i++) {
            nodes.push(_wffTreeList.get(i).getChild(0));
            // If we have two nodes, pop them and perform a conjunction.
            if (nodes.size() == 2) {
                AndNode andNode = new AndNode();
                WffTree ch2 = nodes.pop();
                WffTree ch1 = nodes.pop();
                andNode.addChild(ch1);
                andNode.addChild(ch2);
                // Save the leaf so we can continue adding children.
                if (leaf != null) {
                    leaf.addChild(andNode);
                }
                leaf = andNode;
                nodes.push(andNode);
            }
        }

        // Configure the entailer (the lhs of the entailment).
        this.entailer = new WffTree();
        this.entailer.setFlags(_wffTreeList.getFirst().getFlags());
        this.entailer.addChild(nodes.pop());

        // Configure the entailee (the rhs of the entailment).
        this.entailee = new WffTree();
        this.entailee.setFlags(_wffTreeList.getFirst().getFlags());
        this.entailee.addChild(_wffTreeList.get(_wffTreeList.size() - 1).getChild(0).copy());

        // Create ~(A -> B).
        NegNode negImpLHS = new NegNode();
        ImpNode impLHS = new ImpNode();
        impLHS.addChild(this.entailer.getChild(0).copy());
        impLHS.addChild(this.entailee.getChild(0).copy());
        negImpLHS.addChild(impLHS);

        // Creates ~(B -> A).
        NegNode negImpRHS = new NegNode();
        ImpNode impRHS = new ImpNode();
        impRHS.addChild(this.entailee.getChild(0).copy());
        impRHS.addChild(this.entailer.getChild(0).copy());
        negImpRHS.addChild(impRHS);

        // Takes the logical disjunction of the two to see
        // if one logically entails the other.
        OrNode orNode = new OrNode();
        orNode.addChild(negImpLHS);
        orNode.addChild(negImpRHS);

        // Add the OR node to the combined tree.
        this.combinedTree.addChild(orNode);
        this.combinedTree.setFlags(_wffTreeList.getFirst().getFlags());

        BaseTruthTreeGenerator treeGenerator;
        if (this.combinedTree.isPropositionalWff()) {
            treeGenerator = new PropositionalTruthTreeGenerator(this.combinedTree);
        } else {
            treeGenerator = new PredicateTruthTreeGenerator(this.combinedTree);
        }

        this.truthTree = treeGenerator.getTruthTree();
    }

    /**
     * The details for the algorithm are above.
     *
     * @return true if A semantically entails B, false otherwise.
     */
    public boolean isSemanticallyEntailing() {
        LogicallyEquivalentDeterminer led = new LogicallyEquivalentDeterminer(this.entailer, this.entailee);
        LogicalTautologyDeterminer ltd = new LogicalTautologyDeterminer(this.combinedTree);
        return !led.isEquivalent() && ltd.isTautology();
    }

    public WffTree getCombinedTree() {
        return this.combinedTree;
    }

    public TruthTree getTruthTree() {
        return this.truthTree;
    }
}
