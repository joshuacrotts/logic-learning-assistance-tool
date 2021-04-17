package com.llat.models.localstorage.uidescription.mainview;

public class MainView {
    MainViewLabels mainViewLabels;
    LogicSymbols logicSymbols;

    public MainViewLabels getMainViewLabels() {
        return this.mainViewLabels;
    }

    public void setMainViewLabels(MainViewLabels mainViewLabels) {
        this.mainViewLabels = mainViewLabels;
    }

    public LogicSymbols getLogicSymbols() {
        return this.logicSymbols;
    }

    public void setLogicSymbols(LogicSymbols logicSymbols) {
        this.logicSymbols = logicSymbols;
    }

    @Override
    public String toString() {
        return "MainView{" +
                "mainViewLabels=" + this.mainViewLabels +
                ", logicSymbols=" + this.logicSymbols +
                '}';
    }
}
