package com.llat.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is a test class.
 * 
 * @author Joshua Crotts
 * @date January 21, 2021
 */
public class MainApp extends Application {

    @Override
    public void start(Stage _stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion
                + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        _stage.setScene(scene);
        _stage.show();
    }

    public static void main(String[] _args) {
        launch();
    }
}