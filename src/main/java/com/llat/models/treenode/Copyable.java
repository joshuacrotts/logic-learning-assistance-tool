package com.llat.models.treenode;

public interface Copyable {

    /**
     * Adds support for deep copying of a WffTree. Each node in the AST should override/implement this
     * method. The idea is to create a copy of the data, then deep copy all of that node's children,
     * where the base case is generally the atoms, constants, and predicates. The flags are also copied.
     *
     * @return new copy of the WffTree.
     */
    public abstract WffTree copy();
}
