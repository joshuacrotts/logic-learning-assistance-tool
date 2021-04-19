package com.llat.views.menu;

import javafx.stage.FileChooser;

public enum ExportType {

    LATEX_TRUTH_TABLE("Latex Truth Table", ".tex", new FileChooser.ExtensionFilter("LaTeX files (*.tex)", "*.tex")),
    LATEX_PARSE_TREE("Latex Parse Table", ".tex", new FileChooser.ExtensionFilter("LaTeX files (*.tex)", "*.tex")),
    LATEX_TRUTH_TREE("Latex Truth Tree", ".tex", new FileChooser.ExtensionFilter("LaTeX files (*.tex)", "*.tex")),
    PDF_TRUTH_TABLE("PDF Truth Table", ".pdf", new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")),
    PDF_PARSE_TREE("PDF Parse Table", ".pdf", new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")),
    PDF_TRUTH_TREE("PDF Truth Tree", ".pdf", new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));

    private final String exportType;
    private final String fileExtension;
    private final FileChooser.ExtensionFilter extensionFilter;

    ExportType(String _exportType, String _fileExtension, FileChooser.ExtensionFilter _extensionFilter) {
        this.exportType = _exportType;
        this.fileExtension = _fileExtension;
        this.extensionFilter = _extensionFilter;
    }

    public String getType() {
        return this.exportType;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public FileChooser.ExtensionFilter getExtensionFilter() {
        return this.extensionFilter;
    }

}

