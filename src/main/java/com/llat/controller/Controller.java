package com.llat.controller;

import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.tools.ViewManager;
import com.llat.views.ApplicationView;
import com.llat.views.LoginView;
import com.llat.views.SymbolButton;
import com.llat.views.events.SymbolInputEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    private EventBus eventBus = new EventBus();
    public Controller (Stage _stage) {
        this.stage = _stage;
        this.stage.getScene().getStylesheets().add(ViewManager.getDefaultStyle());
    }

    public void initialize (URL _url, ResourceBundle _rb) {}

    private Pane getView (int _viewName) {
        Pane parentPane = new Pane();
        switch (_viewName) {
            case ViewManager.MAINAPPLICATION: {
                parentPane = (new ApplicationView(this).getParentPane());
                break;
            }
            case ViewManager.LOGIN:
                parentPane = (new LoginView(this)).getParentPane();
                break;
            default:
                // Update this to error view.
                parentPane = new Pane();
        };
        return parentPane;
    }

    public void changeViewTo (int _viewName) {
        Pane parentPane = this.getView(_viewName);
        this.stage.getScene().setRoot(parentPane);
    }

    public void setSymbolInputButtonOnAction (SymbolButton _symbolButton) {
        _symbolButton.setOnAction((event) -> { this.eventBus.throwEvent(new SymbolInputEvent(((SymbolButton)event.getSource()).getDefaultSymbol())); });
    }

    public void addListener (Listener _listener) { this.eventBus.addListener(_listener);}

    public Stage getStage () { return this.stage; }

}
