package com.llat.models.localstorage.uidescription.mainview;


public class MainViewLabels {
    public String propositionalLabel;
    public String predicateLabel;
    public String truthTabelLabel;
    public String truthTreeLabel;
    public String symbolNameLabel;
    public String formalNameLabel;
    public String alternativeSymbolsLabel;
    public String examplesLabel;
    public GeneralMenu generalMenu;
    public PropositionalMenu propositionalMenu;
    public PredicateMenu predicateMenu;
    public String axiomTabLabel;
    public String historyTabLabel;
    public String solveButton;

    public MainViewLabels(String propositionalLabel, String predicateLabel, String truthTabelLabel, String truthTreeLabel, String symbolNameLabel, String formalNameLabel, String alternativeSymbolsLabel, String examplesLabel, GeneralMenu generalMenu, PropositionalMenu propositionalMenu, PredicateMenu predicateMenu, String axiomTabLabel, String historyTabLabel, String solveButton) {
        this.propositionalLabel = propositionalLabel;
        this.predicateLabel = predicateLabel;
        this.truthTabelLabel = truthTabelLabel;
        this.truthTreeLabel = truthTreeLabel;
        this.symbolNameLabel = symbolNameLabel;
        this.formalNameLabel = formalNameLabel;
        this.alternativeSymbolsLabel = alternativeSymbolsLabel;
        this.examplesLabel = examplesLabel;
        this.generalMenu = generalMenu;
        this.propositionalMenu = propositionalMenu;
        this.predicateMenu = predicateMenu;
        this.axiomTabLabel = axiomTabLabel;
        this.historyTabLabel = historyTabLabel;
        this.solveButton = solveButton;
    }

    public String getPropositionalLabel() {
        return propositionalLabel;
    }

    public void setPropositionalLabel(String propositionalLabel) {
        this.propositionalLabel = propositionalLabel;
    }

    public String getPredicateLabel() {
        return predicateLabel;
    }

    public void setPredicateLabel(String predicateLabel) {
        this.predicateLabel = predicateLabel;
    }

    public String getTruthTabelLabel() {
        return truthTabelLabel;
    }

    public void setTruthTabelLabel(String truthTabelLabel) {
        this.truthTabelLabel = truthTabelLabel;
    }

    public String getTruthTreeLabel() {
        return truthTreeLabel;
    }

    public void setTruthTreeLabel(String truthTreeLabel) {
        this.truthTreeLabel = truthTreeLabel;
    }

    public String getSymbolNameLabel() {
        return symbolNameLabel;
    }

    public void setSymbolNameLabel(String symbolNameLabel) {
        this.symbolNameLabel = symbolNameLabel;
    }

    public String getFormalNameLabel() {
        return formalNameLabel;
    }

    public void setFormalNameLabel(String formalNameLabel) {
        this.formalNameLabel = formalNameLabel;
    }

    public String getAlternativeSymbolsLabel() {
        return alternativeSymbolsLabel;
    }

    public void setAlternativeSymbolsLabel(String alternativeSymbolsLabel) {
        this.alternativeSymbolsLabel = alternativeSymbolsLabel;
    }

    public String getExamplesLabel() {
        return examplesLabel;
    }

    public void setExamplesLabel(String examplesLabel) {
        this.examplesLabel = examplesLabel;
    }

    public GeneralMenu getGeneralMenu() {
        return generalMenu;
    }

    public void setGeneralMenu(GeneralMenu generalMenu) {
        this.generalMenu = generalMenu;
    }

    public PropositionalMenu getPropositionalMenu() {
        return propositionalMenu;
    }

    public void setPropositionalMenu(PropositionalMenu propositionalMenu) {
        this.propositionalMenu = propositionalMenu;
    }

    public PredicateMenu getPredicateMenu() {
        return predicateMenu;
    }

    public void setPredicateMenu(PredicateMenu predicateMenu) {
        this.predicateMenu = predicateMenu;
    }

    public String getAxiomTabLabel() {
        return axiomTabLabel;
    }

    public void setAxiomTabLabel(String axiomTabLabel) {
        this.axiomTabLabel = axiomTabLabel;
    }

    public String getHistoryTabLabel() {
        return historyTabLabel;
    }

    public void setHistoryTabLabel(String historyTabLabel) {
        this.historyTabLabel = historyTabLabel;
    }

    public String getSolveButton() {
        return solveButton;
    }

    public void setSolveButton(String solveButton) {
        this.solveButton = solveButton;
    }

    @Override
    public String toString() {
        return "MainViewLabels{" +
                "propositionalLabel='" + propositionalLabel + '\'' +
                ", predicateLabel='" + predicateLabel + '\'' +
                ", truthTabelLabel='" + truthTabelLabel + '\'' +
                ", truthTreeLabel='" + truthTreeLabel + '\'' +
                ", symbolNameLabel='" + symbolNameLabel + '\'' +
                ", formalNameLabel='" + formalNameLabel + '\'' +
                ", alternativeSymbolsLabel='" + alternativeSymbolsLabel + '\'' +
                ", examplesLabel='" + examplesLabel + '\'' +
                ", generalMenu=" + generalMenu +
                ", propositionalMenu=" + propositionalMenu +
                ", predicateMenu=" + predicateMenu +
                ", axiomTabLabel='" + axiomTabLabel + '\'' +
                ", historyTabLabel='" + historyTabLabel + '\'' +
                ", solveButton='" + solveButton + '\'' +
                '}';
    }
}
