package com.llat.models.localstorage.uidescription.mainview;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.symbols.*;

public class Propositional extends LocalStorage {
    private Implication implication;
    private Biconditional biconditional;
    private Negation negation;
    private Conjunction conjunction;
    private Disjunction disjunction;
    private ExclusiveDisjunction exclusiveDisjunction;
    private Turnstile turnstile;
    private DoubleTurnstile doubleTurnstile;

    public Propositional(Implication implication, Biconditional biconditional, Negation negation, Conjunction conjunction, Disjunction disjunction, ExclusiveDisjunction exclusiveDisjunction, Turnstile turnstile, DoubleTurnstile doubleTurnstile) {
        this.implication = implication;
        this.biconditional = biconditional;
        this.negation = negation;
        this.conjunction = conjunction;
        this.disjunction = disjunction;
        this.exclusiveDisjunction = exclusiveDisjunction;
        this.turnstile = turnstile;
        this.doubleTurnstile = doubleTurnstile;
    }

    public Implication getImplication() {
        return implication;
    }

    public void setImplication(Implication implication) {
        this.implication = implication;
    }

    public Biconditional getBiconditional() {
        return biconditional;
    }

    public void setBiconditional(Biconditional biconditional) {
        this.biconditional = biconditional;
    }

    public Negation getNegation() {
        return negation;
    }

    public void setNegation(Negation negation) {
        this.negation = negation;
    }

    public Conjunction getConjunction() {
        return conjunction;
    }

    public void setConjunction(Conjunction conjunction) {
        this.conjunction = conjunction;
    }

    public Disjunction getDisjunction() {
        return disjunction;
    }

    public void setDisjunction(Disjunction disjunction) {
        this.disjunction = disjunction;
    }

    public ExclusiveDisjunction getExclusiveDisjunction() {
        return exclusiveDisjunction;
    }

    public void setExclusiveDisjunction(ExclusiveDisjunction exclusiveDisjunction) {
        this.exclusiveDisjunction = exclusiveDisjunction;
    }

    public Turnstile getTurnstile() {
        return turnstile;
    }

    public void setTurnstile(Turnstile turnstile) {
        this.turnstile = turnstile;
    }

    public DoubleTurnstile getDoubleTurnstile() {
        return doubleTurnstile;
    }

    public void setDoubleTurnstile(DoubleTurnstile doubleTurnstile) {
        this.doubleTurnstile = doubleTurnstile;
    }

    @Override
    public String toString() {
        return "com.llat.models.localstorage.LogicSymbols{" +
                "implication=" + implication +
                ", biconditional=" + biconditional +
                ", negation=" + negation +
                ", conjunction=" + conjunction +
                ", disjunction=" + disjunction +
                ", exclusiveDisjunction=" + exclusiveDisjunction +
                ", turnstile=" + turnstile +
                ", doubleTurnstile=" + doubleTurnstile +
                '}';
    }

}
