package com.llat.models;

public enum AlgorithmType {
    // General algorithms.
    GENERAL("GENERAL"),
    CLOSED_TREE_DETERMINER("Closed Tree Determiner"),
    LOGICAL_FALSEHOOD_DETERMINER("Logical Falsehood Determiner"),
    LOGICALLY_CONTINGENT_DETERMINER("Logically Contingent Determiner"),
    LOGICAL_TAUTOLOGY_DETERMINER("Logical Tautology Determiner"),
    MAIN_OPERATOR_DETECTOR("Main Operator Detector"),
    OPEN_TREE_DETERMINER("Open Tree Determiner"),
    LOGICALLY_CONTRADICTORY_DETERMINER("Logically Contradictory Determiner"),
    LOGICALLY_CONSISTENT_DETERMINER("Logically Consistent Determiner"),
    LOGICALLY_CONTRARY_DETERMINER("Logically Contrary Determiner"),
    LOGICALLY_EQUIVALENT_DETERMINER("Logically Equivalent Determiner"),
    LOGICALLY_IMPLIED_DETERMINER("Logically Implied Determiner"),
    ARGUMENT_TRUTH_TREE_VALIDATOR("Argument Truth Tree Validator"),

    // Propositional ONLY algorithms.
    PROPOSITIONAL("PROPOSITIONAL"),
    PROPOSITIONAL_TRUTH_TREE_GENERATOR("Propositional Truth Tree Generator"),
    RANDOM_FORMULA_GENERATION("Random Formula Generation"),
    TRUTH_TABLE_GENERATOR("Truth Table Generator"),

    // Predicate ONLY algorithms.
    PREDICATE("PREDICATE"),
    BOUND_VARIABLE_DETECTOR("Bound Variable Detector"),
    CLOSED_SENTENCE_DETERMINER("Closed Sentence Determiner"),
    FREE_VARIABLE_DETECTOR("Free Variable Detector"),
    GROUND_SENTENCE_DETERMINER("Ground Sentence Determiner"),
    OPEN_SENTENCE_DETERMINER("Open Sentence Determiner"),
    PREDICATE_TRUTH_TREE_GENERATOR("Predicate Truth Tree Generator"),

    NULL(null);

    /**
     * String representation of this enum.
     */
    private final String string;

    private AlgorithmType(String _string) {
        this.string = _string;
    }

    /**
     *
     * @param _s
     * @return
     */
    public static AlgorithmType getEnum(String _s) {
        for (AlgorithmType algorithm : AlgorithmType.values()) {
            if (algorithm.toString().equals(_s)) {
                return algorithm;
            }
        }

        throw new IllegalArgumentException("Cannot find enum where string is equal to " + _s);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
