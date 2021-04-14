package com.llat.algorithms;

import com.llat.models.treenode.AndNode;
import com.llat.models.treenode.NegNode;
import com.llat.models.treenode.OrNode;
import com.llat.models.treenode.WffTree;

import java.util.HashSet;
import java.util.Set;

public class ConjunctiveNormalFormConverter {

    private WffTree wffTree;

    public ConjunctiveNormalFormConverter(WffTree _wffTree) {
        this.wffTree = _wffTree.copy();
    }

    public WffTree getCNF() {
        this.replaceAllImplications(this.wffTree);
        this.applyDeMorgansLaw(this.wffTree);
        this.removeAllDoubleNegations(this.wffTree);
        System.out.println("Applying it to " + this.wffTree.getStringRep());
        this.getCNFHelper(this.wffTree);
        return this.wffTree;
    }

    private void getCNFHelper(WffTree _tree) {
        if (_tree.isAtom()) {
            return;
        }

        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            WffTree child = _tree.getChild(i);
            System.out.println("Parent: " + _tree.getStringRep() + ", child: " + child.getStringRep());
            if (child.isOr()) {
                WffTree lhs = child.getChild(0);
                WffTree rhs = child.getChild(1);
                AndNode lhsToRhs = new AndNode();

                // If there's nothing that we can distribute to, just bail out.
                if (lhs.isAtom() || rhs.isAtom()) {
                    return;
                }

                // The invariant is that the size of the children is always 2, so this will be fast.
                // LHS is distributed to all RHS children.
                for (WffTree lhsChild : lhs.getChildren()) {
                    System.out.println("LHS Child: " + lhs.getStringRep());
                    //if (lhsChild.isAtom()) return;
                    OrNode innerLhsToRhs = new OrNode();
                    for (int rc = 0; rc < rhs.getChildrenSize(); rc++) {
                        WffTree rhsChild = rhs.getChild(rc);
                        if (rhsChild.getChildrenSize() == 0) return;
                        System.out.println("RHS Child: " + rhsChild.getStringRep());
                        //if (rhsChild.isAtom()) return;
                        // If we hit the end, just add it onto the OR from above e.g., ((A V B) V C)
                        if (rc + 1 == rhs.getChildrenSize()) {
                            innerLhsToRhs.addChild(rhs.getChild(rc));
                        } else {
                            OrNode or = new OrNode();
                            or.addChild(lhsChild);
                            or.addChild(rhsChild);
                            System.out.println("Or being constructed: " + or.getStringRep());
                            innerLhsToRhs.addChild(or);
                        }
                    }
                    System.out.println("Inner LHS to RHS: " + innerLhsToRhs.getStringRep());
                    lhsToRhs.addChild(innerLhsToRhs);
                }
                System.out.println("Replacing " + child.getStringRep() + " with " + lhsToRhs.getStringRep());
                _tree.setChild(i, lhsToRhs);
                child = lhsToRhs;
            }

            this.getCNFHelper(child);
        }
    }

    /**
     *
     * @param _tree
     */
    private void replaceAllImplications(WffTree _tree) {
        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            WffTree child = _tree.getChild(i);
            if (child.isImp()) {
                OrNode or = new OrNode();
                NegNode negNode = new NegNode();
                negNode.addChild(child.getChild(0));
                or.addChild(negNode);
                or.addChild(child.getChild(1));
                _tree.setChild(i, or);
                child = or;
            }
            this.replaceAllImplications(child);
        }
    }

    /**
     *
     * @param _tree
     */
    private void applyDeMorgansLaw(WffTree _tree) {
        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            WffTree child = _tree.getChild(i);
            WffTree innerChild = child.getChild(0);
            if (child.isNegation() && innerChild != null && innerChild.isBinaryOp()) {
                WffTree flipped = BaseTruthTreeGenerator.getNegatedBinaryNode(innerChild);
                NegNode lhs = new NegNode();
                NegNode rhs = new NegNode();
                lhs.addChild(innerChild.getChild(0));
                rhs.addChild(innerChild.getChild(1));
                flipped.addChild(lhs);
                flipped.addChild(rhs);
                _tree.setChild(i, flipped);
                child = flipped;
            }
            this.applyDeMorgansLaw(child);
        }
    }

    /**
     *
     * @param _tree
     */
    private void removeAllDoubleNegations(WffTree _tree) {
        for (int i = 0; i < _tree.getChildrenSize(); i++) {
            WffTree child = _tree.getChild(i);
            WffTree innerChild = child.getChild(0);
            if (child.isNegation() && innerChild != null && innerChild.isNegation()) {
                WffTree innerInnerChild = innerChild.getChild(0);
                _tree.setChild(i, innerInnerChild);
                child = innerInnerChild;
            }
            this.removeAllDoubleNegations(child);
        }
    }

    private void removeDuplicateDisjunctionAtoms(WffTree _tree) {
        OrNode newRoot = new OrNode();
        if (_tree.isOr()) {
            Set<String> seenAtoms = new HashSet<>();
            for (WffTree ch : _tree.getChildren()) {

            }
        }
    }
}
