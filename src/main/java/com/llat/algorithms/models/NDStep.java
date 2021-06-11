package com.llat.algorithms.models;

/**
 *
 */
public enum NDStep {

    HS("HS", "Hypothetical Syllogism"),
    MT("MT", "Modus Tollens"),
    MP("MP", "Modus Ponens"),
    P("P", "Premise"),
    C("C", "Conclusion"),
    DS("DS", "Disjunctive Syllogism"),
    AND_E("&E", "Conjunction Elimination"),
    AND_I("&I", "Conjunction Introduction"),
    FALSE_I("⊥I", "Contradiction"),
    FALSE_E("⊥E", "Contradiction Elimination"),
    OR_I("∨", "Disjunction Introduction");

    /**
     *
     */
    private final String STEP;

    /**
     *
     */
    private final String TEXT_STEP;

    NDStep(String _step, String _textStep) {
        this.STEP = _step;
        this.TEXT_STEP = _textStep;
    }

    @Override
    public String toString() {
        return STEP;
    }

    public String getTextStep() {
        return this.TEXT_STEP;
    }
}
