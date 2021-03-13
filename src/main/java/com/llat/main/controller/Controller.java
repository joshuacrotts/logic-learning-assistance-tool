package com.llat.main.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import  com.llat.main.view.MainApplicationView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    public Controller (Stage _stage) {
        this.stage = _stage;
    }
    @Override
    public void initialize (URL _url, ResourceBundle _rb) {
    }

    public Pane getView(String _sceneType) {
        Pane parentPane;
        switch (_sceneType) {
            default:
                MainApplicationView mainApplicationView = new MainApplicationView(this);
                parentPane = mainApplicationView.getParentPane();
        }
        return parentPane;
    }

    public void changeSceneTo(String _sceneType) {
        Pane parentPane = this.getView(_sceneType);
        this.stage.getScene().setRoot(parentPane);
    }
}
