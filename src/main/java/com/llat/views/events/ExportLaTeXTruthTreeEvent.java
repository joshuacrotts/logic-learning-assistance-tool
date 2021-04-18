package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLaTeXTruthTreeEvent implements Event {

    private final String filePath;

    public ExportLaTeXTruthTreeEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
