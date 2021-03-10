package com.llat.algorithms;

import com.llat.models.treenode.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;

public final class BoundVariableDetector {

    /**
     *
     * @param tree
     * @return
     */
    public static LinkedList<WffTree> get(WffTree tree) {
        LinkedHashSet<WffTree> S = new LinkedHashSet<>();
        Stack<WffTree> L = new Stack<>();
        bound(tree, S, L);
        return new LinkedList<>(S);
    }

    /**
     *
     * @param T
     * @param S
     * @param L
     */
    private static void bound(WffTree T, LinkedHashSet<WffTree> S, Stack<WffTree> L) {
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
