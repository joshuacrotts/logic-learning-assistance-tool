package com.llat.algorithms.predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RandomPredicateFormulaGenerator {

    /**
     * Set of predicates that we have currently used in the
     * formula generation.
     */
    private ArrayList<Character> predicatesUsed;

    /**
     *
     */
    private Map<Character, Integer> predicateArityMap;

    /**
     * Set of predicates that we have not used in the formula
     * generation.
     */
    private ArrayList<Character> predicatesNotUsed;

    /**
     * Set of terminals (constants & variables) that we have currently used in the
     * formula generation.
     */
    private ArrayList<Character> terminalsUsed;

    /**
     * Set of terminals (constants & variables) that we have not used in the formula
     * generation.
     */
    private ArrayList<Character> terminalsNotUsed;

    /**
     *
     */
    private int variables;

    /**
     * Set of all binary operators that we can use.
     */
    private ArrayList<String> binaryOperators;

    private double atomProbability = 1.0;
    private double atomDeltaProbability = 0.10;
    private double usedAtomProbability = 0.25;
    private double wffProbability = 0.75;
    private double wffDeltaProbability = 0.50;
    private double negProbability = 0.50;
    private double negDeltaProbability = 0.20;
    private double binopProbability = 1.0;
    private double binopDeltaProbability = 0.50;
    private double quantifierProbability = 0.50;
    private double quantifierDeltaProbability = 0.25;

    /**
     * Generates a random predicate logic formula.
     * <p>
     * The problem of generating wffs is an open problem in computer science, so this very loose
     * approximation won't win any awards, but the idea is to use probabilities to arbitrarily choose
     * the next step in the grammar. This will not always generate decidable wffs like propositional
     * logic, so be aware!
     *
     * @param _atomProbability       - The starting probability that a wff will be substituted with an atom.
     *                               Recall that atoms are terminals in the grammar.
     * @param _atomDeltaProbability  - The rate at which the likelihood of atoms terminating a wff increases.
     * @param _usedAtomProbability   - The probability that the algorithm will pick an atom that we have already used.
     * @param _wffProbability        - The starting probability that a Wff will be selected. A wff leads to all other non terminal rules.
     * @param _wffDeltaProbability   - The rate at which the likelihood of a wff being selected decreases.
     * @param _negProbability        - The starting probability that a negation will be selected.
     * @param _negDeltaProbability   - The rate at which the likelihood of a negation being selected decreases.
     * @param _binopProbability      - The starting probability that a binary operator wff will be selected.
     * @param _binopDeltaProbability - The rate at which the likelihood of a binary operator being selected decreases.
     */
    public RandomPredicateFormulaGenerator(double _atomProbability,
                                           double _atomDeltaProbability, double _usedAtomProbability,
                                           double _wffProbability, double _wffDeltaProbability,
                                           double _negProbability, double _negDeltaProbability,
                                           double _binopProbability, double _binopDeltaProbability,
                                           double _quantifierProbability, double _quantifierDeltaProbability) {
        this.atomProbability = _atomProbability;
        this.atomDeltaProbability = _atomDeltaProbability;
        this.usedAtomProbability = _usedAtomProbability;
        this.wffProbability = _wffProbability;
        this.wffDeltaProbability = _wffDeltaProbability;
        this.negProbability = _negProbability;
        this.negDeltaProbability = _negDeltaProbability;
        this.binopProbability = _binopProbability;
        this.binopDeltaProbability = _binopDeltaProbability;
        this.quantifierProbability = _quantifierProbability;
        this.quantifierDeltaProbability = _quantifierDeltaProbability;
    }

    /**
     * Generates a random predicate logic formula.
     * <p>
     * ...
     */
    public RandomPredicateFormulaGenerator() {

    }

    public static void main(String[] args) {
        RandomPredicateFormulaGenerator r = new RandomPredicateFormulaGenerator();
        System.out.println(r.genRandomPredicateFormula());
    }

    /**
     * @return
     */
    public String genRandomPredicateFormula() {
        this.reset();
        StringBuilder wff = new StringBuilder();
        this.genWff(wff, this.atomProbability, this.atomDeltaProbability, this.usedAtomProbability,
                this.wffProbability, this.wffDeltaProbability, this.negProbability,
                this.negDeltaProbability, this.binopProbability, this.binopDeltaProbability,
                this.quantifierProbability, this.quantifierDeltaProbability);
        return wff.toString();
    }

    /**
     *
     */
    private void reset() {
        this.predicatesUsed = new ArrayList<>();
        this.predicatesNotUsed = new ArrayList<>();
        this.terminalsUsed = new ArrayList<>();
        this.terminalsNotUsed = new ArrayList<>();
        this.predicateArityMap = new HashMap<>();
        this.binaryOperators = new ArrayList<>();
        this.variables = 0;

        // Add all atoms.
        for (char c = 'A'; c <= 'Z'; c++) {
            this.predicatesNotUsed.add(c);
            this.terminalsNotUsed.add((char) (c + 32));
        }

        // Now add all binary ops.
        this.binaryOperators.add("->");
        this.binaryOperators.add("<->");
        this.binaryOperators.add("&");
        this.binaryOperators.add("∨");
    }

    /**
     * Generates a wff rule.
     * <p>
     * This method is almost identical to the ANTLR grammar rule to produce
     * rules that will always pass the parser.
     */
    private void genWff(StringBuilder _sb, double _atomProbability,
                        double _atomDeltaProbability, double _usedAtomProbability,
                        double _wffProbability, double _wffDeltaProbability,
                        double _negProbability, double _negDeltaProbability,
                        double _binopProbability, double _binopDeltaProbability,
                        double _quantifierProbability, double _quantifierDeltaProbability) {
        // Generates a negation node.
        if (_negProbability > Math.random()) {
            this.genNeg(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                    _wffProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                    _binopProbability, _binopDeltaProbability, _quantifierProbability, _quantifierDeltaProbability);
        }
        // Generate a quantifier.
        else if (_quantifierProbability > Math.random()) {
            this.genQuantifier(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability, _wffProbability,
                    _wffDeltaProbability, _negProbability, _negDeltaProbability, _binopProbability, _binopDeltaProbability,
                    _quantifierProbability, _quantifierDeltaProbability);
        }
        // Otherwise, try to generate an atom.
        else if (_atomProbability < 0.33) {
            _sb.append(this.genRandomPredicate(_usedAtomProbability));
        }
        // Otherwise... try a binary op.
        else if (_binopProbability > Math.random()) {
            this.genBinaryOp(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                    _wffProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                    _binopProbability - _binopDeltaProbability, _binopDeltaProbability,
                    _quantifierProbability, _quantifierDeltaProbability);
        }
        // Finally, just settle for an atom.
        else {
            _sb.append(this.genRandomPredicate(_usedAtomProbability));
        }
    }

    /**
     * @param _sb
     * @return
     */
    private void genNeg(StringBuilder _sb, double _atomProbability,
                        double _atomDeltaProbability, double _usedAtomProbability,
                        double _wffProbability, double _wffDeltaProbability,
                        double _negProbability, double _negDeltaProbability,
                        double _binopProbability, double _binopDeltaProbability,
                        double _quantifierProbability, double _quantifierDeltaProbability) {
        _sb.append("~");
        this.genWff(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability, _wffDeltaProbability, _negProbability - _negDeltaProbability,
                _negDeltaProbability, _binopProbability, _binopDeltaProbability,
                _quantifierProbability, _quantifierDeltaProbability);
    }

    /**
     * @param _sb
     * @return
     */
    private void genQuantifier(StringBuilder _sb, double _atomProbability,
                               double _atomDeltaProbability, double _usedAtomProbability,
                               double _wffProbability, double _wffDeltaProbability,
                               double _negProbability, double _negDeltaProbability,
                               double _binopProbability, double _binopDeltaProbability,
                               double _quantifierProbability, double _quantifierDeltaProbability) {
        if (_negProbability > Math.random()) {
            this.genNeg(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                    _wffProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                    _binopProbability, _binopDeltaProbability, _quantifierProbability, _quantifierDeltaProbability);
        }

        int idx = (int) (Math.random() * ((this.terminalsNotUsed.size() - 19 - this.variables) + 1) + 19 - this.variables);
        this.variables++;

        // Either generate the existential or the universal...
        if (Math.random() < 0.5) {
            _sb.append("(∃");
            _sb.append(this.terminalsNotUsed.get(idx));
            _sb.append(")");
        } else {
            _sb.append("(");
            _sb.append(this.terminalsNotUsed.get(idx));
            _sb.append(")");
        }

        this.genWff(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability, _wffDeltaProbability, _negProbability - _negDeltaProbability,
                _negDeltaProbability, _binopProbability, _binopDeltaProbability,
                _quantifierProbability - _quantifierDeltaProbability,
                _quantifierDeltaProbability);
    }

    /**
     * @param _sb
     */
    private void genBinaryOp(StringBuilder _sb, double _atomProbability,
                             double _atomDeltaProbability, double _usedAtomProbability,
                             double _wffProbability, double _wffDeltaProbability,
                             double _negProbability, double _negDeltaProbability,
                             double _binopProbability, double _binopDeltaProbability,
                             double _quantifierProbability, double _quantifierDeltaProbability) {
        _sb.append("(");
        // Left wff.
        this.genWff(_sb, _atomProbability - _atomDeltaProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability - _wffDeltaProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                _binopProbability, _binopDeltaProbability, _quantifierProbability, _quantifierDeltaProbability);

        // Binary op.
        _sb.append(this.getRandomBinaryOp());

        // Right wff.
        this.genWff(_sb, _atomProbability - _atomDeltaProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability - _wffDeltaProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                _binopProbability, _binopDeltaProbability, _quantifierProbability, _quantifierDeltaProbability);
        _sb.append(")");
    }

    /**
     * @return
     */
    private String genRandomPredicate(double _usedPredicateProbability) {
        char p;
        String predicate = "";

        if (_usedPredicateProbability < Math.random() || this.predicatesUsed.isEmpty()) {
            p = this.predicatesNotUsed.remove((int) (Math.random() * this.predicatesNotUsed.size()));
            this.predicatesUsed.add(p);
        } else {
            p = this.predicatesUsed.get((int) (Math.random() * this.predicatesUsed.size()));
        }

        Integer mapArity = this.predicateArityMap.get(p);
        int terminals = mapArity != null ? mapArity : ((int) (Math.random() * 4 + 1));
        this.predicateArityMap.put(p, terminals);
        predicate += p;

        for (int i = 0; i < terminals; i++) {
            char t;
            if (_usedPredicateProbability < Math.random() || this.terminalsUsed.isEmpty()) {
                t = this.terminalsNotUsed.remove((int) (Math.random() * this.terminalsNotUsed.size()));
                this.terminalsUsed.add(t);
            } else {
                t = this.terminalsUsed.get((int) (Math.random() * this.terminalsUsed.size()));
            }

            predicate += t;
        }

        return predicate;
    }

    /**
     * @return
     */
    private String getRandomBinaryOp() {
        return this.binaryOperators.get((int) (Math.random() * this.binaryOperators.size()));
    }
}
