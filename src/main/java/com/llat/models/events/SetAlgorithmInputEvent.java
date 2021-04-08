package com.llat.models.events;

import com.llat.tools.Event;

import java.util.ArrayList;
import java.util.List;

public class SetAlgorithmInputEvent implements Event {
    ArrayList<List<Object>> algorithmOptions;

    public SetAlgorithmInputEvent (ArrayList<List<Object>> _algorithmOptions) {
        this.algorithmOptions = _algorithmOptions;
    }

    public ArrayList<List<Object>> getAlgorithmOptions () {
        return this.algorithmOptions;
    }

    public void setAlgorithmOptions(ArrayList<List<Object>> _algorithmOptions) {
        this.algorithmOptions = _algorithmOptions;
    }

}
