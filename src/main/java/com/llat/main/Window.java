package com.llat.main;

import com.llat.controller.Controller;
import com.llat.tools.EventBus;
import com.llat.tools.ViewManager;
import javafx.stage.Stage;

public class Window {

    public Window(Stage _stage) {
        EventBus.resetListeners();
        App app = new App();
        app.setStageSettings(_stage);
        (new Controller(_stage)).changeViewTo(ViewManager.MAINAPPLICATION);
        _stage.show();
    }
}
