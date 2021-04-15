package com.llat.models.localstorage.uidescription.mainview;

import com.llat.models.localstorage.LocalStorage;

public class LogicSymbols extends LocalStorage {
    private Propositional propositional;
    private Predicate predicate;

    public LogicSymbols(Propositional propositional, Predicate predicate) {
        this.propositional = propositional;
        this.predicate = predicate;
    }

    public Propositional getPropositional() {
        return propositional;
    }

    public void setPropositional(Propositional propositional) {
        this.propositional = propositional;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "com.llat.models.localstorage.LogicSymbols{" +
                "propositional=" + propositional +
                ", predicate=" + predicate +
                '}';
    }
}
