package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.util.LinkedList;

public interface WffAlgorithm {

    LinkedList<WffTree> getList(WffTree tree);

    WffTree get(WffTree tree);
}
