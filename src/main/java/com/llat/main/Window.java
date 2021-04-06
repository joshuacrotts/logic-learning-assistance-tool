package com.llat.main;

import com.llat.controller.Controller;
import com.llat.tools.ViewManager;
import javafx.stage.Stage;

public class Window {
    public Window(Stage _stage) {
        App app = new App();
        app.setStageSettings(_stage);
        (new Controller(_stage)).changeViewTo(ViewManager.MAINAPPLICATION);
        _stage.show();
    }
    public Window(Stage _stage, int n) {
        App app = new App();
        app.setStageSettings(_stage);
        (new Controller(_stage)).changeViewTo(ViewManager.SETTINGS);
        _stage.show();
    }
}
