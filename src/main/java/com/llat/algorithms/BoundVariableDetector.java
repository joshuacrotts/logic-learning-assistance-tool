package com.llat.algorithms;

import com.llat.models.treenode.*;

import java.util.HashSet;
import java.util.LinkedList;

public final class BoundVariableDetector {

    public static LinkedList<WffTree> get(WffTree tree) {
        HashSet<WffTree> S = new HashSet<>();
        LinkedList<WffTree> L = new LinkedList<>();
        bound(tree, S, L);
        return new LinkedList<>(S);
    }

    /**
     *
     * @param T
     * @param S
     * @param L
     */
    private static void bound(WffTree T, HashSet<WffTree> S, LinkedList<WffTree> L) {
        WffTree quantifierNode = null;
        // Quantifiers are always the left-most child in a tree if they exist.
        if (T.isQuantifier()) {
            L.add(T);
            quantifierNode = T;
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

        // If there are multiple children in this tree, call bound
        // on all of them in BFS fashion. Once this is done, we need
        // to remove the quantifier since it'll no longer be in scope.
        if (T.getChildrenSize() > 1) {
            for (WffTree c : T.getChildren()) {
                bound(c, S, L);
            }

            if (quantifierNode != null) {
                L.remove(quantifierNode);
            }
        }
    }
}
