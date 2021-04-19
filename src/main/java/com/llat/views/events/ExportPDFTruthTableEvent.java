package com.llat.views.events;

import com.llat.tools.Event;

public class ExportPDFTruthTableEvent implements Event {

    private final String filePath;

    public ExportPDFTruthTableEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
