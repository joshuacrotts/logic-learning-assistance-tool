package com.llat.models.localstorage.uidescription.mainview;


public class MainViewLabels {
    public String propositionalLabel;
    public String predicateLabel;
    public String errorAndWarningLabel;
    public String truthTableLabel;
    public String parseTreeLabel;
    public String truthTreeLabel;
    public String symbolNameLabel;
    public String formalNameLabel;
    public String alternativeSymbolsLabel;
    public String explanationLabel;
    public String examplesLabel;
    public GeneralMenu generalMenu;
    public PropositionalMenu propositionalMenu;
    public PredicateMenu predicateMenu;
    public String axiomTabLabel;
    public String historyTabLabel;
    public String solveButton;
    public String applyButton;

    public MainViewLabels(String propositionalLabel, String predicateLabel, String errorAndWarningLabel, String truthTableLabel, String parseTreeLabel, String truthTreeLabel, String symbolNameLabel, String formalNameLabel, String alternativeSymbolsLabel, String explanationLabel, String examplesLabel, GeneralMenu generalMenu, PropositionalMenu propositionalMenu, PredicateMenu predicateMenu, String axiomTabLabel, String historyTabLabel, String solveButton, String applyButton) {
        this.propositionalLabel = propositionalLabel;
        this.predicateLabel = predicateLabel;
        this.errorAndWarningLabel = errorAndWarningLabel;
        this.truthTableLabel = truthTableLabel;
        this.parseTreeLabel = parseTreeLabel;
        this.truthTreeLabel = truthTreeLabel;
        this.symbolNameLabel = symbolNameLabel;
        this.formalNameLabel = formalNameLabel;
        this.alternativeSymbolsLabel = alternativeSymbolsLabel;
        this.explanationLabel = explanationLabel;
        this.examplesLabel = examplesLabel;
        this.generalMenu = generalMenu;
        this.propositionalMenu = propositionalMenu;
        this.predicateMenu = predicateMenu;
        this.axiomTabLabel = axiomTabLabel;
        this.historyTabLabel = historyTabLabel;
        this.solveButton = solveButton;
        this.applyButton = applyButton;
    }

    public String getPropositionalLabel() {
        return this.propositionalLabel;
    }

    public void setPropositionalLabel(String propositionalLabel) {
        this.propositionalLabel = propositionalLabel;
    }

    public String getPredicateLabel() {
        return this.predicateLabel;
    }

    public void setPredicateLabel(String predicateLabel) {
        this.predicateLabel = predicateLabel;
    }

    public String getErrorAndWarningLabel() {
        return this.errorAndWarningLabel;
    }

    public void setErrorAndWarningLabel(String errorAndWarningLabel) {
        this.errorAndWarningLabel = errorAndWarningLabel;
    }

    public String getTruthTableLabel() {
        return this.truthTableLabel;
    }

    public void setTruthTableLabel(String truthTableLabel) {
        this.truthTableLabel = truthTableLabel;
    }

    public String getParseTreeLabel() {
        return this.parseTreeLabel;
    }

    public void setParseTreeLabel(String parseTreeLabel) {
        this.parseTreeLabel = parseTreeLabel;
    }

    public String getTruthTreeLabel() {
        return this.truthTreeLabel;
    }

    public void setTruthTreeLabel(String truthTreeLabel) {
        this.truthTreeLabel = truthTreeLabel;
    }

    public String getSymbolNameLabel() {
        return this.symbolNameLabel;
    }

    public void setSymbolNameLabel(String symbolNameLabel) {
        this.symbolNameLabel = symbolNameLabel;
    }

    public String getFormalNameLabel() {
        return this.formalNameLabel;
    }

    public void setFormalNameLabel(String formalNameLabel) {
        this.formalNameLabel = formalNameLabel;
    }

    public String getAlternativeSymbolsLabel() {
        return this.alternativeSymbolsLabel;
    }

    public void setAlternativeSymbolsLabel(String alternativeSymbolsLabel) {
        this.alternativeSymbolsLabel = alternativeSymbolsLabel;
    }

    public String getExplanationLabel() {
        return this.explanationLabel;
    }

    public void setExplanationLabel(String explanationLabel) {
        this.explanationLabel = explanationLabel;
    }

    public String getExamplesLabel() {
        return this.examplesLabel;
    }

    public void setExamplesLabel(String examplesLabel) {
        this.examplesLabel = examplesLabel;
    }

    public GeneralMenu getGeneralMenu() {
        return this.generalMenu;
    }

    public void setGeneralMenu(GeneralMenu generalMenu) {
        this.generalMenu = generalMenu;
    }

    public PropositionalMenu getPropositionalMenu() {
        return this.propositionalMenu;
    }

    public void setPropositionalMenu(PropositionalMenu propositionalMenu) {
        this.propositionalMenu = propositionalMenu;
    }

    public PredicateMenu getPredicateMenu() {
        return this.predicateMenu;
    }

    public void setPredicateMenu(PredicateMenu predicateMenu) {
        this.predicateMenu = predicateMenu;
    }

    public String getAxiomTabLabel() {
        return this.axiomTabLabel;
    }

    public void setAxiomTabLabel(String axiomTabLabel) {
        this.axiomTabLabel = axiomTabLabel;
    }

    public String getHistoryTabLabel() {
        return this.historyTabLabel;
    }

    public void setHistoryTabLabel(String historyTabLabel) {
        this.historyTabLabel = historyTabLabel;
    }

    public String getSolveButton() {
        return this.solveButton;
    }

    public void setSolveButton(String solveButton) {
        this.solveButton = solveButton;
    }

    public String getApplyButton() {
        return this.applyButton;
    }

    public void setApplyButton(String applyButton) {
        this.applyButton = applyButton;
    }

    @Override
    public String toString() {
        return "MainViewLabels{" +
                "propositionalLabel='" + this.propositionalLabel + '\'' +
                ", predicateLabel='" + this.predicateLabel + '\'' +
                ", errorAndWarningLabel='" + this.errorAndWarningLabel + '\'' +
                ", truthTableLabel='" + this.truthTableLabel + '\'' +
                ", parseTreeLabel='" + this.parseTreeLabel + '\'' +
                ", truthTreeLabel='" + this.truthTreeLabel + '\'' +
                ", symbolNameLabel='" + this.symbolNameLabel + '\'' +
                ", formalNameLabel='" + this.formalNameLabel + '\'' +
                ", alternativeSymbolsLabel='" + this.alternativeSymbolsLabel + '\'' +
                ", explanationLabel='" + this.explanationLabel + '\'' +
                ", examplesLabel='" + this.examplesLabel + '\'' +
                ", generalMenu=" + this.generalMenu +
                ", propositionalMenu=" + this.propositionalMenu +
                ", predicateMenu=" + this.predicateMenu +
                ", axiomTabLabel='" + this.axiomTabLabel + '\'' +
                ", historyTabLabel='" + this.historyTabLabel + '\'' +
                ", solveButton='" + this.solveButton + '\'' +
                ", applyButton='" + this.applyButton + '\'' +
                '}';
    }
}
