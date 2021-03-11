package com.llat.algorithms.propositional;

import com.llat.models.treenode.AtomNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.WffTree;

import java.util.*;

/**
 *
 */
public final class TruthTableGenerator {

    /**
     *
     */
    private WffTree wffTree;

    /**
     *
     */
    private HashMap<String, Integer> truthPattern;

    /**
     *
     */
    private Stack<WffTree> operands;

    /**
     *
     */
    private int size;

    /**
     *
     */
    private int rows;

    public TruthTableGenerator(WffTree _wffTree) {
        this.wffTree = _wffTree;
        this.operands = new Stack<>();
        this.truthPattern = new HashMap<>();
        this.size = getAtomCount(this.wffTree);
        this.rows = (int) Math.pow(2, this.size);
    }

    /**
     * Builds the truth table for the supplied tree. Each node
     * has a LinkedList of truth values associated with it, and
     * these are set as the tree is built. So, this function does
     * not return anything. Once this method is called, the tree
     * will need to be traversed again to find the truth values.
     * To get just the truth table values for the entire tree,
     * call getTruthValues() on the main operator. This process can
     * also be done on each subtree for easy printing.
     */
    public void get() {
        this.buildTable(this.wffTree);
        this.print(this.wffTree);
    }

    /**
     * Prints out the truth values for a WffTree in pre-order fashion.
     *
     * @param tree - root of WffTree to print.
     */
    private void print(WffTree tree) {
        for (WffTree ch : tree.getChildren()) {
            System.out.println(ch + ": " + ch.getTruthValues());
            print(ch);
        }
    }

    /**
     * Recursively builds the truth table in a postfix traversal of the tree,
     * meaning the leaves are processed before an operator, guaranteeing the
     * invariant that there is always one node in the operand stack when a negation
     * operator is found, and at least two nodes are in the stack when a binary
     * operator is found.
     *
     * @param tree - root of WffTree.
     */
    private void buildTable(WffTree tree) {
        for (int i = 0; i < tree.getChildrenSize(); i++) {
            WffTree child = tree.getChild(i);
            this.buildTable(child);
        }
        this.processNode(tree);
    }

    /**
     * Processes a node for truth table updating via the postfix tree traversal.
     *
     * If the node passed is an atom, we check to see if it is has already been
     * seen. If not, we compute a "pattern" for it, representing the number of
     * times we use a truth value before flipping. For instance, if pattern = 2,
     * we flip every other boolean. So, the pattern is true, true, false, false.
     *
     * If the node passed is a negation operator, we pop the operand currently
     * on the stack (the invariant holds that there is at least one WffTree on the
     * stack), and apply the negation to it.
     *
     * If the node passed is a binary operator, we pop two operands currently
     * on the stack (the invariatn holds that there are at least two WffTrees on
     * the stack), and apply the operator to them.
     *
     * @param tree - WffTree node.
     */
    private void processNode(WffTree tree) {
        if (tree.isAtom()) {
            AtomNode atom = (AtomNode) tree;
            if (!this.truthPattern.containsKey(atom.getSymbol())) {
                int pattern = (int) Math.pow(2, this.truthPattern.size());
                this.truthPattern.put(atom.getSymbol(), pattern);
            }

            initializeAtomTruthValues((AtomNode) tree);
        } else if (tree.isNegation()) {
            WffTree operand = this.operands.pop();
            populateNodeTruthValues(operand, null, tree);
        } else if (!tree.isRoot()) {
            WffTree operand1 = this.operands.pop();
            WffTree operand2 = this.operands.pop();
            populateNodeTruthValues(operand1, operand2, tree);
        }

        if (!tree.isRoot()) {
            this.operands.add(tree);
        }
    }

    /**
     * Initializes an atom to its default truth values. These depend
     * on how many atoms there are in the well-formed formula. It goes
     * from 1, 2, 4, ..., 2^n. This number indicates the pattern of
     * alternating true/false booleans. So, if pattern = 2, and n = 2,
     * it will have a starting truth value of true,true,false, false.
     *
     * @param tree - AtomNode that we are initializing truth values for.
     */
    private void initializeAtomTruthValues(AtomNode tree) {
        boolean value = true;
        int pattern = this.truthPattern.get(tree.getSymbol());
        for (int i = 1; i <= this.rows; i++) {
            tree.getTruthValues().add(value);
            if (i % pattern == 0) {
                value = !value;
            }
        }
    }

    /**
     * Sets the truth table values for a node in the table. There are two possibilities
     * when calling this method:
     *
     * 1) operand2 is null - meaning that operand1 must be a WffTree, and op must be a
     *    negation operator. If this is not the case, an exception is thrown.
     * 2) All three trees are non-null. This means that op is a binary operator, being
     *    applied to two sub-wffs.
     *
     * @param operand1 - first wff.
     * @param operand2 - second wff - can be null.
     * @param op - operator to perform - if operand2 == null, this is a NegNode.
     */
    private void populateNodeTruthValues(WffTree operand1, WffTree operand2, WffTree op) {
        // If the second operand is null, this has to be a negation op.
        if (operand2 == null) {
            if (!(op instanceof NegNode)) {
                throw new IllegalArgumentException("operand2 is null, so op must be a negation node.");
            }
            // Iterate through and flip all the boolean values.
            for (int i = 0; i < this.rows; i++) {
                boolean op1Bool = operand1.getTruthValues().get(i);
                op.setTruthValue(!op1Bool, i);
            }
        } else {
            // Otherwise, get the two operands and perform the corresponding
            // logical operator.
            for (int i = 0; i < this.rows; i++) {
                boolean op1Bool = operand1.getTruthValues().get(i);
                boolean op2Bool = operand2.getTruthValues().get(i);

                if (op.isAnd()) {
                    op.setTruthValue(logicalAnd(op1Bool, op2Bool), i);
                } else if (op.isOr()) {
                    op.setTruthValue(logicalOr(op1Bool, op2Bool), i);
                } else if (op.isImp()) {
                    op.setTruthValue(logicalImp(op1Bool, op2Bool), i);
                } else if (op.isBicond()) {
                    op.setTruthValue(logicalBicond(op1Bool, op2Bool), i);
                }
            }
        }
    }

    /**
     * Counts the number of unique atoms in a propositional logic formula. This is
     * counted in a bfs fashion.
     *
     * @param tree - propositional logic wff root.
     * @return number of unique atoms found.
     */
    private static int getAtomCount(WffTree tree) {
        Queue<WffTree> q = new LinkedList<>();
        HashSet<String> atoms = new HashSet<>();
        q.add(tree);

        while (!q.isEmpty()) {
            WffTree wt = q.poll();
            for (WffTree ch : wt.getChildren()) {
                if (ch.isAtom()) {
                    String symbol = ((AtomNode) ch).getSymbol();
                    atoms.add(symbol);
                } else {
                    q.add(ch);
                }
            }
        }

        return atoms.size();
    }

    /**
     * Performs a logical conjunction (AND) (&&) on two boolean values, meaning that
     * both values must be true to return true.
     *
     * @param _a
     * @param _b
     * @return logical AND operation result.
     */
    private static boolean logicalAnd(boolean _a, boolean _b) {
        return _a && _b;
    }

    /**
     * Performs a logical disjunction (OR) (||) on two boolean values, meaning that
     * at least value values must be true to return true.
     *
     * @param _a
     * @param _b
     * @return logical OR operation result.
     */
    private static boolean logicalOr(boolean _a, boolean _b) {
        return _a || _b;
    }

    /**
     * Performs a logical exclusive or (XOR) (^) on two boolean values, meaning that
     * both operands must be different to be true.
     *
     * @param _a
     * @param _b
     * @return logical XOR operation result.
     */
    private static boolean logicalXOr(boolean _a, boolean _b) {
        return _a ^ _b;
    }

    /**
     * Performs a logical implication (if-then) (->) on two boolean values, meaning that
     * if _a is true and _b is false, we return false. This otherwise returns
     * true. We use the logically equivalent form of NOT A OR B to compute this.
     *
     * @param _a
     * @param _b
     * @return logical implication operation result.
     */
    private static boolean logicalImp(boolean _a, boolean _b) {
        return !_a || _b;
    }

    /**
     * Performs a logical biconditional (iff) (&&) on two boolean values, meaning that
     * both values must be the same to return true. This is simply the negation of
     * the logical exclusive-or, so we use this instead of computing it manually.
     *
     * @param _a
     * @param _b
     * @return logical AND operation result.
     */
    private static boolean logicalBicond(boolean _a, boolean _b) {
        return !logicalXOr(_a, _b);
    }
}
