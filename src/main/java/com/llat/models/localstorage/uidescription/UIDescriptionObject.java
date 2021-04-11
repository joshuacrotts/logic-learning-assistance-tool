package com.llat.models.localstorage.uidescription;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.symbols.*;

public class UIDescriptionObject extends LocalStorage {

    private Implication implication;
    private Biconditional biconditional;
    private Negation negation;
    private Conjunction conjunction;
    private Disjunction disjunction;
    private ExclusiveDisjunction exclusiveDisjunction;
    private Turnstile turnstile;
    private DoubleTurnstile doubleTurnstile;
    private Universal universal;
    private Existential existential;

    public UIDescriptionObject(Implication implication, Biconditional biconditional, Negation negation,
                               Conjunction conjunction, Disjunction disjunction, ExclusiveDisjunction exclusiveDisjunction,
                               Turnstile turnstile, DoubleTurnstile doubleTurnstile, Universal universal, Existential existential) {
        this.implication = implication;
        this.biconditional = biconditional;
        this.negation = negation;
        this.conjunction = conjunction;
        this.disjunction = disjunction;
        this.exclusiveDisjunction = exclusiveDisjunction;
        this.turnstile = turnstile;
        this.doubleTurnstile = doubleTurnstile;
        this.universal = universal;
        this.existential = existential;
    }

    public Implication getImplication() {
        return this.implication;
    }

    public void setImplication(Implication implication) {
        this.implication = implication;
    }

    public Biconditional getBiconditional() {
        return this.biconditional;
    }

    public void setBiconditional(Biconditional biconditional) {
        this.biconditional = biconditional;
    }

    public Negation getNegation() {
        return this.negation;
    }

    public void setNegation(Negation negation) {
        this.negation = negation;
    }

    public Conjunction getConjunction() {
        return this.conjunction;
    }

    public void setConjunction(Conjunction conjunction) {
        this.conjunction = conjunction;
    }

    public Disjunction getDisjunction() {
        return this.disjunction;
    }

    public void setDisjunction(Disjunction disjunction) {
        this.disjunction = disjunction;
    }

    public ExclusiveDisjunction getExclusiveDisjunction() {
        return this.exclusiveDisjunction;
    }

    public void setExclusiveDisjunction(ExclusiveDisjunction exclusiveDisjunction) {
        this.exclusiveDisjunction = exclusiveDisjunction;
    }

    public Turnstile getTurnstile() {
        return this.turnstile;
    }

    public void setTurnstile(Turnstile turnstile) {
        this.turnstile = turnstile;
    }

    public DoubleTurnstile getDoubleTurnstile() {
        return this.doubleTurnstile;
    }

    public void setDoubleTurnstile(DoubleTurnstile doubleTurnstile) {
        this.doubleTurnstile = doubleTurnstile;
    }

    public Universal getUniversal() {
        return this.universal;
    }

    public void setUniversal(Universal universal) {
        this.universal = universal;
    }

    public Existential getExistential() {
        return this.existential;
    }

    public void setExistential(Existential existential) {
        this.existential = existential;
    }

    @Override
    public String toString() {
        return "UIDescriptionObject{" +
                "implication=" + this.implication +
                ", biconditional=" + this.biconditional +
                ", negation=" + this.negation +
                ", conjunction=" + this.conjunction +
                ", disjunction=" + this.disjunction +
                ", exclusiveDisjunction=" + this.exclusiveDisjunction +
                ", turnstile=" + this.turnstile +
                ", doubleTurnstile=" + this.doubleTurnstile +
                ", universal=" + this.universal +
                ", existential=" + this.existential +
                '}';
    }
}
