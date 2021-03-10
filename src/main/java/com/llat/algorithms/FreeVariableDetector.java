package com.llat.algorithms;

import com.llat.models.treenode.QuantifierNode;
import com.llat.models.treenode.VariableNode;
import com.llat.models.treenode.WffTree;

import java.util.*;

public final class FreeVariableDetector {

    public static LinkedList<WffTree> get(WffTree tree) {
        LinkedHashSet<WffTree> S = new LinkedHashSet<>();
        Stack<WffTree> L = new Stack<>();
        free(tree, S, L);
        return new LinkedList<>(S);
    }

    /**
     *
     * @param T
     * @param S
     * @param L
     */
    private static void free(WffTree T, LinkedHashSet<WffTree> S, Stack<WffTree> L) {
        // Quantifiers are always the left-most child in a tree if they exist.
        if (T.isQuantifier()) {
            L.push(T);
        } else if (T.isVariable()) {
            // Once we find a variable, we need to check and make sure NO quantifier binds it.
            VariableNode tv = (VariableNode) T;
            boolean found = false;
            for (WffTree v : L) {
                QuantifierNode qn = (QuantifierNode) v;
                if (qn.getVariableSymbol().equals(tv.getSymbol())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                S.add(T);
            }
        }

        for (WffTree ch : T.getChildren()) {
            free(ch, S, L);
        }

        if (T.isQuantifier()) {
            L.pop();
        }
    }
}
