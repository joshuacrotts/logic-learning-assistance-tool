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

    public Window(Stage _stage, boolean isLoggedIn) {
        App app = new App();
        EventBus.resetListeners();
        app.setStageSettings(_stage);
        Controller controller = new Controller(_stage);
        controller.changeViewTo(ViewManager.MAINAPPLICATION);
        controller.LocalUser();
        _stage.show();
    }
}
