package com.llat.controller;

import com.llat.input.interpreters.LLATParserInterpreter;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.tools.ViewManager;
import com.llat.views.ApplicationView;
import com.llat.views.LoginView;
import com.llat.views.SymbolButton;
import com.llat.views.events.FormulaInputEvent;
import com.llat.views.events.SolveButtonEvent;
import com.llat.views.events.SymbolInputEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    private LLATParserInterpreter llatParserInterpreter = new LLATParserInterpreter();
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
        _symbolButton.setOnAction((event) -> { EventBus.throwEvent(new SymbolInputEvent(((SymbolButton)event.getSource()).getDefaultSymbol())); }); }

    public void setSolveButtonOnAction (Button _button) { _button.setOnAction((event) -> { EventBus.throwEvent(new SolveButtonEvent()); }); }

    public Stage getStage () { return this.stage; }

}
