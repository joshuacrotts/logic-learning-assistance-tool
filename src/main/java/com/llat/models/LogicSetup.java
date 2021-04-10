package com.llat.models;

import com.llat.algorithms.*;
import com.llat.algorithms.models.TruthTree;
import com.llat.algorithms.predicate.*;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.algorithms.propositional.TruthTableGenerator;
import com.llat.models.interpreters.LogicSetupInterpreter;
import com.llat.models.treenode.WffTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class LogicSetup {

    /**
     *
     */
    private LinkedList<WffTree> wffTree;

    /**
     *
     */
    private final LogicSetupInterpreter logicSetupInterpreter;

    /**
     *
     */
    private final List<Object> general1 = new ArrayList() {{
        this.add(AlgorithmType.GENERAL);
        this.add(AlgorithmType.MAIN_OPERATOR_DETECTOR);
        this.add(AlgorithmType.CLOSED_TREE_DETERMINER);
        this.add(AlgorithmType.OPEN_TREE_DETERMINER);
        this.add(AlgorithmType.LOGICAL_FALSEHOOD_DETERMINER);
        this.add(AlgorithmType.LOGICALLY_CONTINGENT_DETERMINER);
        this.add(AlgorithmType.LOGICAL_TAUTOLOGY_DETERMINER);
    }};

    /**
     *
     */
    private final List<Object> general2 = new ArrayList() {{
        this.add(AlgorithmType.GENERAL);
        this.add(AlgorithmType.LOGICALLY_CONTRADICTORY_DETERMINER);
        this.add(AlgorithmType.LOGICALLY_CONSISTENT_DETERMINER);
        this.add(AlgorithmType.LOGICALLY_CONTRARY_DETERMINER);
        this.add(AlgorithmType.LOGICALLY_EQUIVALENT_DETERMINER);
        this.add(AlgorithmType.LOGICALLY_IMPLIED_DETERMINER);
    }};

    /**
     *
     */
    private final List<Object> generalMore = new ArrayList() {{
        this.add(AlgorithmType.GENERAL);
        this.add(AlgorithmType.ARGUMENT_TRUTH_TREE_VALIDATOR);
    }};

    /**
     *
     */
    private final List<Object> propositional1 = new ArrayList() {{
        this.add(AlgorithmType.PROPOSITIONAL);
        this.add(AlgorithmType.PROPOSITIONAL_TRUTH_TREE_GENERATOR);
        this.add(AlgorithmType.RANDOM_FORMULA_GENERATION);
        this.add(AlgorithmType.TRUTH_TABLE_GENERATOR);
    }};

    /**
     *
     */
    private final List<Object> predicate1 = new ArrayList() {{
        this.add(AlgorithmType.PREDICATE);
        this.add(AlgorithmType.BOUND_VARIABLE_DETECTOR);
        this.add(AlgorithmType.CLOSED_SENTENCE_DETERMINER);
        this.add(AlgorithmType.FREE_VARIABLE_DETECTOR);
        this.add(AlgorithmType.GROUND_SENTENCE_DETERMINER);
        this.add(AlgorithmType.OPEN_SENTENCE_DETERMINER);
        this.add(AlgorithmType.PREDICATE_TRUTH_TREE_GENERATOR);
    }};

    public LogicSetup() {
        this.logicSetupInterpreter = new LogicSetupInterpreter(this);
    }

    /**
     * @param _inputType
     * @return
     */
    public AlgorithmType convertStringToAlgorithmType(String _inputType) {
        return AlgorithmType.getEnum(_inputType);
    }

    /**
     * @param _algorithm
     * @return
     */
    public LogicReturn detectAlgorithm(AlgorithmType _algorithm) {
        WffTree rootOne = null;
        WffTree rootTwo = null;

        if (this.wffTree.size() >= 1) {
            rootOne = this.wffTree.get(0);
        }
        if (this.wffTree.size() == 2) {
            rootTwo = this.wffTree.get(1);
        }

        // Clear the highlighting before we detect an algorithm.
        if (rootOne != null) {
            rootOne.clearHighlighting();
        }

        if (rootTwo != null) {
            rootTwo.clearHighlighting();
        }

        // Choose the appropriate algorithm.
        switch (_algorithm) {
            // Build parse and truth tree.
            case CLOSED_TREE_DETERMINER:
                ClosedTreeDeterminer closedTreeDeterminer = new ClosedTreeDeterminer(rootOne);
                return new LogicTruthAndParseTree(closedTreeDeterminer.hasAllClosed(), rootOne);

            case LOGICAL_FALSEHOOD_DETERMINER:
                LogicalFalsehoodDeterminer logicalFalsehoodDeterminer = new LogicalFalsehoodDeterminer(rootOne);
                return new LogicTruthAndParseTree(logicalFalsehoodDeterminer.isFalsehood(), logicalFalsehoodDeterminer.getTree());

            case LOGICALLY_CONTINGENT_DETERMINER:
                LogicallyContingentDeterminer logicallyContingentDeterminer = new LogicallyContingentDeterminer(rootOne);
                return new LogicTruthAndParseTree(logicallyContingentDeterminer.isContingent(), logicallyContingentDeterminer.getWffTree());

            case LOGICAL_TAUTOLOGY_DETERMINER:
                LogicalTautologyDeterminer logicalTautologyDeterminer = new LogicalTautologyDeterminer(rootOne);
                return new LogicTruthAndParseTree(logicalTautologyDeterminer.isTautology(), logicalTautologyDeterminer.getTree());

            case MAIN_OPERATOR_DETECTOR:
                MainOperatorDetector mainOperatorDetector = new MainOperatorDetector(rootOne);
                WffTree mainOp = mainOperatorDetector.get();
                mainOp.setHighlighted(true);
                return new LogicTree(mainOperatorDetector.get());

            case OPEN_TREE_DETERMINER:
                OpenTreeDeterminer openTreeDeterminer = new OpenTreeDeterminer(rootOne);
                return new LogicTruthAndParseTree(openTreeDeterminer.hasSomeOpen(), rootOne);

            case LOGICALLY_CONTRADICTORY_DETERMINER:
                LogicallyContradictoryDeterminer logicallyContradictoryDeterminer = new LogicallyContradictoryDeterminer(rootOne, rootTwo);
                return new LogicTruthAndParseTree(logicallyContradictoryDeterminer.isContradictory(), logicallyContradictoryDeterminer.getCombinedTree());

            case LOGICALLY_CONSISTENT_DETERMINER:
                LogicallyConsistentDeterminer logicallyConsistentDeterminer = new LogicallyConsistentDeterminer(rootOne, rootTwo);
                return new LogicTruthAndParseTree(logicallyConsistentDeterminer.isConsistent(), logicallyConsistentDeterminer.getCombinedTree());

            case LOGICALLY_CONTRARY_DETERMINER:
                LogicallyContraryDeterminer logicallyContraryDeterminer = new LogicallyContraryDeterminer(rootOne, rootTwo);
                return new LogicTruthAndParseTree(logicallyContraryDeterminer.isContrary(), logicallyContraryDeterminer.getCombinedTree());

            case LOGICALLY_EQUIVALENT_DETERMINER:
                LogicallyEquivalentDeterminer logicallyEquivalentDeterminer = new LogicallyEquivalentDeterminer(rootOne, rootTwo);
                return new LogicTruthAndParseTree(logicallyEquivalentDeterminer.isEquivalent(), logicallyEquivalentDeterminer.getCombinedTree());

            case LOGICALLY_IMPLIED_DETERMINER:
                LogicallyImpliedDeterminer logicallyImpliedDeterminer = new LogicallyImpliedDeterminer(rootOne, rootTwo);
                return new LogicTruthAndParseTree(logicallyImpliedDeterminer.isImplied(), logicallyImpliedDeterminer.getCombinedTree());

            case ARGUMENT_TRUTH_TREE_VALIDATOR:
                ArgumentTruthTreeValidator argumentTruthTreeValidator = new ArgumentTruthTreeValidator(this.wffTree);
                return new LogicTruthParseAndTruthTree(argumentTruthTreeValidator.isValid(), argumentTruthTreeValidator.getCombinedTree(), argumentTruthTreeValidator.getTruthTree());

            case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                PropositionalTruthTreeGenerator propositionalTruthTreeGenerator = new PropositionalTruthTreeGenerator(rootOne);
                return new LogicParseAndTruthTree(propositionalTruthTreeGenerator.getWffTree(), propositionalTruthTreeGenerator.getTruthTree());

            case RANDOM_FORMULA_GENERATION:
                throw new UnsupportedOperationException("Random formula generation not working yet.");

            case TRUTH_TABLE_GENERATOR:
                TruthTableGenerator truthTableGenerator = new TruthTableGenerator(rootOne);
                return new LogicTruthAndParseTree(truthTableGenerator.getTruthTable(), rootOne);

            case BOUND_VARIABLE_DETECTOR:
                BoundVariableDetector boundVariableDetector = new BoundVariableDetector(rootOne);
                this.clearAndHighlight(rootOne, boundVariableDetector.getBoundVariables());
                return new LogicTree(rootOne);

            case CLOSED_SENTENCE_DETERMINER:
                ClosedSentenceDeterminer closedSentenceDeterminer = new ClosedSentenceDeterminer(rootOne);
                return new LogicTruthAndParseTree(closedSentenceDeterminer.isClosedSentence(), rootOne);

            case FREE_VARIABLE_DETECTOR:
                FreeVariableDetector freeVariableDetector = new FreeVariableDetector(rootOne);
                this.clearAndHighlight(rootOne, freeVariableDetector.getFreeVariables());
                return new LogicTree(rootOne);

            case GROUND_SENTENCE_DETERMINER:
                GroundSentenceDeterminer groundSentenceDeterminer = new GroundSentenceDeterminer(rootOne);
                return new LogicTruthAndParseTree(groundSentenceDeterminer.isGroundSentence(), rootOne);

            case OPEN_SENTENCE_DETERMINER:
                OpenSentenceDeterminer openSentenceDeterminer = new OpenSentenceDeterminer(rootOne);
                return new LogicTruthAndParseTree(openSentenceDeterminer.isOpenSentence(), rootOne);

            case PREDICATE_TRUTH_TREE_GENERATOR:
                PredicateTruthTreeGenerator predicateTruthTreeGenerator = new PredicateTruthTreeGenerator(rootOne);
                return new LogicParseAndTruthTree(predicateTruthTreeGenerator.getWffTree(), predicateTruthTreeGenerator.getTruthTree());

            default:
                return null;
        }
    }

    /**
     * @param _root
     * @param _trees
     */
    private void clearAndHighlight(WffTree _root, LinkedList<WffTree> _trees) {
        for (WffTree t : _trees)
            t.setHighlighted(true);
    }

    public LinkedList<WffTree> getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

    public int getWffCount() {
        return (this.wffTree == null) ? -1 : this.wffTree.size();
    }

    public ArrayList<List<Object>> getAvailableAlgorithms() {
        switch (this.getWffCount()) {
            case -1:
                return null;
            case 1:
                return (this.wffTree.get(0).isPredicateWff()) ? new ArrayList<List<Object>>() {{
                    this.add(LogicSetup.this.general1);
                    this.add(LogicSetup.this.predicate1);
                }} : new ArrayList<List<Object>>() {{
                    this.add(LogicSetup.this.general1);
                    this.add(LogicSetup.this.propositional1);
                }};
            case 2:
                return new ArrayList<List<Object>>() {{
                    this.add(LogicSetup.this.general2);
                    this.add(LogicSetup.this.generalMore);
                }};
            default:
                return new ArrayList<List<Object>>() {{
                    this.add(LogicSetup.this.generalMore);
                }};
        }
    }

    public interface LogicReturn {
    }

    /**
     *
     */
    public static class LogicTruthAndParseTree implements LogicReturn {
        private final boolean truthValue;
        private final WffTree wffTree;

        public LogicTruthAndParseTree(boolean _truthValue, WffTree _wffTree) {
            this.truthValue = _truthValue;
            this.wffTree = _wffTree;
        }

        public boolean getTruthValue() {
            return this.truthValue;
        }

        public WffTree getWffTree() {
            return this.wffTree;
        }
    }

    /**
     *
     */
    public static class LogicParseAndTruthTree implements LogicReturn {
        private final WffTree wffTree;
        private final TruthTree truthTree;

        public LogicParseAndTruthTree(WffTree _wffTree, TruthTree _truthTree) {
            this.wffTree = _wffTree;
            this.truthTree = _truthTree;
        }

        public WffTree getWffTree() {
            return this.wffTree;
        }

        public TruthTree getTruthTree() {
            return this.truthTree;
        }

    }

    /**
     *
     */
    public static class LogicTruth implements LogicReturn {
        private final boolean truthValue;

        public LogicTruth(boolean _truthValue) {
            this.truthValue = _truthValue;
        }

        public boolean getTruthValue() {
            return this.truthValue;
        }
    }

    /**
     *
     */
    public static class LogicTruthTree implements LogicReturn {
        private final TruthTree truthTree;

        public LogicTruthTree(TruthTree _truthTree) {
            this.truthTree = _truthTree;
        }

        public TruthTree getTruthTree() {
            return this.truthTree;
        }
    }

    /**
     *
     */
    public static class LogicTree implements LogicReturn {
        private final WffTree wffTree;

        public LogicTree(WffTree _wffTree) {
            this.wffTree = _wffTree;
        }

        public WffTree getWffTree() {
            return this.wffTree;
        }
    }

    /**
     *
     */
    public static class LogicTrees implements LogicReturn {
        private final LinkedList<WffTree> wffTrees;

        public LogicTrees(LinkedList<WffTree> _wffTrees) {
            this.wffTrees = _wffTrees;
        }

        public LinkedList<WffTree> getWffTrees() {
            return this.wffTrees;
        }
    }

    /**
     *
     */
    public static class LogicTruthParseAndTruthTree implements LogicReturn {

        private final boolean truthValue;
        private final WffTree wffTree;
        private final TruthTree truthTree;

        public LogicTruthParseAndTruthTree(boolean _truthValue, WffTree _wffTree, TruthTree _truthTree) {
            this.truthValue = _truthValue;
            this.wffTree = _wffTree;
            this.truthTree = _truthTree;
        }

        public boolean getTruthValue() {
            return this.truthValue;
        }

        public WffTree getWffTree() {
            return this.wffTree;
        }

        public TruthTree getTruthTree() {
            return this.truthTree;
        }
    }
}
