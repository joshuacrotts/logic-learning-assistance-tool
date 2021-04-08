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
    private LogicSetupInterpreter logicSetupInterpreter;

    /**
     *
     */
    private final List<Object> general1 = new ArrayList() {{
        add(AlgorithmType.GENERAL);
        add(AlgorithmType.MAIN_OPERATOR_DETECTOR);
        add(AlgorithmType.CLOSED_TREE_DETERMINER);
        add(AlgorithmType.OPEN_TREE_DETERMINER);
        add(AlgorithmType.LOGICAL_FALSEHOOD_DETERMINER);
        add(AlgorithmType.LOGICALLY_CONTINGENT_DETERMINER);
        add(AlgorithmType.LOGICAL_TAUTOLOGY_DETERMINER);
    }};

    /**
     *
     */
    private final List<Object> general2 = new ArrayList() {{
        add(AlgorithmType.GENERAL);
        add(AlgorithmType.LOGICALLY_CONTRADICTORY_DETERMINER);
        add(AlgorithmType.LOGICALLY_CONSISTENT_DETERMINER);
        add(AlgorithmType.LOGICALLY_CONTRARY_DETERMINER);
        add(AlgorithmType.LOGICALLY_EQUIVALENT_DETERMINER);
        add(AlgorithmType.LOGICALLY_IMPLIED_DETERMINER);
    }};

    /**
     *
     */
    private final List<Object> generalMore = new ArrayList() {{
        add(AlgorithmType.GENERAL);
        add(AlgorithmType.ARGUMENT_TRUTH_TREE_VALIDATOR);
    }};

    /**
     *
     */
    private final List<Object> propositional1 = new ArrayList() {{
        add(AlgorithmType.PROPOSITIONAL);
        add(AlgorithmType.PROPOSITIONAL_TRUTH_TREE_GENERATOR);
        add(AlgorithmType.RANDOM_FORMULA_GENERATION);
        add(AlgorithmType.TRUTH_TABLE_GENERATOR);
    }};

    /**
     *
     */
    List<Object> predicate1 = new ArrayList() {{
        add(AlgorithmType.PREDICATE);
        add(AlgorithmType.BOUND_VARIABLE_DETECTOR);
        add(AlgorithmType.CLOSED_SENTENCE_DETERMINER);
        add(AlgorithmType.FREE_VARIABLE_DETECTOR);
        add(AlgorithmType.GROUND_SENTENCE_DETERMINER);
        add(AlgorithmType.OPEN_SENTENCE_DETERMINER);
        add(AlgorithmType.PREDICATE_TRUTH_TREE_GENERATOR);
    }};

    public LogicSetup() {
        logicSetupInterpreter = new LogicSetupInterpreter(this);
    }

    /**
     *
     * @param _inputType
     * @return
     */
    public AlgorithmType convertStringToAlgorithmType(String _inputType) {
        return AlgorithmType.getEnum(_inputType);
    }

    /**
     *
     * @param _algorithm
     * @return
     */
    public LogicReturn detectAlgorithm(AlgorithmType _algorithm) {
        WffTree rootOne = this.wffTree.size() == 1 ? this.wffTree.get(0) : null;
        WffTree rootTwo = this.wffTree.size() == 2 ? this.wffTree.get(1) : null;

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
                return new LogicTruthAndTree(closedTreeDeterminer.hasAllClosed(), rootOne);

            case LOGICAL_FALSEHOOD_DETERMINER:
                LogicalFalsehoodDeterminer logicalFalsehoodDeterminer = new LogicalFalsehoodDeterminer(rootOne);
                return new LogicTruthAndTree(logicalFalsehoodDeterminer.isFalsehood(), logicalFalsehoodDeterminer.getTree());

            case LOGICALLY_CONTINGENT_DETERMINER:
                LogicallyContingentDeterminer logicallyContingentDeterminer = new LogicallyContingentDeterminer(rootOne);
                return new LogicTruthAndTree(logicallyContingentDeterminer.isContingent(), logicallyContingentDeterminer.getWffTree());

            case LOGICAL_TAUTOLOGY_DETERMINER:
                LogicalTautologyDeterminer logicalTautologyDeterminer = new LogicalTautologyDeterminer(rootOne);
                return new LogicTruthAndTree(logicalTautologyDeterminer.isTautology(), logicalTautologyDeterminer.getTree());

            case MAIN_OPERATOR_DETECTOR:
                MainOperatorDetector mainOperatorDetector = new MainOperatorDetector(rootOne);
                WffTree mainOp = mainOperatorDetector.get();
                mainOp.setHighlighted(true);
                return new LogicTree(mainOperatorDetector.get());

            case OPEN_TREE_DETERMINER:
                OpenTreeDeterminer openTreeDeterminer = new OpenTreeDeterminer(rootOne);
                return new LogicTruthAndTree(openTreeDeterminer.hasSomeOpen(), rootOne);

            case LOGICALLY_CONTRADICTORY_DETERMINER:
                LogicallyContradictoryDeterminer logicallyContradictoryDeterminer = new LogicallyContradictoryDeterminer(rootOne, rootTwo);
                return new LogicTruthAndTree(logicallyContradictoryDeterminer.isContradictory(), logicallyContradictoryDeterminer.getCombinedTree());

            case LOGICALLY_CONSISTENT_DETERMINER:
                LogicallyConsistentDeterminer logicallyConsistentDeterminer = new LogicallyConsistentDeterminer(rootOne, rootTwo);
                return new LogicTruthAndTree(logicallyConsistentDeterminer.isConsistent(), logicallyConsistentDeterminer.getCombinedTree());

            case LOGICALLY_CONTRARY_DETERMINER:
                LogicallyContraryDeterminer logicallyContraryDeterminer = new LogicallyContraryDeterminer(rootOne, rootTwo);
                return new LogicTruthAndTree(logicallyContraryDeterminer.isContrary(), logicallyContraryDeterminer.getCombinedTree());

            case LOGICALLY_EQUIVALENT_DETERMINER:
                LogicallyEquivalentDeterminer logicallyEquivalentDeterminer = new LogicallyEquivalentDeterminer(rootOne, rootTwo);
                return new LogicTruthAndTree(logicallyEquivalentDeterminer.isEquivalent(), logicallyEquivalentDeterminer.getCombinedTree());

            case LOGICALLY_IMPLIED_DETERMINER:
                LogicallyImpliedDeterminer logicallyImpliedDeterminer = new LogicallyImpliedDeterminer(rootOne, rootTwo);
                return new LogicTruthAndTree(logicallyImpliedDeterminer.isImplied(), logicallyImpliedDeterminer.getCombinedTree());

            case ARGUMENT_TRUTH_TREE_VALIDATOR:
                ArgumentTruthTreeValidator argumentTruthTreeValidator = new ArgumentTruthTreeValidator(this.wffTree);
                return new LogicTree(argumentTruthTreeValidator.getCombinedTree());

            case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                PropositionalTruthTreeGenerator propositionalTruthTreeGenerator = new PropositionalTruthTreeGenerator(rootOne);
                return new LogicTruthTree(propositionalTruthTreeGenerator.getTruthTree());

            case RANDOM_FORMULA_GENERATION:
                throw new UnsupportedOperationException("Random formula generation not working yet.");

            case TRUTH_TABLE_GENERATOR:
                TruthTableGenerator truthTableGenerator = new TruthTableGenerator(rootOne);
                return new LogicTruthAndTree(truthTableGenerator.getTruthTable(), rootOne);

            case BOUND_VARIABLE_DETECTOR:
                BoundVariableDetector boundVariableDetector = new BoundVariableDetector(rootOne);
                this.clearAndHighlight(rootOne, boundVariableDetector.getBoundVariables());
                return new LogicTree(rootOne);

            case CLOSED_SENTENCE_DETERMINER:
                ClosedSentenceDeterminer closedSentenceDeterminer = new ClosedSentenceDeterminer(rootOne);
                return new LogicTruthAndTree(closedSentenceDeterminer.isClosedSentence(), rootOne);

            case FREE_VARIABLE_DETECTOR:
                FreeVariableDetector freeVariableDetector = new FreeVariableDetector(rootOne);
                this.clearAndHighlight(rootOne, freeVariableDetector.getFreeVariables());
                return new LogicTree(rootOne);

            case GROUND_SENTENCE_DETERMINER:
                GroundSentenceDeterminer groundSentenceDeterminer = new GroundSentenceDeterminer(rootOne);
                return new LogicTruthAndTree(groundSentenceDeterminer.isGroundSentence(), rootOne);

            case OPEN_SENTENCE_DETERMINER:
                OpenSentenceDeterminer openSentenceDeterminer = new OpenSentenceDeterminer(rootOne);
                return new LogicTruthAndTree(openSentenceDeterminer.isOpenSentence(), rootOne);

            case PREDICATE_TRUTH_TREE_GENERATOR:
                PredicateTruthTreeGenerator predicateTruthTreeGenerator = new PredicateTruthTreeGenerator(rootOne);
                return new LogicTruthTree(predicateTruthTreeGenerator.getTruthTree());

            default:
                return null;
        }
    }

    /**
     *
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

    public int getWffCount() {
        return (this.wffTree == null) ? -1 : this.wffTree.size();
    }

    public ArrayList<List<Object>> getAvailableAlgorithms() {
        switch (this.getWffCount()) {
            case -1:
                return null;
            case 1:
                return (this.wffTree.get(0).isPredicateWff()) ? new ArrayList<List<Object>>() {{
                    add(general1);
                    add(predicate1);
                }} : new ArrayList<List<Object>>() {{
                    add(general1);
                    add(propositional1);
                }};
            case 2:
                return new ArrayList<List<Object>>() {{
                    add(general2);
                    add(generalMore);
                }};
            default:
                return new ArrayList<List<Object>>() {{
                    add(generalMore);
                }};
        }
    }

    public void setWffTree(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

    public interface LogicReturn {
    }

    /**
     *
     */
    public class LogicTruthAndTree implements LogicReturn {
        private boolean truthValue;
        private WffTree wffTree;

        public LogicTruthAndTree(boolean _truthValue, WffTree _wffTree) {
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
    public class LogicTruth implements LogicReturn {
        private boolean truthValue;

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
    public class LogicTruthTree implements LogicReturn {
        private TruthTree truthTree;

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
    public class LogicTree implements LogicReturn {
        private WffTree wffTree;

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
    public class LogicTrees implements LogicReturn {
        private LinkedList<WffTree> wffTrees;

        public LogicTrees(LinkedList<WffTree> _wffTrees) {
            this.wffTrees = _wffTrees;
        }

        public LinkedList<WffTree> getWffTrees() {
            return this.wffTrees;
        }
    }
}
