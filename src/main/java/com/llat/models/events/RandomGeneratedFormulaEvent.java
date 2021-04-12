package com.llat.models.events;

import com.llat.tools.Event;

public class RandomGeneratedFormulaEvent implements Event {
    String formula;
    private boolean isEmpty = false;

    public RandomGeneratedFormulaEvent (String _formula) {
        this.formula = _formula;
    }

    public RandomGeneratedFormulaEvent () {
        this.isEmpty = true;
    }

    public String getFormula() {
        return this.formula;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

}
