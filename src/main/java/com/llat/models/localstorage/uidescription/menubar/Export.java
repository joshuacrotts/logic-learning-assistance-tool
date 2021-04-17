package com.llat.models.localstorage.uidescription.menubar;

public class Export  {
    public String label;
    public PDF pdf;
    public Latex latex;

    public Export(String label, PDF pdf, Latex latex) {
        this.label = label;
        this.pdf = pdf;
        this.latex = latex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    public Latex getLatex() {
        return latex;
    }

    public void setLatex(Latex latex) {
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "Export{" +
                "label='" + label + '\'' +
                ", pdf=" + pdf +
                ", latex=" + latex +
                '}';
    }

}
