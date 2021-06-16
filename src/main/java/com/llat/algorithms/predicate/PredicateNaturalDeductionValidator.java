package com.llat.algorithms.predicate;

import com.llat.algorithms.ArgumentTruthTreeValidator;
import com.llat.algorithms.BaseNaturalDeductionValidator;
import com.llat.algorithms.BaseTruthTreeGenerator;
import com.llat.algorithms.models.NDFlag;
import com.llat.algorithms.models.NDStep;
import com.llat.algorithms.models.NDWffTree;
import com.llat.algorithms.models.TruthTree;
import com.llat.input.events.SyntaxErrorEvent;
import com.llat.models.treenode.*;
import com.llat.tools.EventBus;

import java.util.*;

/**
 *
 */
public final class PredicateNaturalDeductionValidator extends BaseNaturalDeductionValidator {

    /**
     *
     */
    protected static final int TIMEOUT = 100;

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
            this.addAllConstants(_wffTreeList.get(i), this.CONSTANTS);
        this.addAllConstants(_wffTreeList.getLast(), this.CONCLUSION_CONSTANTS);
    }

    /**
     * Computes a natural deduction proof for a predicate logic formula. We use a couple of heuristics to ensure
     * the runtime/search space isn't COMPLETELY insane (those being that we only apply certain rules if others fail
     * to produce meaningful results, etc.).
     *
     * @return list of NDWffTree "args". These serve as the premises, with the last element in the list being
     * the conclusion.
     */
    public LinkedList<NDWffTree> getNaturalDeductionProof() {
        ArgumentTruthTreeValidator truthTreeValidator = new ArgumentTruthTreeValidator(this.ORIGINAL_WFFTREE_LIST);
        if (!truthTreeValidator.isValid()) { return null; }

        // We'll either find the conclusion or time out first.
        int currIteration = 0;
        boolean foundConclusion = false;
        boolean foundContradiction = false;
        for (currIteration = 0; currIteration <= PredicateNaturalDeductionValidator.TIMEOUT;
             currIteration++) {
            boolean mod1 = false;
            // If any of these return true we won't do the extra steps.
            mod1 = this.findExistentialQuantifiers();
            mod1 = this.findSimplifications() || mod1;
            mod1 = this.findModusPonens() || mod1;
            mod1 = this.findModusTollens() || mod1;
            mod1 = this.findDisjunctiveSyllogisms() || mod1;
            mod1 = this.findHypotheticalSyllogisms() || mod1;
            mod1 = this.findDoubleNegations() || mod1;
            mod1 = this.findBiconditionalEquivalences() || mod1;
            mod1 = this.findUniversalQuantifiers() || mod1;

            this.appendDisjunctions();
            if (!mod1) { this.appendConjunctions(); }
            if (!mod1) { mod1 = this.findMaterialImplicationEquivalences(); }
            if (!mod1) { this.findDeMorganEquivalences(); }

            // If we found a conclusion or contradiction, quit.
            if ((foundConclusion = this.findConclusion())) break;
            if ((foundContradiction = this.findContradictions())) break;
        }

        // If we timed out, just return null.
        if (currIteration > PredicateNaturalDeductionValidator.TIMEOUT) { return null; }

        // Backtrack from the conclusion to mark all those nodes that were used in the proof.
        this.activateLinks(this.CONCLUSION_WFF);

        // Add the premises that were actually used in the argument.
        LinkedList<NDWffTree> args = new LinkedList<>();
        for (NDWffTree ndWffTree : this.PREMISES_LIST) {
            if (ndWffTree.isActive()) {
                args.add(ndWffTree);
            }
        }

        // Finally, add the conclusion IF it wasn't derived through a contradiction.
        if (!foundContradiction) { args.add(this.CONCLUSION_WFF); }
        return args;
    }

    /**
     *
     * @return
     */
    private boolean findExistentialQuantifiers() {
        boolean changed = false;
       // First try to eliminate the quantifiers by replacing them with new constants.
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().isExistential() && !ndWffTree.isExisActive() && !ndWffTree.isUnivActive()) {
                changed = true;
                ndWffTree.setFlags(NDFlag.EXIS);
                char variable = ((ExistentialQuantifierNode) ndWffTree.getWffTree()).getVariableSymbol().charAt(0);
                this.addExistentialConstant(ndWffTree, variable);
            }
        }

        // Now try to replace constants with existential quantifiers that haven't been added by EXIS.
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (!ndWffTree.getWffTree().isQuantifier() && !ndWffTree.isExisActive() && !ndWffTree.isUnivActive()) {
                // First find all constants in the node.
                HashSet<Character> ndWffConstants = new HashSet<>();
                this.addAllConstants(ndWffTree.getWffTree(), ndWffConstants);

                // Now replace all of them.
                for (Character ch : ndWffConstants) {
                    this.removeExistentialConstant(ndWffTree, ch);
                }
                ndWffTree.setFlags(NDFlag.EXIS);
            }
        }
        return changed;
    }

    /**
     *
     * @return
     */
    private boolean findUniversalQuantifiers() {
        boolean changed = false;
        // First try to eliminate the quantifiers by replacing them with OLD constants.
        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
            if (ndWffTree.getWffTree().isUniversal() && !ndWffTree.isExisActive() && !ndWffTree.isUnivActive()) {
                changed = true;
                ndWffTree.setFlags(NDFlag.UNIV);
                char variable = ((UniversalQuantifierNode) ndWffTree.getWffTree()).getVariableSymbol().charAt(0);
                this.addUniversalConstants(ndWffTree, variable);
            }
        }
//
//        // Now try to replace constants with universal quantifiers that haven't been added by UNIV OR EXIS.
//        for (int i = 0; i < this.PREMISES_LIST.size(); i++) {
//            NDWffTree ndWffTree = this.PREMISES_LIST.get(i);
//            if (!ndWffTree.getWffTree().isQuantifier() && !ndWffTree.isExisActive() && !ndWffTree.isUnivActive()) {
//                // First find all constants in the node.
//                HashSet<Character> ndWffConstants = new HashSet<>();
//                this.addAllConstants(ndWffTree.getWffTree(), ndWffConstants);
//
//                // Now replace all of them.
//                for (Character ch : ndWffConstants) {
//                    this.removeUniversalConstants(ndWffTree, ch);
//                }
//                ndWffTree.setFlags(NDFlag.UNIV);
//            }
//        }
        return changed;
    }

    /**
     *
     */
    private void addExistentialConstant(NDWffTree _existentialNDWffTree, char _variableToReplace) {
        // Find the next available constant to use.
        char constant = 'a';
        while (this.CONSTANTS.contains(constant) || this.CONCLUSION_CONSTANTS.contains(constant)) {
            // This could wrap around...
            constant++;
        }

        // Replace all variables found with the constant.
        WffTree _newRoot = _existentialNDWffTree.getWffTree().getChild(0).copy();
        this.replaceSymbol(_newRoot, _variableToReplace, constant, ReplaceType.CONSTANT);
        this.addPremise(new NDWffTree(_newRoot, NDFlag.EXIS, NDStep.EE, _existentialNDWffTree));
        this.CONSTANTS.add(constant);
    }

    /**
     *
     */
    private void removeExistentialConstant(NDWffTree _ndWffTree, char _constantToReplace) {
        // Replace all occurrences of _constantToReplace with a variable.
        WffTree _newRoot = _ndWffTree.getWffTree().copy();
        // This WILL be a problem if the user decides to use ANYTHING else in the conclusion... maybe do all variables?
        ExistentialQuantifierNode existentialQuantifierNode = new ExistentialQuantifierNode("x");
        this.replaceSymbol(_newRoot, _constantToReplace, existentialQuantifierNode.getVariableSymbolChar(), ReplaceType.VARIABLE);
        existentialQuantifierNode.addChild(_newRoot);
        this.addPremise(new NDWffTree(existentialQuantifierNode, NDFlag.EXIS, NDStep.EI, _ndWffTree));
    }

    /**
     */
    private void addUniversalConstants(NDWffTree _universalNDWffTree, char _variableToReplace) {
        // Add a default constant if one is not available to the universal quantifier.
        if (this.CONSTANTS.isEmpty()) {
            this.CONSTANTS.add('a');
        }

        for (char c : this.CONSTANTS) {
            // Create a copy and replace the selected variable.
            WffTree _newRoot = _universalNDWffTree.getWffTree().getChild(0).copy();
            this.replaceSymbol(_newRoot, _variableToReplace, c, ReplaceType.CONSTANT);
            this.addPremise(new NDWffTree(_newRoot, NDStep.UE, _universalNDWffTree));
        }
    }

    private void removeUniversalConstants(NDWffTree _predicateNDWffTree) {

    }

    /**
     * Replaces a variable or a constant with a constant node in a WffTree. This is used when performing
     * existential, universal decomposition, or identity decomposition.
     *
     * @param _newRoot           - root of WffTree to modify.
     * @param _symbolToReplace   - constant or variable that we want to replace e.g. (x) = x
     * @param _symbol            - symbol to replace _symbolToReplace with.
     * @param _type
     */
    private void replaceSymbol(WffTree _newRoot, char _symbolToReplace, char _symbol, ReplaceType _type) {
        for (int i = 0; i < _newRoot.getChildrenSize(); i++) {
            if (_newRoot.getChild(i).isVariable() || _newRoot.getChild(0).isConstant()) {
                char v = _newRoot.getChild(i).getSymbol().charAt(0);
                if (v == _symbolToReplace) {
                    if (_type == ReplaceType.CONSTANT) { _newRoot.setChild(i, new ConstantNode("" + _symbol)); }
                    else if (_type == ReplaceType.VARIABLE) { _newRoot.setChild(i, new VariableNode("" + _symbol)); }
                }
            }
            this.replaceSymbol(_newRoot.getChild(i), _symbolToReplace, _symbol, _type);
        }
    }

    /**
     *
     * @param _tree
     * @param _charSet
     */
    private void addAllConstants(WffTree _tree, HashSet<Character> _charSet) {
        if (_tree != null && _tree.isConstant()) {
            _charSet.add(((ConstantNode) _tree).getSymbol().charAt(0));
        }

        for (WffTree child : _tree.getChildren()) {
            this.addAllConstants(child, _charSet);
        }
    }

    /**
     *
     */
    private enum ReplaceType {
        VARIABLE,
        CONSTANT
    }
}