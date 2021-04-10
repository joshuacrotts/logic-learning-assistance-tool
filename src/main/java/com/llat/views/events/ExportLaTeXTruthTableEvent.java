package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLaTeXTruthTableEvent implements Event {

    private final String filePath;

    public ExportLaTeXTruthTableEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
