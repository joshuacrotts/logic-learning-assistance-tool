package com.llat.views.events;

import com.llat.tools.Event;

public class ExportPDFParseTreeEvent implements Event {

    private final String filePath;

    public ExportPDFParseTreeEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
