package com.llat.models.events;

import com.llat.tools.Event;

import java.util.ArrayList;
import java.util.List;

public class SetAlgorithmInputEvent implements Event {
    ArrayList<List<String>> algorithmOptions;

    public SetAlgorithmInputEvent (ArrayList<List<String>> _algorithmOptions) {
        this.algorithmOptions = _algorithmOptions;
    }

    public ArrayList<List<String>> getAlgorithmOptions () {
        return this.algorithmOptions;
    }

    public void setAlgorithmOptions(ArrayList<List<String>> _algorithmOptions) {
        this.algorithmOptions = _algorithmOptions;
    }

}
