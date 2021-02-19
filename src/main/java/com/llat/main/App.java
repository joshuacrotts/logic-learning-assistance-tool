package com.llat.main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage _primaryStage) {
        //Create a Frame object
        new Frame(_primaryStage, "main");
    }
}