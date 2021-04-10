package com.llat.views.events;

import com.llat.tools.Event;

public class ExportLatexParseTreeEvent implements Event {
    String filePath;

    public ExportLatexParseTreeEvent (String _filePath) {
        this.filePath = _filePath;
    }

    public String getFilePath () {
        return this.filePath;
    }

}
