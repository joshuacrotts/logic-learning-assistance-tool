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
public class LogicSetup {
    private LinkedList<WffTree> wffTree;
    LogicSetupInterpreter logicSetupInterpreter;
    List<Object> general1 = new ArrayList(){{
        add(AlgorithmType.GENERAL);
        add("Closed Tree Determiner");
        add("Logical Falsehood Determiner");
        add("Logically Contingent Determiner");
        add("Logical Tautology Determiner");
        add("Main operator detector");
        add("Open Tree Determiner");
    }};
    List<Object> general2 = new ArrayList(){{
        add(AlgorithmType.GENERAL);
        add("Logically Contradictory Determiner");
        add("Logically Consistent Determiner");
        add("Logically Contrary Determiner");
        add("Logically Equivalent Determiner");
        add("Logically Implied Determiner");
    }};
    List<Object> generalMore = new ArrayList(){{
        add(AlgorithmType.GENERAL);
        add("Argument Truth Tree Validator");
    }};
    List<Object> propositional1 = new ArrayList() {{
        add(AlgorithmType.PROPOSITIONAL);
        add("Propositional Truth Tree Generator");
        add("Random Formula generation");
        add("Truth Table Generator");
    }};
    List<Object> predicate1 = new ArrayList() {{
        add(AlgorithmType.PREDICATE);
        //add("Bound Variable Detector");
        add("Closed Sentence Determiner");
        //add("Free Variable Detector");
        add("Ground Sentence Determiner");
        add("Open Sentence Determiner");
        add("Predicate Truth Tree Generator");
    }};

    public LogicSetup() {
        logicSetupInterpreter = new LogicSetupInterpreter(this);
    }

    public interface LogicReturn {}

    public class LogicTruthAndTree implements LogicReturn {
        private boolean truthValue;
        private WffTree wffTree;
        public LogicTruthAndTree (boolean _truthValue, WffTree _wffTree){
            this.truthValue = _truthValue;
            this.wffTree = _wffTree;
        }
        public boolean getTruthValue() {return this.truthValue;}
        public WffTree getWffTree() {return this.wffTree;}
    }

    public class LogicTruth implements LogicReturn {
        private boolean truthValue;
        public LogicTruth (boolean _truthValue) {
            this.truthValue = _truthValue;
        }
        public boolean getTruthValue() {return this.truthValue;}
    }



    public class LogicTruthTree implements LogicReturn {
        private TruthTree truthTree;
        public LogicTruthTree(TruthTree _truthTree) {
            this.truthTree = _truthTree;
        }
        public TruthTree getTruthTree () {return this.truthTree;}
    }

    public class LogicTree implements LogicReturn {
        private WffTree wffTree;
        public LogicTree (WffTree _wffTree) {
            this.wffTree = _wffTree;
        }
        public WffTree getWffTree() {return this.wffTree;}
    }

    public class LogicTrees implements LogicReturn {
        private LinkedList<WffTree> wffTrees;

        public LogicTrees(LinkedList<WffTree> _wffTrees) {
            this.wffTrees = _wffTrees;
        }
        public LinkedList<WffTree> getWffTrees() {return this.wffTrees;}
    }
    public AlgorithmType convertStringToAlgorithmType(String _inputType) {
        switch(_inputType) {
            case "Closed Tree Determiner":
                return AlgorithmType.CLOSED_TREE_DETERMINER;                                    /*Truth and Parse Tree*/
            case "Logical Falsehood Determiner":
                return AlgorithmType.LOGICAL_FALSEHOOD_DETERMINER;                              /*Truth and Parse Tree*/
            case "Logically Contingent Determiner":
                return AlgorithmType.LOGICALLY_CONTINGENT_DETERMINER;                                     /*Parse Tree*/
            case "Logical Tautology Determiner":
                return AlgorithmType.LOGICAL_TAUTOLOGY_DETERMINER;                              /*Truth and Parse Tree*/
            case "Main operator detector":
                return AlgorithmType.MAIN_OPERATOR_DETECTOR;                                              /*Parse Tree*/
            case "Open Tree Determiner":
                return AlgorithmType.OPEN_TREE_DETERMINER;                                      /*Truth and Parse Tree*/
            case "Logically Contradictory Determiner":
                return AlgorithmType.LOGICALLY_CONTRADICTORY_DETERMINER;                        /*Truth and Parse Tree*/
            case "Logically Consistent Determiner":
                return AlgorithmType.LOGICALLY_CONSISTENT_DETERMINER;                           /*Truth and Parse Tree*/
            case "Logically Contrary Determiner":
                return AlgorithmType.LOGICALLY_CONTRARY_DETERMINER;                             /*Truth and Parse Tree*/
            case "Logically Equivalent Determiner":
                return AlgorithmType.LOGICALLY_EQUIVALENT_DETERMINER;                           /*Truth and Parse Tree*/
            case "Logically Implied Determiner":
                return AlgorithmType.LOGICALLY_IMPLIED_DETERMINER;                              /*Truth and Parse Tree*/
            case "Argument Truth Tree Validator":
                return AlgorithmType.ARGUMENT_TRUTH_TREE_VALIDATOR;                             /*Truth and Parse Tree*/
            case "Propositional Truth Tree Generator":
                return AlgorithmType.PROPOSITIONAL_TRUTH_TREE_GENERATOR;                        /*Truth and Parse Tree*/
            case "Random Formula generation":
                return AlgorithmType.RANDOM_FORMULA_GENERATION;
            case "Truth Table Generator":
                return AlgorithmType.TRUTH_TABLE_GENERATOR;
            case "Bound Variable Detector":
                return AlgorithmType.BOUND_VARIABLE_DETECTOR;                                             /*Parse Tree*/
            case "Closed Sentence Determiner":
                return AlgorithmType.CLOSED_SENTENCE_DETERMINER;                                          /*Parse Tree*/
            case "Free Variable Detector":
                return AlgorithmType.FREE_VARIABLE_DETECTOR;                                              /*Parse Tree*/
            case "Ground Sentence Determiner":
                return AlgorithmType.GROUND_SENTENCE_DETERMINER;                                          /*Parse Tree*/
            case "Open Sentence Determiner":
                return AlgorithmType.OPEN_SENTENCE_DETERMINER;                                            /*Parse Tree*/
            case "Predicate Truth Tree Generator":
                return AlgorithmType.PREDICATE_TRUTH_TREE_GENERATOR;
            default:
                return AlgorithmType.NULL;
        }
    }
    public LogicReturn detectAlgorithm (AlgorithmType _algorithm) {
        switch (_algorithm) {
                // Build parse and truth tree.
            case CLOSED_TREE_DETERMINER:
                ClosedTreeDeterminer closedTreeDeterminer = new ClosedTreeDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (closedTreeDeterminer.hasAllClosed(), this.wffTree.get(0));
                // Build parse and truth tree.
            case LOGICAL_FALSEHOOD_DETERMINER:
                LogicalFalsehoodDeterminer logicalFalsehoodDeterminer = new LogicalFalsehoodDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (logicalFalsehoodDeterminer.isFalsehood(), logicalFalsehoodDeterminer.getTree());
                // Build parse tree.
            case LOGICALLY_CONTINGENT_DETERMINER:
                LogicallyContingentDeterminer logicallyContingentDeterminer = new LogicallyContingentDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (logicallyContingentDeterminer.isContingent(), logicallyContingentDeterminer.getWffTree());
                // Build parse and truth tree.
            case LOGICAL_TAUTOLOGY_DETERMINER:
                LogicalTautologyDeterminer logicalTautologyDeterminer = new LogicalTautologyDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (logicalTautologyDeterminer.isTautology(), logicalTautologyDeterminer.getTree());
                // Build parse tree.
            case MAIN_OPERATOR_DETECTOR:
                MainOperatorDetector mainOperatorDetector = new MainOperatorDetector(this.wffTree.get(0));
                return new LogicTree (mainOperatorDetector.get());
                // Build parse and truth tree.
            case OPEN_TREE_DETERMINER:
                OpenTreeDeterminer openTreeDeterminer = new OpenTreeDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (openTreeDeterminer.hasSomeOpen(), this.wffTree.get(0));
                // Build parse and truth tree.
            case LOGICALLY_CONTRADICTORY_DETERMINER:
                LogicallyContradictoryDeterminer logicallyContradictoryDeterminer = new LogicallyContradictoryDeterminer(this.wffTree.get(0), this.wffTree.get(1));
                return new LogicTruthAndTree (logicallyContradictoryDeterminer.isContradictory(), logicallyContradictoryDeterminer.getCombinedTree());
                // Build parse and truth tree.
            case LOGICALLY_CONSISTENT_DETERMINER:
                LogicallyConsistentDeterminer logicallyConsistentDeterminer = new LogicallyConsistentDeterminer(this.wffTree.get(0), this.wffTree.get(1));
                return new LogicTruthAndTree (logicallyConsistentDeterminer.isConsistent(), logicallyConsistentDeterminer.getCombinedTree());
                // Build parse and truth tree.
            case LOGICALLY_CONTRARY_DETERMINER:
                LogicallyContraryDeterminer logicallyContraryDeterminer = new LogicallyContraryDeterminer(this.wffTree.get(0), this.wffTree.get(1));
                return new LogicTruthAndTree (logicallyContraryDeterminer.isContrary(), logicallyContraryDeterminer.getCombinedTree());
                // Build parse and truth tree.
            case LOGICALLY_EQUIVALENT_DETERMINER:
                LogicallyEquivalentDeterminer logicallyEquivalentDeterminer = new LogicallyEquivalentDeterminer(this.wffTree.get(0), this.wffTree.get(1));
                return new LogicTruthAndTree (logicallyEquivalentDeterminer.isEquivalent(), logicallyEquivalentDeterminer.getCombinedTree());
                // Build parse and truth tree.
            case LOGICALLY_IMPLIED_DETERMINER:
                LogicallyImpliedDeterminer logicallyImpliedDeterminer = new LogicallyImpliedDeterminer(this.wffTree.get(0), this.wffTree.get(1));
                return new LogicTruthAndTree (logicallyImpliedDeterminer.isImplied(), logicallyImpliedDeterminer.getCombinedTree());
                // Build parse and truth tree.
            case ARGUMENT_TRUTH_TREE_VALIDATOR:
                ArgumentTruthTreeValidator argumentTruthTreeValidator = new ArgumentTruthTreeValidator(this.wffTree);
                return new LogicTree (argumentTruthTreeValidator.getCombinedTree());
                // Build parse and truth tree.
            case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                PropositionalTruthTreeGenerator propositionalTruthTreeGenerator = new PropositionalTruthTreeGenerator(this.wffTree.get(0));
                return new LogicTruthTree (propositionalTruthTreeGenerator.getTruthTree());
            case RANDOM_FORMULA_GENERATION:
                return null;
                // Build truth table.
            case TRUTH_TABLE_GENERATOR:
                TruthTableGenerator truthTableGenerator = new TruthTableGenerator(this.wffTree.get(0));
                return new LogicTruthAndTree (truthTableGenerator.getTruthTable(), this.wffTree.get(0));
                // Build parse tree.
            case BOUND_VARIABLE_DETECTOR:
                BoundVariableDetector boundVariableDetector = new BoundVariableDetector(this.wffTree.get(0));
                return new LogicTrees (boundVariableDetector.getBoundVariables());
                // Build parse tree.
            case CLOSED_SENTENCE_DETERMINER:
                ClosedSentenceDeterminer closedSentenceDeterminer = new ClosedSentenceDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (closedSentenceDeterminer.isClosedSentence(), this.wffTree.get(0));
                // Build parse tree.
            case FREE_VARIABLE_DETECTOR:
                FreeVariableDetector freeVariableDetector = new FreeVariableDetector(this.wffTree.get(0));
                return new LogicTrees (freeVariableDetector.getFreeVariables());
                // Build parse tree.
            case GROUND_SENTENCE_DETERMINER:
                GroundSentenceDeterminer groundSentenceDeterminer = new GroundSentenceDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (groundSentenceDeterminer.isGroundSentence(), this.wffTree.get(0));
                // Build parse tree.
            case OPEN_SENTENCE_DETERMINER:
                OpenSentenceDeterminer openSentenceDeterminer = new OpenSentenceDeterminer(this.wffTree.get(0));
                return new LogicTruthAndTree (openSentenceDeterminer.isOpenSentence(), this.wffTree.get(0));
                // Build truth tree.
            case PREDICATE_TRUTH_TREE_GENERATOR:
                PredicateTruthTreeGenerator predicateTruthTreeGenerator = new PredicateTruthTreeGenerator(this.wffTree.get(0));
                return new LogicTruthTree (predicateTruthTreeGenerator.getTruthTree());
            default:
                return null;
        }
    }

    public LinkedList<WffTree> getWffTree () {
        return this.wffTree;
    }

    public int getWffCount () {
        return (this.wffTree == null) ? -1 : this.wffTree.size();
    }

    public ArrayList<List<Object>> getAvailableAlgorithms() {
        switch (this.getWffCount()) {
            case -1:
                return null;
            case 1:
                return (this.wffTree.get(0).isPredicateWff()) ? new ArrayList<List<Object>>(){{add(general1); add(predicate1);}} : new ArrayList<List<Object>>(){{add(general1); add(propositional1);}};
            case 2:
                return new ArrayList<List<Object>>(){{add(general2); add(generalMore);}};
            default:
                return new ArrayList<List<Object>>(){{add(generalMore);}};
        }
    }

    public void setWffTree(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

}
