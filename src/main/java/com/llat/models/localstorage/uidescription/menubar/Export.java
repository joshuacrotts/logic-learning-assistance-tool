package com.llat.models.localstorage.uidescription.menubar;

public class Export {
    public String label;
    public PDF pdf;
    public Latex latex;

    public Export(String label, PDF pdf, Latex latex) {
        this.label = label;
        this.pdf = pdf;
        this.latex = latex;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PDF getPdf() {
        return this.pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    public Latex getLatex() {
        return this.latex;
    }

    public void setLatex(Latex latex) {
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "Export{" +
                "label='" + this.label + '\'' +
                ", pdf=" + this.pdf +
                ", latex=" + this.latex +
                '}';
    }

}
