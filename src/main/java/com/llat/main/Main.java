package com.llat.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.llat.main.controller.Controller;
public class Main extends Application {

    @Override
    public void start(Stage _stage) throws Exception{
        this.setStageSettings(_stage);
        (new Controller(_stage)).changeSceneTo("main");
        _stage.show();
    }
    public void setStageSettings(Stage _stage) {
        _stage.setScene(new Scene(new Pane()));
        _stage.setTitle("Logic Learning Assistance Tool");
        _stage.setFullScreen(true);
        _stage.setMinHeight(720);
        _stage.setMinWidth(1280);
        //_stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
