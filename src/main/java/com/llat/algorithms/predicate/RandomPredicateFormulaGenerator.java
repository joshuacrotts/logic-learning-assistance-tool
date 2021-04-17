package com.llat.algorithms.predicate;

import com.llat.tools.LLATUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RandomPredicateFormulaGenerator {

    /**
     *
     */
    private final Map<Character, Integer> predicateArityMap;

    /**
     *
     */
    private final ArrayList<Character> predicatesNotUsed;

    /**
     *
     */
    private final ArrayList<Character> predicatesUsed;

    /**
     *
     */
    private final ArrayList<Character> variablesUsed;

    /**
     *
     */
    private final ArrayList<Character> variablesNotUsed;

    /**
     *
     */
    private final ArrayList<Character> constantsUsed;

    /**
     *
     */
    private final ArrayList<Character> constantsNotUsed;

    /**
     *
     */
    private final ArrayList<String> binaryOperators;

    /**
     *
     */
    private final double predicateProbability = 1.0;

    /**
     *
     */
    private final double predicateDeltaProbability = 0.10;

    private final double usedPredicateProbability = 0.25;
    private final double negDeltaProbability = 0.20;
    private final double binopDeltaProbability = 0.50;
    private final double quantifierDeltaProbability = 0.25;
    private double negProbability = 0.50;
    private double binopProbability = 1.0;
    private double quantifierProbability = 0.50;

    public RandomPredicateFormulaGenerator() {
        this.predicatesNotUsed = new ArrayList<>();
        this.predicatesUsed = new ArrayList<>();
        this.variablesNotUsed = new ArrayList<>();
        this.variablesUsed = new ArrayList<>();
        this.constantsNotUsed = new ArrayList<>();
        this.constantsUsed = new ArrayList<>();
        this.predicateArityMap = new HashMap<>();
        this.binaryOperators = new ArrayList<>();

        // First populate the list of predicates.
        for (char c = 'A'; c <= 'Z'; c++) {
            this.predicatesNotUsed.add(c);
        }

        // Now populate the list of constants.
        for (char c = 'a'; c <= 't'; c++) {
            this.constantsNotUsed.add(c);
        }

        // Finally, populate the list of variables.
        for (char c = 'u'; c <= 'z'; c++) {
            this.variablesNotUsed.add(c);
        }

        // Now add all binary ops.
        this.binaryOperators.add("->");
        this.binaryOperators.add("<->");
        this.binaryOperators.add("&");
        this.binaryOperators.add("∨");
    }

    public static void main(String[] args) {
        RandomPredicateFormulaGenerator gen = new RandomPredicateFormulaGenerator();
        System.out.println(gen.genRandomPredicateFormula());
    }

    /**
     * @return
     */
    public String genRandomPredicateFormula() {
        StringBuilder formula = new StringBuilder();
        this.genWff(formula);
        return formula.toString();
    }

    /**
     * @param _sb
     */
    private void genWff(StringBuilder _sb) {
        double p = Math.random();
        if (p < this.negProbability) {
            this.genNeg(_sb);
            this.genWff(_sb);
        } else if (p < this.quantifierProbability) {
            this.genQuantifier(_sb);
            this.genWff(_sb);
        } else if (p < this.binopProbability) {
            this.genBinaryOp(_sb);
        } else {
            this.genPredicate(_sb);
        }
    }

    /**
     * @param _sb
     */
    private void genNeg(StringBuilder _sb) {
        _sb.append("~");
        this.negProbability -= this.negDeltaProbability;
    }

    /**
     * @param _sb
     */
    private void genQuantifier(StringBuilder _sb) {
        this.quantifierProbability -= this.quantifierDeltaProbability;
        if (Math.random() < this.negProbability) {
            this.genNeg(_sb);
        }

        // Either generate the existential or the universal...
        if (Math.random() < 0.5) {
            _sb.append("(∃");
            _sb.append(this.getRandomVariable());
            _sb.append(")");
        } else {
            _sb.append("(");
            _sb.append(this.getRandomVariable());
            _sb.append(")");
        }
    }

    /**
     * @param _sb
     */
    private void genBinaryOp(StringBuilder _sb) {
        this.binopProbability -= this.binopDeltaProbability;
        _sb.append("(");

        // LHS Wff.
        this.genWff(_sb);

        // Binary op.
        _sb.append(" ");
        _sb.append(this.getRandomBinaryOp());
        _sb.append(" ");

        // RHS Wff.
        this.genWff(_sb);
        _sb.append(")");
    }

    /**
     * @param _sb
     */
    private void genPredicate(StringBuilder _sb) {
        char p;
        double usedPredicateProbability = 0.5;
        StringBuilder predicate = new StringBuilder();

        if ((Math.random() < usedPredicateProbability && !this.predicatesNotUsed.isEmpty()) || this.predicatesUsed.isEmpty()) {
            p = this.predicatesNotUsed.remove(LLATUtils.randomInt(this.predicatesNotUsed.size() - 1));
            this.predicatesUsed.add(p);
        } else {
            p = this.predicatesUsed.get(LLATUtils.randomInt(this.predicatesUsed.size() - 1));
        }

        Integer mapArity = this.predicateArityMap.get(p);
        int terminals = mapArity != null ? mapArity : ((int) (Math.random() * 4 + 1));
        this.predicateArityMap.put(p, terminals);
        predicate.append(p);

        for (int i = 0; i < terminals; i++) {
            char t;
            if (Math.random() < 0.5) {
                t = this.getRandomConstant();
            } else {
                t = this.getRandomVariable();
            }
            predicate.append(t);
        }

        _sb.append(predicate);
    }

    /**
     * @return
     */
    private Character getRandomVariable() {
        char v;
        double usedVariableProbability = 0.5;

        if ((Math.random() < usedVariableProbability && !this.variablesNotUsed.isEmpty()) || this.variablesUsed.isEmpty()) {
            v = this.variablesNotUsed.remove(LLATUtils.randomInt(this.variablesNotUsed.size() - 1));
            this.variablesUsed.add(v);
        } else {
            v = this.variablesUsed.get(LLATUtils.randomInt(this.variablesUsed.size() - 1));
        }

        return v;
    }

    /**
     * @return
     */
    private Character getRandomConstant() {
        char c;
        double usedConstantProbability = 0.5;

        if ((Math.random() < usedConstantProbability && !this.constantsNotUsed.isEmpty()) || this.constantsUsed.isEmpty()) {
            c = this.constantsNotUsed.remove(LLATUtils.randomInt(this.constantsNotUsed.size() - 1));
            this.constantsUsed.add(c);
        } else {
            c = this.constantsUsed.get(LLATUtils.randomInt(this.constantsUsed.size() - 1));
        }

        return c;
    }

    /**
     * @return
     */
    private String getRandomBinaryOp() {
        return this.binaryOperators.get(LLATUtils.randomInt(this.binaryOperators.size() - 1));
    }
}
