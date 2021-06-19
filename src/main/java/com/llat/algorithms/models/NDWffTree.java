package com.llat.algorithms.models;

import com.llat.models.treenode.WffTree;

import java.util.Collections;
import java.util.ArrayList;

/**
 *
 */
public class NDWffTree {

    /**
     *
     */
    private ArrayList<NDWffTree> derivedParents;

    private ArrayList<Integer> derivedParentIndices;

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

    public NDWffTree(WffTree _wffTree, int _flags, NDStep _derivationStep, NDWffTree... _derivedParents) {
        this.wffTree = _wffTree;
        this.derivationStep = _derivationStep;
        this.derivedParents = new ArrayList<>();
        this.derivedParentIndices = new ArrayList<>();
        this.flags = _flags;

        if (_derivedParents != null) {
            Collections.addAll(this.derivedParents, _derivedParents);
        }
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep, NDWffTree... _derivedParents) {
        this(_wffTree, 0, _derivationStep, _derivedParents);
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep, NDWffTree _derivedParent) {
        this(_wffTree, 0, _derivationStep, _derivedParent);
    }

    public NDWffTree(WffTree _wffTree, NDStep _derivationStep) {
        this(_wffTree, 0, _derivationStep);
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

    public ArrayList<NDWffTree> getDerivedParents() {
        return this.derivedParents;
    }

    public void setDerivedParents(ArrayList<NDWffTree> _derivedParents) {
        this.derivedParents = _derivedParents;
    }

    public ArrayList<Integer> getDerivedParentIndices() {
        return this.derivedParentIndices;
    }

    public void setDerivedParentIndices(ArrayList<Integer> _derivedParentIndices) {
        this.derivedParentIndices = _derivedParentIndices;
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
                && (this.derivationStep == NDStep.C || this.derivationStep == NDStep.P)) {
            return String.format("%-50s%-50s", this.wffTree.getStringRep(), this.derivationStep);
        } else {
            // It's a little ugly but it works.
            StringBuilder sb = new StringBuilder(String.format("%-50s", this.wffTree.getStringRep()));
            sb.append(this.derivationStep);
            sb.append(", ");
            for (int i = 0; i < this.derivedParentIndices.size() - 1; i++) {
                sb.append(this.derivedParentIndices.get(i));
                sb.append(", ");
            }
            sb.append(this.derivedParentIndices.get(this.derivedParentIndices.size() - 1));
            return sb.toString();
        }
    }

    public void setFlags(int _flags) {
        this.flags |= _flags;
    }

    public boolean isActive() {
        return (this.flags & NDFlag.ACTIVE) != 0;
    }

    public void setActive(boolean _b) {
        if (_b) {
            this.flags |= NDFlag.ACTIVE;
        } else {
            this.flags &= ~NDFlag.ACTIVE;
        }
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

    public boolean isDSActive() {
        return (this.flags & NDFlag.DS) != 0;
    }

    public boolean isAndIActive() {
        return (this.flags & NDFlag.AI) != 0;
    }

    public boolean isAndEActive() {
        return (this.flags & NDFlag.AE) != 0;
    }

    public boolean isDEMActive() {
        return (this.flags & NDFlag.DEM) != 0;
    }

    public boolean isBCActive() {
        return (this.flags & NDFlag.BC) != 0;
    }

    public boolean isMIActive() {
        return (this.flags & NDFlag.MI) != 0;
    }

    public boolean isImpIActive() {
        return (this.flags & NDFlag.II) != 0;
    }

    public boolean isExisActive() {
        return (this.flags & NDFlag.EX) != 0;
    }

    public boolean isUnivActive() {
        return (this.flags & NDFlag.UN) != 0;
    }

    public boolean isSatisfied() {
        return (this.flags & NDFlag.SAT) != 0;
    }
}
