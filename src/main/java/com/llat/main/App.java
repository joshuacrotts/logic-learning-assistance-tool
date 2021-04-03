package com.llat.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage _stage) {
        new Window(_stage);
    }



    public void setStageSettings(Stage _stage) {
        _stage.setScene(new Scene(new Pane()));
        _stage.setTitle("Logic Learning Assistance Tool");
        _stage.setFullScreen(false);
        _stage.setMinHeight(720);
        _stage.setMinWidth(1280);
    }

}