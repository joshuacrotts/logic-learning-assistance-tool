package com.llat.models;

import com.llat.algorithms.ClosedTreeDeterminer;
import com.llat.models.interpreters.LogicSetupInterpreter;
import com.llat.models.treenode.WffTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
enum Algorithm {
    CLOSED_TREE_DETERMINER,
    LOGICAL_FALSEHOOD_DETERMINER,
    LOGICALLY_CONTINGENT_DETERMINER,
    LOGICAL_TAUTOLOGY_DETERMINER,
    MAIN_OPERATOR_DETECTOR,
    OPEN_TREE_DETERMINER,
    LOGICALLY_CONTRADICTORY_DETERMINER,
    LOGICALLY_CONSISTENT_DETERMINER,
    LOGICALLY_CONTRARY_DETERMINER,
    LOGICALLY_EQUIVALENT_DETERMINER,
    LOGICALLY_IMPLIED_DETERMINER,
    ARGUMENT_TRUTH_TREE_VALIDATOR,
    PROPOSITIONAL_TRUTH_TREE_GENERATOR,
    RANDOM_FORMULA_GENERATION,
    TRUTH_TABLE_GENERATOR,
    BOUND_VARIABLE_DETECTOR,
    CLOSED_SENTENCE_DETERMINER,
    FREE_VARIABLE_DETECTOR,
    GROUND_SENTENCE_DETERMINER,
    OPEN_SENTENCE_DETERMINER,
    PREDICATE_TRUTH_TREE_GENERATOR
}

public class LogicSetup {
    private LinkedList<WffTree> wffTree;
    LogicSetupInterpreter logicSetupInterpreter;
    List<String> general1 = new ArrayList(){{
        add("General");
        add("Closed Tree Determiner (1)");
        add("Logical Falsehood Determiner (1)");
        add("Logically Contingent Determiner (1)");
        add("Logical Tautology Determiner (1)");
        add("Main operator detector (1)");
        add("Open Tree Determiner (1)");
    }};
    List<String> general2 = new ArrayList(){{
        add("General");
        add("Logically Contradictory Determiner (2)");
        add("Logically Consistent Determiner (2)");
        add("Logically Contrary Determiner (2)");
        add("Logically Equivalent Determiner (2)");
        add("Logically Implied Determiner (2)");
    }};
    List<String> generalMore = new ArrayList(){{
        add("General");
        add("Argument Truth Tree Validator (>=2)");
    }};
    List<String> propositional1 = new ArrayList() {{
        add("Propositional");
        add("Propositional Truth Tree Generator (1)");
        add("Random Formula generation");
        add("Truth Table Generator (1)");
    }};
    List<String> predicate1 = new ArrayList() {{
        add("Predicate");
        add("Bound Variable Detector (1)");
        add("Closed Sentence Determiner (1)");
        add("Free Variable Detector (1)");
        add("Ground Sentence Determiner (1)");
        add("Open Sentence Determiner (1)");
        add("Predicate Truth Tree Generator (1)");
    }};

    public LogicSetup() {
        logicSetupInterpreter = new LogicSetupInterpreter(this);
    }

    public void detectAlgorithm (Algorithm _algorithm) {
        switch (_algorithm) {
            case CLOSED_TREE_DETERMINER:
                ClosedTreeDeterminer closedTreeDeterminer = new ClosedTreeDeterminer(this.wffTree.get(0));
            case LOGICAL_FALSEHOOD_DETERMINER:
            case LOGICALLY_CONTINGENT_DETERMINER:
            case LOGICAL_TAUTOLOGY_DETERMINER:
            case MAIN_OPERATOR_DETECTOR:
            case OPEN_TREE_DETERMINER:
            case LOGICALLY_CONTRADICTORY_DETERMINER:
            case LOGICALLY_CONSISTENT_DETERMINER:
            case LOGICALLY_CONTRARY_DETERMINER:
            case LOGICALLY_EQUIVALENT_DETERMINER:
            case LOGICALLY_IMPLIED_DETERMINER:
            case ARGUMENT_TRUTH_TREE_VALIDATOR:
            case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
            case RANDOM_FORMULA_GENERATION:
            case TRUTH_TABLE_GENERATOR:
            case BOUND_VARIABLE_DETECTOR:
            case CLOSED_SENTENCE_DETERMINER:
            case FREE_VARIABLE_DETECTOR:
            case GROUND_SENTENCE_DETERMINER:
            case OPEN_SENTENCE_DETERMINER:
            case PREDICATE_TRUTH_TREE_GENERATOR:
        }
    }

    public LinkedList<WffTree> getWffTree () {
        return this.wffTree;
    }

    public int getWffCount() {
        return (this.wffTree == null) ? -1 : this.wffTree.size();
    }

    public ArrayList<List<String>> getAvailableAlgorithms() {
        switch (this.getWffCount()) {
            case -1:
                return null;
            case 1:
                return (this.wffTree.get(0).isPredicateWff()) ? new ArrayList<List<String>>(){{add(general1); add(predicate1);}} : new ArrayList<List<String>>(){{add(general1); add(propositional1);}};
            case 2:
                return new ArrayList<List<String>>(){{add(general2); add(generalMore);}};
            default:
                return new ArrayList<List<String>>(){{add(generalMore);}};
        }
    }

    public void setWffTree(LinkedList<WffTree> _wffTree) {
        this.wffTree = _wffTree;
    }

}
