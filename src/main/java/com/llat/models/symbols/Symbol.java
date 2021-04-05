package com.llat.models.symbols;

import java.util.List;

public abstract class Symbol {

    private StringSymbol symbol;
    private String description;
    private Axioms axioms;
    private String tooltip;
    private String readAs;

    public StringSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(StringSymbol symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Axioms getAxioms() {
        return axioms;
    }

    public void setAxioms(Axioms axioms) {
        this.axioms = axioms;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getReadAs() {
        return readAs;
    }

    public void setReadAs(String readAs) {
        this.readAs = readAs;
    }

    public class StringSymbol {
        String applied;
        List<String> allSymbols;

        public String getApplied() {
            return applied;
        }

        public void setApplied(String applied) {
            this.applied = applied;
        }

        public List<String> getAllSymbols() {
            return allSymbols;
        }

        public void setAllSymbols(List<String> allSymbols) {
            this.allSymbols = allSymbols;
        }

        @Override
        public String toString() {
            return "StringSymbol{" +
                    "applied='" + applied + '\'' +
                    ", allSymbols=" + allSymbols +
                    '}';
        }
    }

    public class Axioms {
        String explanation;
        List<String> example;

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public List<String> getExample() {
            return example;
        }

        public void setExample(List<String> example) {
            this.example = example;
        }

        @Override
        public String toString() {
            return "Axioms{" +
                    "explanation='" + explanation + '\'' +
                    ", example='" + example + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbol=" + symbol +
                ", description='" + description + '\'' +
                ", axioms=" + axioms +
                ", tooltip='" + tooltip + '\'' +
                ", readAs='" + readAs + '\'' +
                '}';
    }
}

