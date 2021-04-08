package com.llat.main;

import com.llat.controller.Controller;
import com.llat.tools.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage _stage) {
        this.setStageSettings(_stage);
        (new Controller(_stage)).changeViewTo(ViewManager.MAINAPPLICATION);
        _stage.show();
    }

    public void setStageSettings(Stage _stage) {
        _stage.setScene(new Scene(new Pane()));
        _stage.setTitle("Logic Learning Assistance Tool");
        _stage.setMaximized(true);
        _stage.setFullScreen(false);
        _stage.setMinHeight(720);
        _stage.setMinWidth(1280);
        _stage.setMaximized(true);

    }
}