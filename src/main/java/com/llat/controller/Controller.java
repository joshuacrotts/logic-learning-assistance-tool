package com.llat.controller;

import com.llat.tools.ViewManager;
import com.llat.tools.ViewManager;
import com.llat.views.LoginView;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    public Controller (Stage _stage) {
        this.stage = _stage;
        this.stage.getScene().getStylesheets().add(ViewManager.getDefaultStyle());
    }

    public void initialize (URL _url, ResourceBundle _rb) {}

    private Pane getView(int _viewName) {
        Pane parentPane = new Pane();
        switch (_viewName) {
            case ViewManager.LOGIN:
                parentPane = (new LoginView(this)).getParentPane();
                break;
            default:
                parentPane = new Pane();
        };
        return parentPane;
    }

    public void changeViewTo(int _viewName) {
        Pane parentPane = this.getView(_viewName);
        this.stage.getScene().setRoot(parentPane);
    }

    public Stage getStage() { return this.stage; }
}
