package com.llat.algorithms.models;

import com.llat.models.treenode.WffTree;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 */
public class NDWffTree {

    /**
     *
     */
    private LinkedList<NDWffTree> derivedParents;

    /**
     *
     */
    private WffTree wffTree;

    /**
     *
     */
    private NDStep derivationStep;

    /**
     *
     */
    private int flags;

    public NDWffTree(WffTree _wffTree, boolean _active, NDStep _derivationStep, NDWffTree... _derivedParents) {
        this.wffTree = _wffTree;
        this.derivationStep = _derivationStep;
        this.derivedParents = new LinkedList<>();
        this.flags |= _active ? NDFlag.ACTIVE : 0;

        if (_derivedParents != null) {
            Collections.addAll(this.derivedParents, _derivedParents);
        }
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep, NDWffTree... _derivedParents) {
        this(_wffTree, false, _derivationStep, _derivedParents);
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep, NDWffTree _derivedParent) {
        this(_wffTree, false, _derivationStep, _derivedParent);
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep) {
        this(_wffTree, false, _derivationStep);
    }

    @Override
    public int hashCode() {
        return this.getWffTree().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        NDWffTree otherTree = (NDWffTree) o;
        return this.getWffTree().equals(otherTree.getWffTree())
                || this.getWffTree().stringEquals(otherTree.getWffTree());
    }

    public LinkedList<NDWffTree> getDerivedParents() {
        return this.derivedParents;
    }

    public void setDerivedParents(LinkedList<NDWffTree> _derivedParents) {
        this.derivedParents = _derivedParents;
    }

    public WffTree getWffTree() {
        return this.wffTree;
    }

    public void setWffTree(WffTree wffTree) {
        this.wffTree = wffTree;
    }

    public NDStep getDerivationStep() {
        return this.derivationStep;
    }

    public void setDerivationStep(NDStep _derivationStep) {
        this.derivationStep = _derivationStep;
    }

    @Override
    public String toString() {
        if ((this.derivedParents == null || this.derivedParents.isEmpty())
                && (this.derivationStep == NDStep.C|| this.derivationStep == NDStep.P)) {
            return this.wffTree.getStringRep() + "\t\t" + this.derivationStep;
        } else {
            // It's a little ugly but it works.
            StringBuilder sb = new StringBuilder(this.wffTree.getStringRep());
            sb.append("\t\tDerived from [");
            for (int i = 0; i < this.derivedParents.size() - 1; i++) {
                sb.append(this.derivedParents.get(i).getWffTree().getStringRep());
                sb.append(", ");
            }
            sb.append(this.derivedParents.getLast().getWffTree().getStringRep());
            sb.append("], ");
            sb.append(this.derivationStep);
            return sb.toString();
        }
    }

    public void setFlags(int _flags) {
        this.flags |= _flags;
    }

    public boolean isActive() {
        return (this.flags & NDFlag.ACTIVE) != 0;
    }

    public boolean isHSActive() {
        return (this.flags & NDFlag.HS) != 0;
    }

    public boolean isMPActive() {
        return (this.flags & NDFlag.MP) != 0;
    }

    public boolean isMTActive() {
        return (this.flags & NDFlag.MT) != 0;
    }

    public boolean isAndIActive() { return (this.flags & NDFlag.AND_I) != 0; }
}
