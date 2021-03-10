package com.llat.algorithms;

import com.llat.models.treenode.WffTree;

import java.util.LinkedList;

public interface WffAlgorithm {

    public LinkedList<WffTree> getList(WffTree tree);

    public WffTree get(WffTree tree);
}
