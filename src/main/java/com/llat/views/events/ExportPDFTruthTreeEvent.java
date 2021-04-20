package com.llat.views.events;

import com.llat.tools.Event;

public class ExportPDFTruthTreeEvent implements Event {

    private final String filePath;

    public ExportPDFTruthTreeEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
