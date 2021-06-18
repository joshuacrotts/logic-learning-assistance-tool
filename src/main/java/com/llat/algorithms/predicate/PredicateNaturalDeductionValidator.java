package com.llat.algorithms.predicate;

import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.models.treenode.*;

import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 */
public final class PredicateNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    private static final int TIMEOUT = 100;

    /**
     *
     */
    private final HashSet<Character> CONSTANTS;

    /**
     *
     */
    private final HashSet<Character> CONCLUSION_CONSTANTS;

    public PredicateNaturalDeductionValidator(LinkedList<WffTree> _wffTreeList) {
        super(_wffTreeList);
        // Get all constants and conclusion constants...
        this.CONSTANTS = new HashSet<>();
        this.CONCLUSION_CONSTANTS = new HashSet<>();
        for (int i = 0; i < _wffTreeList.size() - 1; i++)
            this.addAllConstantsToSet(_wffTreeList.get(i), this.CONSTANTS);
        this.addAllConstantsToSet(_wffTreeList.getLast(), this.CONCLUSION_CONSTANTS);
    }

    /**
     * Computes a natural deduction proof for a predicate logic formula. We use a couple of heuristics to ensure
     * the runtime/search space isn't COMPLETELY insane (those being that we only apply certain rules if others fail
     * to produce meaningful results, etc.).
     *
     * @return list of NDWffTree "args". These serve as the premises, with the last element in the list being
     * the conclusion.
     */
    @Override
    public LinkedList<NDWffTree> getNaturalDeductionProof() {
        while (!this.findConclusion() && !this.findContradictions()) {
            this.satisfy(this.CONCLUSION_WFF.getWffTree(), this.CONCLUSION_WFF);
            for (NDWffTree premise : this.PREMISES_LIST) {
                this.satisfy(premise.getWffTree(), premise);
            }
        }

        // Backtrack from the conclusion to mark all those nodes that were used in the proof.
        this.activateLinks(this.CONCLUSION_WFF);

        // Add the premises that were actually used in the argument.
        LinkedList<NDWffTree> args = new LinkedList<>();
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.isActive()) {
                args.add(ndWffTree);
            }
        }

        // Finally, add the conclusion.
        args.add(this.CONCLUSION_WFF);
        return args;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfy(WffTree _tree, NDWffTree _parent) {
        NDWffTree satisfiedNDWffTree = null;
        if (_tree.isPredicate() || _tree.isNegPredicate()) {
            satisfiedNDWffTree = this.satisfyPredicate(_tree, _parent);
        } else if (_tree.isAnd()) {
            satisfiedNDWffTree = this.satisfyConjunction(_tree, _parent);
        } else if (_tree.isExistential()) {
            satisfiedNDWffTree = this.satisfyExistential(_tree, _parent);
        }

        // If we couldn't find anything to deduce/reduce the proposition with,
        // try to search for it in the premises list.
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.getWffTree().stringEquals(_tree)) {
                return ndWffTree;
            }
        }

        return satisfiedNDWffTree;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyPredicate(WffTree _tree, NDWffTree _parent) {
        // First, check to see if the premise is a satisfied goal or not.
        if (this.isGoal(_tree)) {
            return this.getPremiseNDWffTree(_tree);
        }

        // If it is a quantifier, then we know _tree has a variable that may need replacing/substituting.
        // that may need replacing/substituting.
        if (_parent.getWffTree().isQuantifier()) {
            QuantifierNode qn = (QuantifierNode) _parent.getWffTree();
            char variable = qn.getVariableSymbolChar();
            // Loop through each other premise.
            for (NDWffTree othNDWffTree : this.PREMISES_LIST) {
                if (_tree.getNodeType() == othNDWffTree.getWffTree().getNodeType()) {
                    // If the two are negated, then we need to truncate it.
                    WffTree w1 = _tree.isNegation() ? _tree.getChild(0) : _tree;
                    WffTree w2 = othNDWffTree.getWffTree().isNegation() ? othNDWffTree.getWffTree().getChild(0)
                            : othNDWffTree.getWffTree();
                    if (w1.getChildrenSize() == w2.getChildrenSize()) {
                        // Now check to see if the children are the same OR
                        // if the former is a matching variable while the latter is a constant.
                        boolean found = true;
                        for (int i = 0; i < w1.getChildrenSize() && found; i++) {
                            WffTree ch1 = w1.getChild(i);
                            WffTree ch2 = w2.getChild(i);
                            if (ch1.stringEquals(ch2)) {
                                continue;
                            } else if (ch1.isVariable() && ch2.isConstant()) {
                                // Finally, make sure the variable matches the one in the quantifier.
                                VariableNode vn = (VariableNode) ch1;
                                if (variable != vn.getSymbol().charAt(0)) {
                                    found = false;
                                }
                            } else {
                                found = false;
                            }
                        }
                        if (found)
                            return othNDWffTree;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyConjunction(WffTree _tree, NDWffTree _parent) {
        if (!_parent.isAndEActive() && !_parent.isAndIActive() && !this.isConclusion(_parent)) {
            if (_tree.stringEquals(_parent.getWffTree()) &&
                    this.findSimplification(_tree, _parent)) {
                return null;
            }
        }

        NDWffTree lhs = this.satisfy(_tree.getChild(0), _parent);
        NDWffTree rhs = this.satisfy(_tree.getChild(1), _parent);
        if (lhs != null && rhs != null) {
            AndNode andNode = new AndNode();
            andNode.addChild(lhs.getWffTree());
            andNode.addChild(rhs.getWffTree());
            NDWffTree andNDWffTree = new NDWffTree(andNode, NDFlag.AI, NDStep.AI, lhs, rhs);
            this.addPremise(andNDWffTree);
            return andNDWffTree;
        }

        return null;
    }

    /**
     * @param _tree
     * @param _parent
     * @return
     */
    private NDWffTree satisfyExistential(WffTree _tree, NDWffTree _parent) {
        // Try to eliminate the existential if it's not a conclusion.
        if (!_parent.isExisActive() && !this.isConclusion(_parent)) {
            throw new UnsupportedOperationException("Haven't done this yet...");
        }

        NDWffTree child = this.satisfy(_tree.getChild(0), _parent);
        // If we find a satisfiable NDWffTree, then we can add it.
        if (child != null) {
            String v = ((ExistentialQuantifierNode) _parent.getWffTree()).getVariableSymbol();
            ExistentialQuantifierNode existentialQuantifierNode = new ExistentialQuantifierNode(v);
            existentialQuantifierNode.addChild(_tree.getChild(0));
            NDWffTree existentialNDWffTree = new NDWffTree(existentialQuantifierNode, NDFlag.EX, NDStep.EI, child);
            this.addPremise(existentialNDWffTree);
            return existentialNDWffTree;
        }

        return null;
    }

    /**
     * Recursively adds all constants found in a WffTree to a HashSet. The constants
     * should be listed as a ConstantNode.
     *
     * @param _tree - WffTree to recursively check.
     * @param _charSet - HashSet of characters to add the discovered constants to.
     */
    private void addAllConstantsToSet(WffTree _tree, HashSet<Character> _charSet) {
        if (_tree != null && _tree.isConstant()) {
            _charSet.add(_tree.getSymbol().charAt(0));
        }

        for (WffTree child : _tree.getChildren()) {
            this.addAllConstantsToSet(child, _charSet);
        }
    }
}