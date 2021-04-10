package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLatexTruthTreeEvent implements Event {
    String filePath;

    public ExportLatexTruthTreeEvent (String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath () {
        return this.filePath;
    }

}
