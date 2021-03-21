package com.llat.models.uidescription;

import com.llat.models.gson.GsonInterface;

import java.util.List;

public class UIDescriptionObject implements GsonInterface {

    private Implication implication;
    private Equivalence equivalence;
    private Negation negation;
    private Conjunction conjunction;
    private Disjunction disjunction;
    private ExclusiveDisjunction exclusiveDisjunction;
    private Turnstile turnstile;
    private DoubleTurnstile doubleTurnstile;
    private Universal universal;
    private Existential existential;

    public UIDescriptionObject(Implication implication, Equivalence equivalence, Negation negation,
                               Conjunction conjunction, Disjunction disjunction, ExclusiveDisjunction exclusiveDisjunction,
                               Turnstile turnstile, DoubleTurnstile doubleTurnstile, Universal universal, Existential existential) {
        this.implication = implication;
        this.equivalence = equivalence;
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
        return implication;
    }

    public void setImplication(Implication implication) {
        this.implication = implication;
    }

    public Equivalence getEquivalence() {
        return equivalence;
    }

    public void setEquivalence(Equivalence equivalence) {
        this.equivalence = equivalence;
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

    public Universal getUniversal() {
        return universal;
    }

    public void setUniversal(Universal universal) {
        this.universal = universal;
    }

    public Existential getExistential() {
        return existential;
    }

    public void setExistential(Existential existential) {
        this.existential = existential;
    }

    public class Implication {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Equivalence {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Negation {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Conjunction {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Disjunction {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class ExclusiveDisjunction {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Turnstile {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class DoubleTurnstile {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Universal {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

    public class Existential {
        private Symbol symbol;
        private String description;
        private Axioms axioms;
        private String tooltip;
        private String readAs;

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public class Symbol {
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
        }

        public class Axioms {
            String explanation;
            String example;

            public String getExplanation() {
                return explanation;
            }

            public void setExplanation(String explanation) {
                this.explanation = explanation;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }
}
