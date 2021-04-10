package com.llat.models.treenode;

import com.llat.algorithms.TexPrinter;

/**
 *
 */
public class AtomNode extends WffTree {

    /**
     * Symbol to define the proposition/atom.
     */
    private final String ATOM_SYMBOL;

    public AtomNode(String _symbol) {
        super(NodeType.ATOM);
        this.ATOM_SYMBOL = _symbol;
    }

    @Override
    public WffTree copy() {
        AtomNode atomNode = new AtomNode(this.ATOM_SYMBOL);
        atomNode.setFlags(this.getFlags());
        return atomNode;
    }

    public String getSymbol() {
        return this.ATOM_SYMBOL;
    }

    @Override
    public String getStringRep() {
        return this.getSymbol();
    }

    @Override
    public String getTexCommand() {
        return TexPrinter.removeMathMode(this.getSymbol());
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.ATOM_SYMBOL;
    }

    @Override
    public String getTexParseCommand() {
        return this.getTexCommand();
    }
}
