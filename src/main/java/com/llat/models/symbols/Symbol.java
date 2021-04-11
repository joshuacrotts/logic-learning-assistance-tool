package com.llat.models.symbols;

import java.util.List;

public abstract class Symbol {

    private StringSymbol symbol;
    private String description;
    private Axioms axioms;
    private String tooltip;
    private String readAs;

    public StringSymbol getSymbol() {
        return this.symbol;
    }

    public void setSymbol(StringSymbol symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Axioms getAxioms() {
        return this.axioms;
    }

    public void setAxioms(Axioms axioms) {
        this.axioms = axioms;
    }

    public String getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getReadAs() {
        return this.readAs;
    }

    public void setReadAs(String readAs) {
        this.readAs = readAs;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbol=" + this.symbol +
                ", description='" + this.description + '\'' +
                ", axioms=" + this.axioms +
                ", tooltip='" + this.tooltip + '\'' +
                ", readAs='" + this.readAs + '\'' +
                '}';
    }

    public class StringSymbol {
        String applied;
        List<String> allSymbols;

        public String getApplied() {
            return this.applied;
        }

        public void setApplied(String applied) {
            this.applied = applied;
        }

        public List<String> getAllSymbols() {
            return this.allSymbols;
        }

        public void setAllSymbols(List<String> allSymbols) {
            this.allSymbols = allSymbols;
        }

        @Override
        public String toString() {
            return "StringSymbol{" +
                    "applied='" + this.applied + '\'' +
                    ", allSymbols=" + this.allSymbols +
                    '}';
        }
    }

    public class Axioms {
        String explanation;
        List<String> example;

        public String getExplanation() {
            return this.explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public List<String> getExample() {
            return this.example;
        }

        public void setExample(List<String> example) {
            this.example = example;
        }

        @Override
        public String toString() {
            return "Axioms{" +
                    "explanation='" + this.explanation + '\'' +
                    ", example='" + this.example + '\'' +
                    '}';
        }
    }
}

