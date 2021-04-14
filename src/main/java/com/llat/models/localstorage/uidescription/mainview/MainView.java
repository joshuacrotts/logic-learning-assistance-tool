package com.llat.models.localstorage.uidescription.mainview;

public class MainView {
    MainViewLabels mainViewLabels;
    LogicSymbols logicSymbols;

    public MainViewLabels getMainViewLabels() {
        return mainViewLabels;
    }

    public void setMainViewLabels(MainViewLabels mainViewLabels) {
        this.mainViewLabels = mainViewLabels;
    }

    public LogicSymbols getLogicSymbols() {
        return logicSymbols;
    }

    public void setLogicSymbols(LogicSymbols logicSymbols) {
        this.logicSymbols = logicSymbols;
    }

    @Override
    public String toString() {
        return "MainView{" +
                "mainViewLabels=" + mainViewLabels +
                ", logicSymbols=" + logicSymbols +
                '}';
    }
}
