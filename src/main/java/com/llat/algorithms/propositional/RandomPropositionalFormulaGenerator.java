package com.llat.algorithms.propositional;

import java.util.ArrayList;

/**
 *
 */
public class RandomPropositionalFormulaGenerator {

    /**
     * Set of atoms that we have currently used in the
     * formula generation.
     */
    private ArrayList<Character> atomsUsed;

    /**
     * Set of atoms that we have not used in the formula
     * generation.
     */
    private ArrayList<Character> atomsNotUsed;

    /**
     * Set of all unary operators that we can use.
     */
    private ArrayList<String> unaryOperators;

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

    /**
     * Generates a random propositional logic formula. Its randomness is a little subjective
     * due to the algorithm used, but it is good for generating simple and decidable propositional
     * logic formulas, just for practice.
     * <p>
     * The algorithm uses a series of probabilities to determine when to stop adding operators
     * and operands, as described below:
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
    public RandomPropositionalFormulaGenerator(double _atomProbability,
                                               double _atomDeltaProbability, double _usedAtomProbability,
                                               double _wffProbability, double _wffDeltaProbability,
                                               double _negProbability, double _negDeltaProbability,
                                               double _binopProbability, double _binopDeltaProbability) {
        this.atomProbability = _atomProbability;
        this.atomDeltaProbability = _atomDeltaProbability;
        this.usedAtomProbability = _usedAtomProbability;
        this.wffProbability = _wffProbability;
        this.wffDeltaProbability = _wffDeltaProbability;
        this.negProbability = _negProbability;
        this.negDeltaProbability = _negDeltaProbability;
        this.binopProbability = _binopProbability;
        this.binopDeltaProbability = _binopDeltaProbability;
    }

    /**
     * Generates a random propositional logic formula.
     * <p>
     * Its randomness is a little subjective due to the algorithm used, but it is good for generating simple and decidable propositional
     * logic formulas, just for practice. The probabilities for the default constructor are set up to generate wffs that contain between
     * 0 and 3 binary operators.
     */
    public RandomPropositionalFormulaGenerator() {

    }

    public static void main(String[] args) {
        RandomPropositionalFormulaGenerator r = new RandomPropositionalFormulaGenerator();
        System.out.println(r.genRandomPropositionalFormula());
    }

    /**
     * Generates the random propositional formula.
     *
     * @return string representation of generated formula.
     */
    public String genRandomPropositionalFormula() {
        this.reset();
        StringBuilder wff = new StringBuilder();
        this.genWff(wff, this.atomProbability, this.atomDeltaProbability, this.usedAtomProbability,
                this.wffProbability, this.wffDeltaProbability, this.negProbability,
                this.negDeltaProbability, this.binopProbability, this.binopDeltaProbability);
        return wff.toString();
    }

    /**
     *
     */
    private void reset() {
        this.atomsUsed = new ArrayList<>();
        this.atomsNotUsed = new ArrayList<>();
        this.unaryOperators = new ArrayList<>();
        this.binaryOperators = new ArrayList<>();

        // Add all atoms.
        for (char c = 'A'; c <= 'Z'; c++) {
            this.atomsNotUsed.add(c);
        }

        // Now add the unary operators.
        this.unaryOperators.add("~");

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
                        double _binopProbability, double _binopDeltaProbability) {
        // Generates a negation node.
        if (_negProbability > Math.random()) {
            this.genNeg(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                    _wffProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                    _binopProbability, _binopDeltaProbability);
        }
        // Otherwise, try to generate an atom.
        else if (_atomProbability < 0.33) {
            _sb.append(this.genRandomAtom(_usedAtomProbability));
        }
        // Otherwise... try a binary op.
        else if (_binopProbability > Math.random()) {
            this.genBinaryOp(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                    _wffProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                    _binopProbability - _binopDeltaProbability, _binopDeltaProbability);
        }
        // Finally, just settle for an atom.
        else {
            _sb.append(this.genRandomAtom(_usedAtomProbability));
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
                        double _binopProbability, double _binopDeltaProbability) {
        _sb.append("~");
        this.genWff(_sb, _atomProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability, _wffDeltaProbability, _negProbability - _negDeltaProbability,
                _negDeltaProbability, _binopProbability, _binopDeltaProbability);
    }

    /**
     * @param _sb
     */
    private void genBinaryOp(StringBuilder _sb, double _atomProbability,
                             double _atomDeltaProbability, double _usedAtomProbability,
                             double _wffProbability, double _wffDeltaProbability,
                             double _negProbability, double _negDeltaProbability,
                             double _binopProbability, double _binopDeltaProbability) {
        _sb.append("(");
        // Left wff.
        this.genWff(_sb, _atomProbability - _atomDeltaProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability - _wffDeltaProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                _binopProbability, _binopDeltaProbability);

        // Binary op.
        _sb.append(" ");
        _sb.append(this.getRandomBinaryOp());
        _sb.append(" ");

        // Right wff.
        this.genWff(_sb, _atomProbability - _atomDeltaProbability, _atomDeltaProbability, _usedAtomProbability,
                _wffProbability - _wffDeltaProbability, _wffDeltaProbability, _negProbability, _negDeltaProbability,
                _binopProbability, _binopDeltaProbability);
        _sb.append(")");
    }

    /**
     * @return
     */
    private Character genRandomAtom(double _usedAtomProbability) {
        char atom;
        if (_usedAtomProbability < Math.random() || this.atomsUsed.isEmpty()) {
            atom = this.atomsNotUsed.remove((int) (Math.random() * this.atomsNotUsed.size()));
            this.atomsUsed.add(atom);
            return atom;
        }

        return this.atomsUsed.get((int) (Math.random() * this.atomsUsed.size()));
    }

    /**
     * @return
     */
    private String getRandomBinaryOp() {
        return this.binaryOperators.get((int) (Math.random() * this.binaryOperators.size()));
    }
}
