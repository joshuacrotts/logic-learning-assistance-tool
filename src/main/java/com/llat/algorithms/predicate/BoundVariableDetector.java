package com.llat.algorithms.predicate;

import com.llat.models.treenode.QuantifierNode;
import com.llat.models.treenode.VariableNode;
import com.llat.models.treenode.WffTree;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;

public final class BoundVariableDetector {

    /**
     *
     */
    private WffTree wffTree;

    public BoundVariableDetector(WffTree _wffTree) {
        this.wffTree = _wffTree;
    }

    /**
     * @return
     */
    public LinkedList<WffTree> get() {
        LinkedList<WffTree> S = new LinkedList<>();
        Stack<WffTree> L = new Stack<>();
        this.bound(this.wffTree, S, L);
        return S;
    }

    /**
     * @param T
     * @param S
     * @param L
     */
    private void bound(WffTree T, LinkedList<WffTree> S, Stack<WffTree> L) {
        // Quantifiers are always the left-most child in a tree if they exist.
        if (T.isQuantifier()) {
            L.push(T);
        } else if (T.isVariable()) {
            // Once we find a variable, we need to check and see if we have a quantifier
            // that binds it.
            VariableNode tv = (VariableNode) T;
            for (WffTree v : L) {
                QuantifierNode qn = (QuantifierNode) v;
                if (qn.getVariableSymbol().equals(tv.getSymbol())) {
                    S.add(T);
                    break;
                }
            }
        }

        for (WffTree ch : T.getChildren()) {
            bound(ch, S, L);
        }

        if (T.isQuantifier()) {
            L.pop();
        }
    }
}
