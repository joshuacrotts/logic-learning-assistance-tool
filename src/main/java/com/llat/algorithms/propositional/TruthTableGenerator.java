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
     * Maximum number of atoms that this algorithm can realistically handle before taking an eternity
     * to generate the truth table.
     */
    private static final int MAX_ATOMS = 14;

    /**
     * Root of WffTree to generate the truth table for.
     */
    private final WffTree wffTree;

    /**
     * Alternating pattern of truth values for the atoms.
     */
    private final LinkedHashMap<String, Integer> truthPattern;

    /**
     * Stack of operands and operators to recursively evaluate when building the tree.
     */
    private final Stack<WffTree> operands;

    /**
     * The number of atoms in this truth tree.
     */
    private final int size;

    /**
     * The number of rows for this truth tree. Equal to 2^(size).
     */
    private final int rows;

    public TruthTableGenerator(WffTree _wffTree) {
        this.wffTree = _wffTree;
        this.operands = new Stack<>();
        this.truthPattern = new LinkedHashMap<>();
        this.size = getAtomCount(this.wffTree);
        this.rows = (int) Math.pow(2, this.size);

        // We want to clear the tree every time so we don't get duplicate values.
        this.clearWffTree();

        // Calling it here just makes more sense...
        this.get();
    }

    /**
     * Clears all truth values from this WffTree.
     */
    public void clearWffTree() {
        Queue<WffTree> queue = new LinkedList<>();
        queue.add(this.wffTree);

        while (!queue.isEmpty()) {
            WffTree tree = queue.poll();
            tree.getTruthValues().clear();
            for (WffTree ch : tree.getChildren()) {
                queue.add(ch);
            }
         }
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
        if (this.size > MAX_ATOMS) {
            System.out.println("This formula is too complex to build a truth table for.");
            return;
        }
        this.buildTable(this.wffTree);
    }

    /**
     * Prints out the truth values for a WffTree in pre-order fashion.
     */
    public void print() {
        printHelper();
    }

    /**
     * Performs a post-order traversal of the WffTree. We do this to get the respective truth values.
     *
     * To access these values, iterate over the list returned by this method, and do node.getTruthValues().
     *
     * @return list of nodes in post-order.
     */
    public LinkedHashSet<WffTree> postorder() {
        LinkedHashSet<WffTree> list = new LinkedHashSet<>();
        this.postorderHelper(this.wffTree.getChild(0), list);
        return list;
    }

    /**
     * Function to print out the truth tree in a "table" fashion in the console. This, however, does not print
     * the atoms at the start of the truth table.
     */
    private void printHelper() {
        LinkedList<WffTree> postorderTraversal = new LinkedList<>(this.postorder());
        int maxWidth = postorderTraversal.get(postorderTraversal.size() - 1).getStringRep().length();
        for (WffTree tree : postorderTraversal) {
            System.out.printf("%-" + maxWidth + "s : ", tree.getStringRep());
            System.out.println(tree.getTruthValues());
        }
    }

    /**
     * Performs a recursive post-order traversal on the WffTree.
     *
     * @param _tree - tree to search.
     * @param _postorderList - list to continuously add to.
     */
    private void postorderHelper(WffTree _tree, HashSet<WffTree> _postorderList) {
        if (_tree == null) {
            return;
        }

        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            this.postorderHelper(_tree.getChild(i), _postorderList);
        }

        _postorderList.add(_tree);
    }

    /**
     * Recursively builds the truth table in a postfix traversal of the tree,
     * meaning the leaves are processed before an operator, guaranteeing the
     * invariant that there is always one node in the operand stack when a negation
     * operator is found, and at least two nodes are in the stack when a binary
     * operator is found.
     *
     * @param _tree - root of WffTree.
     */
    private void buildTable(WffTree _tree) {
        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            WffTree child = _tree.getChild(i);
            this.buildTable(child);
        }
        this.processNode(_tree);
    }

    /**
     * Processes a node for truth table updating via the postfix tree traversal.
     * <p>
     * If the node passed is an atom, we check to see if it is has already been
     * seen. If not, we compute a "pattern" for it, representing the number of
     * times we use a truth value before flipping. For instance, if pattern = 2,
     * we flip every other boolean. So, the pattern is true, true, false, false.
     * <p>
     * If the node passed is a negation operator, we pop the operand currently
     * on the stack (the invariant holds that there is at least one WffTree on the
     * stack), and apply the negation to it.
     * <p>
     * If the node passed is a binary operator, we pop two operands currently
     * on the stack (the invariant holds that there are at least two WffTrees on
     * the stack), and apply the operator to them.
     *
     * @param _tree - WffTree node.
     */
    private void processNode(WffTree _tree) {
        if (_tree.isAtom()) {
            AtomNode atom = (AtomNode) _tree;
            if (!this.truthPattern.containsKey(atom.getSymbol())) {
                // We need to go from largest to smallest.
                int pattern = (int) Math.pow(2, this.size - this.truthPattern.size() - 1);
                this.truthPattern.put(atom.getSymbol(), pattern);
            }

            initializeAtomTruthValues((AtomNode) _tree);
        } else if (_tree.isNegation()) {
            WffTree operand = this.operands.pop();
            populateNodeTruthValues(operand, null, _tree);
        } else if (!_tree.isRoot()) {
            WffTree operand1 = this.operands.pop();
            WffTree operand2 = this.operands.pop();
            // Push them backwards.
            populateNodeTruthValues(operand2, operand1, _tree);
        }

        if (!_tree.isRoot()) {
            this.operands.add(_tree);
        }
    }

    /**
     * Initializes an atom to its default truth values. These depend
     * on how many atoms there are in the well-formed formula. It goes
     * from 1, 2, 4, ..., 2^n. This number indicates the pattern of
     * alternating true/false booleans. So, if pattern = 2, and n = 2,
     * it will have a starting truth value of true,true,false, false.
     *
     * @param _tree - AtomNode that we are initializing truth values for.
     */
    private void initializeAtomTruthValues(AtomNode _tree) {
        boolean value = true;
        int pattern = this.truthPattern.get(_tree.getSymbol());
        for (int i = 1; i <= this.rows; i++) {
            _tree.getTruthValues().add(value);
            if (i % pattern == 0) {
                value = !value;
            }
        }
    }

    /**
     * Sets the truth table values for a node in the table. There are two possibilities
     * when calling this method:
     * <p>
     * 1) operand2 is null - meaning that operand1 must be a WffTree, and op must be a
     * negation operator. If this is not the case, an exception is thrown.
     * 2) All three trees are non-null. This means that op is a binary operator, being
     * applied to two sub-wffs.
     *
     * @param _operand1 - first wff.
     * @param _operand2 - second wff - can be null.
     * @param _op       - operator to perform - if operand2 == null, this is a NegNode.
     */
    private void populateNodeTruthValues(WffTree _operand1, WffTree _operand2, WffTree _op) {
        // If the second operand is null, this has to be a negation op.
        if (_operand2 == null) {
            if (!(_op instanceof NegNode)) {
                throw new IllegalArgumentException("operand2 is null, so op must be a negation node.");
            }
            // Iterate through and flip all the boolean values.
            for (int i = 0; i < this.rows; i++) {
                boolean op1Bool = _operand1.getTruthValues().get(i);
                _op.setTruthValue(!op1Bool, i);
            }
        } else {
            // Otherwise, get the two operands and perform the corresponding
            // logical operator.
            for (int i = 0; i < this.rows; i++) {
                boolean op1Bool = _operand1.getTruthValues().get(i);
                boolean op2Bool = _operand2.getTruthValues().get(i);
                if (_op.isAnd()) {
                    _op.setTruthValue(logicalAnd(op1Bool, op2Bool), i);
                } else if (_op.isOr()) {
                    _op.setTruthValue(logicalOr(op1Bool, op2Bool), i);
                } else if (_op.isImp()) {
                    _op.setTruthValue(logicalImp(op1Bool, op2Bool), i);
                } else if (_op.isBicond()) {
                    _op.setTruthValue(logicalBicond(op1Bool, op2Bool), i);
                } else if (_op.isExclusiveOr()) {
                    _op.setTruthValue(logicalXOR(op1Bool, op2Bool), i);
                }
            }
        }
    }

    /**
     * Counts the number of unique atoms in a propositional logic formula. This is
     * counted in a bfs fashion.
     *
     * @param _tree - propositional logic wff root.
     * @return number of unique atoms found.
     */
    private int getAtomCount(WffTree _tree) {
        Queue<WffTree> q = new LinkedList<>();
        HashSet<String> atoms = new HashSet<>();
        q.add(_tree);

        while (!q.isEmpty()) {
            WffTree wt = q.poll();
            for (WffTree ch : wt.getChildren()) {
                if (ch.isAtom()) {
                    String symbol = ch.getSymbol();
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
    private static boolean logicalXOR(boolean _a, boolean _b) {
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
        return !logicalXOR(_a, _b);
    }
}
