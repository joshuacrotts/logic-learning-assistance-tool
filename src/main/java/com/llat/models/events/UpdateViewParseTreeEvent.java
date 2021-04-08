package com.llat.models.events;

import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;

public class UpdateViewParseTreeEvent implements Event {

    WffTree wffTree;
    boolean isEmpty;
    public UpdateViewParseTreeEvent (WffTree _wffTree) {
        this.wffTree = _wffTree;
    }
    public UpdateViewParseTreeEvent () {
        this.isEmpty = true;
}

    public boolean isEmpty() {
        return this.isEmpty;
    }
    public WffTree getWffTree () {
        return this.wffTree;
    }

}
