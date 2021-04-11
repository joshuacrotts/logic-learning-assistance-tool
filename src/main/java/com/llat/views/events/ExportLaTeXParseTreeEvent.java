package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLaTeXParseTreeEvent implements Event {

    private final String filePath;

    public ExportLaTeXParseTreeEvent(String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
