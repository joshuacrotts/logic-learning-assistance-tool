package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.controller.Controller;
public class Main extends Application {

    @Override
    public void start(Stage _stage) throws Exception{
        this.setStageSettings(_stage);
        (new Controller(_stage)).changeSceneTo("main");
        _stage.show();
    }
    public void setStageSettings(Stage _stage) {
        _stage.setScene(new Scene(new Pane()));
        _stage.setTitle("Hello World");
        _stage.setFullScreen(true);
        _stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
