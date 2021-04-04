package com.llat.views.events;

import com.llat.tools.Event;

public class FormulaInputEvent implements Event {

    /**
     *
     */
    private String formula;

    public FormulaInputEvent(String _formula) {
        this.formula = _formula;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String _formula) {
        this.formula = _formula;
    }
}
