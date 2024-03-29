package com.llat.models.treenode;

import com.llat.tools.TexPrintable;

import java.util.LinkedList;

/**
 *
 */
public class WffTree implements Copyable, TexPrintable {

    /**
     * Defines the type of node that we're using. There should be only one
     * ROOT node in the tree.
     */
    private final NodeType NODE_TYPE;

    /**
     *
     */
    private final LinkedList<WffTree> children;

    /**
     *
     */
    private final LinkedList<Boolean> truthValues;

    /**
     *
     */
    private String symbol;

    /**
     *
     */
    private int flags;

    public WffTree(String _symbol, NodeType _nodeType) {
        this.symbol = _symbol;
        this.NODE_TYPE = _nodeType;
        this.children = new LinkedList<>();
        this.truthValues = new LinkedList<>();
    }

    public WffTree(NodeType _nodeType) {
        this(null, _nodeType);
    }

    public WffTree() {
        this(null, NodeType.ROOT);
    }

    /**
     * Replaces all instances of the customized symbols to a standard
     * notation so the equivalence checker has some uniformity.
     *
     * We should probably replace these as static regex compilers...
     *
     * @param _strRep
     * @return
     */
    private static String getStandardizedEquiv(String _strRep) {
        String s = _strRep.replaceAll(" ", "");
        s = s.replaceAll("[~¬!]|(not|NOT)", "~"); // NEG
        s = s.replaceAll("[∧^⋅]|(and|AND)", "&"); // AND
        s = s.replaceAll("[\\|+\\|\\|]|(or|OR)", "∨"); // OR
        s = s.replaceAll("[→⇒⊃>]|(implies|IMPLIES)", "→"); // IMP
        s = s.replaceAll("[⇔≡↔]|(<>|iff|IFF)", "↔"); // BICOND
        s = s.replaceAll("[⊻≢⩒↮]|(xor|XOR)", "⊕"); // XOR
        s = s.replaceAll("[(]", "#");
        s = s.replaceAll("[)]", "#"); // We need to standardize these as well!
        return s;
    }

    @Override
    public WffTree copy() {
        WffTree t = new WffTree(this.symbol, this.NODE_TYPE);
        t.setFlags(this.getFlags());
        this.copyHelper(this, t);
        return t;
    }

    /**
     * Turns off highlighting for all nodes in the AST. This just iterates through the tree and
     * calls setHighlighted(false) on all nodes and their children. Each time an algorithm is called,
     * just call this before running it.
     */
    public void clearHighlighting() {
        this.setHighlighted(false);
        for (WffTree t : this.children) {
            this.clearHighlightingHelper(t);
        }
    }

    /**
     * Recursively prints the syntax tree.
     */
    public void printSyntaxTree() {
        System.out.println(this.printSyntaxTreeHelper(0));
    }

    /**
     * Returns whether or not the string representations of two Wffs are equivalent. The important
     * distinction is that this method does NOT compare object references - the equals method
     * does this. This ONLY compares the strings that make up this Wff.
     *
     * Also, note that this DOES try to flip the operands if and ONLY if they are a symmetric
     * operator e.g., AND, OR, and BICOND. All others are non-symmetric. Identity is a
     * separate check.
     *
     * @param _obj - WffTree object to compare against.
     * @return true if the string representations match, false otherwise.
     */
    public boolean stringEquals(Object _obj) {
        if (!(_obj instanceof WffTree)) {
            throw new ClassCastException("Cannot cast object of type " + _obj.getClass() + " to WffTree.");
        }

        WffTree o = (WffTree) _obj;

        // First try to swap the operands and see if they are the same.
        if (this.isAnd() || this.isOr() || this.isBicond()) {
            if (this.getNodeType() == o.getNodeType()) {
                if (this.getChild(0).stringEquals(o.getChild(1)) &&
                    this.getChild(1).stringEquals(o.getChild(0))) {
                    return true;
                }
            }
        }

        String wff1Equiv = WffTree.getStandardizedEquiv(this.getStringRep());
        String wff2Equiv = WffTree.getStandardizedEquiv(o.getStringRep());

        if (wff1Equiv.equals(wff2Equiv)) {
            return true;
        }

        StringBuilder w1 = new StringBuilder(wff1Equiv);
        StringBuilder w2 = new StringBuilder(wff2Equiv);

        // Check to see if both are identity operators and if so, reverse them.
        if (w1.compareTo(w2.reverse()) == 0 || w1.reverse().compareTo(w2.reverse()) == 0
            || w1.reverse().compareTo(w2) == 0) {
            return true;
        } else {
            // This is a bit ugly but hopefully it works...
            // Check to see if either one has a negation.
            // If the identity is of the form ~x=y, reverse it as ~y=x
            if (this.isNegation() && this.getChild(0).isIdentity()) {
                StringBuilder i1r = new StringBuilder(w1.substring(1)).reverse();
                i1r.insert(0, "~");
                return i1r.compareTo(w2) == 0;
            } else if (o.isNegation() && o.getChild(0).isIdentity()) {
                StringBuilder i2r = new StringBuilder(w2.substring(1)).reverse();
                i2r.insert(0, "~");
                return i2r.compareTo(w1) == 0;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object _obj) {
        return super.equals(_obj);
    }

    @Override
    public int hashCode() {
        return this.getStringRep().hashCode();
    }

    /**
     * Returns the i-th child in the list of children.
     *
     * @param i - index of child to return.
     * @return WffTree child that is desired.
     * @throws IndexOutOfBoundsException if i is out of bounds of the list.
     */
    public WffTree getChild(int i) {
        try {
            return this.children.get(i);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * A node P is closable if and only if it is one of the following types of wffs:
     * ~P
     * P
     * I (where I is an arbitrary identity wff)
     * ~I (same as above)
     * <p>
     * All others MUST be processed before closing.
     *
     * @return true if the node is closable, false otherwise.
     */
    public boolean isClosable() {
        if (this.isPredicate() || this.isAtom()) {
            return true;
        }
        // Nodes of type ~P are good.
        else if (this.isNegation() && this.getChild(0) != null && (this.getChild(0).isPredicate() || this.getChild(0).isAtom()))
            return true;
            // Nodes of type ~identity are good.
        else if (this.isNegation() && this.getChild(0) != null && this.getChild(0).isIdentity()) {
            return true;
        }
        // Nodes of type identity are good.
        else {
            return this.isIdentity();
        }
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public void addChild(WffTree _node) {
        this.children.add(_node);
    }

    public void setChild(int _index, WffTree _node) {
        this.children.set(_index, _node);
    }

    public boolean isRoot() {
        return this.NODE_TYPE == NodeType.ROOT;
    }

    public boolean isAtom() {
        return this.NODE_TYPE == NodeType.ATOM;
    }

    public boolean isNegation() {
        return this.NODE_TYPE == NodeType.NEG;
    }

    public boolean isDoubleNegation() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.NEG;
    }

    public boolean isNegImp() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.IMP;
    }

    public boolean isNegAnd() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.AND;
    }

    public boolean isNegOr() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.OR;
    }

    public boolean isNegExclusiveOr() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.XOR;
    }

    public boolean isNegIdentity() {
        return this.NODE_TYPE == NodeType.NEG && this.getChild(0) != null &&
                this.getChild(0).NODE_TYPE == NodeType.IDENTITY;
    }

    public boolean isAnd() {
        return this.NODE_TYPE == NodeType.AND;
    }

    public boolean isOr() {
        return this.NODE_TYPE == NodeType.OR;
    }

    public boolean isImp() {
        return this.NODE_TYPE == NodeType.IMP;
    }

    public boolean isBicond() {
        return this.NODE_TYPE == NodeType.BICOND;
    }

    public boolean isExclusiveOr() {
        return this.NODE_TYPE == NodeType.XOR;
    }

    public boolean isIdentity() {
        return this.NODE_TYPE == NodeType.IDENTITY;
    }

    public boolean isQuantifier() {
        return this.NODE_TYPE == NodeType.EXISTENTIAL || this.NODE_TYPE == NodeType.UNIVERSAL;
    }

    public boolean isExistential() {
        return this.NODE_TYPE == NodeType.EXISTENTIAL;
    }

    public boolean isUniversal() {
        return this.NODE_TYPE == NodeType.UNIVERSAL;
    }

    public boolean isBinaryOp() {
        return this.isAnd() || this.isOr() || this.isImp() || this.isBicond() || this.isExclusiveOr() || this.isIdentity();
    }

    public boolean isPredicate() {
        return this.NODE_TYPE == NodeType.PREDICATE;
    }

    public boolean isConstant() {
        return this.NODE_TYPE == NodeType.CONSTANT;
    }

    public boolean isVariable() {
        return this.NODE_TYPE == NodeType.VARIABLE;
    }

    public NodeType getNodeType() {
        return this.NODE_TYPE;
    }

    public boolean isPropositionalWff() {
        return (this.flags & NodeFlag.PROPOSITIONAL) != 0;
    }

    public boolean isPredicateWff() {
        return (this.flags & NodeFlag.PREDICATE) != 0;
    }

    public LinkedList<WffTree> getChildren() {
        return this.children;
    }

    public LinkedList<Boolean> getTruthValues() {
        return this.truthValues;
    }

    public void setTruthValue(boolean _b, int i) {
        if (i >= this.truthValues.size()) {
            this.truthValues.add(i, _b);
        } else {
            this.truthValues.set(i, _b);
        }
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String _s) {
        this.symbol = _s;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int _flag) {
        this.flags |= _flag;
    }

    public boolean isHighlighted() {
        return (this.flags & NodeFlag.HIGHLIGHT) != 0;
    }

    public void setHighlighted(boolean _highlighted) {
        if (_highlighted) {
            this.flags |= NodeFlag.HIGHLIGHT;
        } else {
            this.flags &= ~NodeFlag.HIGHLIGHT;
        }
    }

    public boolean isPalindromeWff() {
        String s = this.getStringRep();
        int n = s.length();
        for (int i = 0; i < (n/2); ++i) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return
     */
    public String getStringRep() {
        StringBuilder str = new StringBuilder();
        for (WffTree ch : this.getChildren()) {
            str.append(ch.getStringRep());
        }
        return str.toString();
    }

    /**
     * Recursively returns the tex command for this WffTree.
     *
     * @return String representation of the tex commands needed to display this in LaTeX.
     */
    public String getTexCommand() {
        StringBuilder str = new StringBuilder();
        for (WffTree ch : this.getChildren()) {
            str.append(ch.getTexCommand());
        }
        return str.toString();
    }

    /**
     * Returns the tex command for this WffTree ONLY, meaning that no children are called. Whatever is
     * returned is the tex commmand for that particular node. For WffTree nodes, it is null since this
     * should be the root of an AST.
     *
     * @return String representation of TeX command.
     */
    public String getTexParseCommand() {
        return null;
    }

    @Override
    public String toString() {
        return this.NODE_TYPE.toString();
    }

    /**
     * Performs a recursive copy of all children in this truth tree.
     * Applies to the second parameter.
     *
     * @param _root    - root of tree to copy.
     * @param _newTree - tree to copy into.
     */
    private void copyHelper(WffTree _root, WffTree _newTree) {
        for (WffTree ch : _root.children) {
            _newTree.addChild(ch.copy());
        }
    }

    /**
     * Recursive clear highlighting method. This removes all highlighting
     * performed by the front-end or anything that suggests that *this* node
     * is the result of some algorithm.
     *
     * @param _root - root of WffTree.
     */
    private void clearHighlightingHelper(WffTree _root) {
        for (WffTree ch : _root.children) {
            ch.setHighlighted(false);
            this.clearHighlightingHelper(ch);
        }
    }

    /**
     * Recursive function to print a syntax tree. The current depth is passed
     * as the "indent" parameter so that the output looks properly nested.
     * Each recursive call for a child is indented by two additional spaces.
     *
     * @param indent current indentation level
     * @return a string representation of this syntax tree node (and its descendants)
     * @author Steve Tate
     */
    private StringBuilder printSyntaxTreeHelper(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(Math.max(0, indent)));
        sb.append(this.toString());

        if (!this.children.isEmpty()) {
            sb.append(" (\n");
            boolean isFirstChild = true;
            for (WffTree child : this.children) {
                if (!isFirstChild) {
                    sb.append(",\n");
                }
                isFirstChild = false;
                sb.append(child.printSyntaxTreeHelper(indent + 2));
            }
            sb.append(")");
        }

        return sb;
    }
}
