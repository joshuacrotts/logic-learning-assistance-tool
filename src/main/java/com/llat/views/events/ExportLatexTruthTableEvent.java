package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLatexTruthTableEvent implements Event {
    String filePath;

    public ExportLatexTruthTableEvent (String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath () {
        return this.filePath;
    }

}
