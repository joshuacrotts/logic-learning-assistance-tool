package com.llat.main;

import com.llat.controller.Controller;
import com.llat.tools.EventBus;
import com.llat.tools.ViewManager;
import javafx.stage.Stage;

public class Window {

    public Window(Stage _stage) {
        App app = new App();
        EventBus.resetListeners();
        app.setStageSettings(_stage);
        (new Controller(_stage)).changeViewTo(ViewManager.MAINAPPLICATION);
        _stage.show();
    }

    public Window(Controller _controller) {
        App app = new App();
        EventBus.resetListeners();
        app.setStageSettings(_controller.getStage());
        _controller.changeViewTo(ViewManager.MAINAPPLICATION);
        _controller.getStage().show();
    }
}
