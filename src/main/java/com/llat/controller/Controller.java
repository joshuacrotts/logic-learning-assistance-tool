package com.llat.controller;

import com.llat.database.DatabaseAdapter;
import com.llat.database.DatabaseInterpeter;
import com.llat.database.UserObject;
import com.llat.input.interpreters.LLATParserInterpreter;
import com.llat.models.LogicSetup;
import com.llat.models.localstorage.credentials.CredentialsAdaptor;
import com.llat.models.localstorage.uidescription.UIObject;
import com.llat.models.localstorage.uidescription.UIObjectAdaptor;
import com.llat.tools.EventBus;
import com.llat.tools.LLATUtils;
import com.llat.tools.MouseManager;
import com.llat.tools.ViewManager;
import com.llat.views.*;
import com.llat.views.events.*;
import com.llat.views.menu.ExportMenu;
import com.llat.views.menu.ExportType;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final Stage stage;
    private final LLATParserInterpreter llatParserInterpreter = new LLATParserInterpreter();
    private final DatabaseAdapter databaseAdapter = new DatabaseAdapter();
    private final DatabaseInterpeter di = new DatabaseInterpeter(this.databaseAdapter, this);
    private final UIObjectAdaptor uiObjectAdaptor = new UIObjectAdaptor();
    private final CredentialsAdaptor credentialsAdaptor = new CredentialsAdaptor();
    private final LogicSetup logicSetup = new LogicSetup();
    private UIObject uiObject;
    private UserObject user;
    private ApplicationView applicationView;
    private LoginView loginView;
    private RegisterView registerView;
    private boolean hasNetworkConnection;

    public Controller(Stage _stage) {
        this.stage = _stage;
        this.stage.getScene().getStylesheets().add(ViewManager.getDefaultStyle());
        this.uiObject = (UIObject) this.uiObjectAdaptor.getData();
        this.hasNetworkConnection = LLATUtils.connectedToNet();
        LocalUser();
    }

    public void initialize(URL _url, ResourceBundle _rb) {
    }

    /**
     * @param _viewName
     */
    public void changeViewTo(int _viewName) {
        Pane parentPane = this.getView(_viewName);
        this.stage.getScene().setRoot(parentPane);
        this.stage.setWidth(this.stage.getWidth() - 1);
        this.stage.setWidth(this.stage.getWidth() + 1);
    }

    /**
     * @param _symbolButton
     */
    public void setSymbolInputButtonOnAction(SymbolButton _symbolButton) {
        _symbolButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                EventBus.throwEvent(new SymbolInputEvent(((SymbolButton) event.getSource()).getDefaultSymbol().getSymbol().getApplied()));
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                EventBus.throwEvent(new SymbolDescriptionEvent(((SymbolButton) event.getSource()).getDefaultSymbol()));
            }
        });
    }

    /**
     * @param _canvas
     */
    public void setPaneToZoomable(Pane _canvas) {
        _canvas.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double modifier = 0;
                if (scrollEvent.getDeltaY() < 0) {
                    modifier = .1;
                } else {
                    modifier = -.1;
                }
                if (_canvas.getScaleX() + modifier < ParseTreeView.MAXSCALE && _canvas.getScaleX() + modifier > ParseTreeView.MINSCALE + .1) {
                    _canvas.setScaleX(_canvas.getScaleX() + modifier);
                }
                if (_canvas.getScaleY() + modifier < ParseTreeView.MAXSCALE && _canvas.getScaleY() + modifier > ParseTreeView.MINSCALE + .1) {
                    _canvas.setScaleY(_canvas.getScaleY() + modifier);
                }
            }
        });
    }

    /**
     * @param _canvas
     */
    public void setPaneToPannable(Pane _canvas) {
        _canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseManager curMouse = new MouseManager(mouseEvent.getX(), mouseEvent.getY());
                _canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        double xMovement = _canvas.getTranslateX() + (curMouse.getCurX() - mouseEvent.getX()) / (2 / _canvas.getScaleX());
                        if (xMovement > _canvas.getParent().getLayoutBounds().getMinX() && xMovement + _canvas.getWidth() < _canvas.getParent().getLayoutBounds().getMaxX()) {
                            _canvas.setTranslateX(_canvas.getTranslateX() + (curMouse.getCurX() - mouseEvent.getX()) / (2 / _canvas.getScaleX()));
                            curMouse.setCurX(mouseEvent.getX());
                        }
                        double yMovement = _canvas.getTranslateY() + (curMouse.getCurY() - mouseEvent.getY()) / (2 / _canvas.getScaleX());
                        if (yMovement > _canvas.getParent().getLayoutBounds().getMinY() && yMovement + _canvas.getHeight() < _canvas.getParent().getLayoutBounds().getMaxY()) {
                            _canvas.setTranslateY(_canvas.getTranslateY() + (curMouse.getCurY() - mouseEvent.getY()) / (2 / _canvas.getScaleX()));
                            curMouse.setCurY(mouseEvent.getY());
                        }


                    }
                });
            }
        });
    }

    /**
     * @param _button
     */
    public void setSolveButtonOnAction(Button _button) {
        _button.setOnAction((event) -> {
            EventBus.throwEvent(new SolveButtonEvent());
        });
    }

    /**
     * @param _button
     */
    public void setApplyAlgorithmOnAction(Button _button) {
        _button.setOnAction((event) -> {
            EventBus.throwEvent(new ApplyAlgorithmButtonEvent());
            _button.setDisable(true);
        });
    }

    public void throwExportEvent(ExportType _exportType, String _filePath) {
        switch (_exportType) {
            case LATEX_PARSE_TREE:
                EventBus.throwEvent(new ExportLaTeXParseTreeEvent(_filePath));
                break;
            case LATEX_TRUTH_TREE:
                EventBus.throwEvent(new ExportLaTeXTruthTreeEvent(_filePath));
                break;
            case LATEX_TRUTH_TABLE:
                EventBus.throwEvent(new ExportLaTeXTruthTableEvent(_filePath));
                break;
            case PDF_PARSE_TREE:
                EventBus.throwEvent(new ExportPDFParseTreeEvent(_filePath));
                break;
            case PDF_TRUTH_TREE:
                EventBus.throwEvent(new ExportPDFTruthTreeEvent(_filePath));
                break;
            case PDF_TRUTH_TABLE:
                EventBus.throwEvent(new ExportPDFTruthTableEvent(_filePath));
                break;
        }
    }
    /**
     * @param _menuItem
     * @param _exportType
     */
    public void setExportOnAction(MenuItem _menuItem, ExportType _exportType) {
        _menuItem.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export " + _exportType.getType());
            fileChooser.getExtensionFilters().add(_exportType.getExtensionFilter());
            File file = fileChooser.showSaveDialog(this.getStage());
            if (file == null) {
                return;
            }
            String filePath = file.getAbsolutePath();
            this.throwExportEvent(_exportType, filePath);
        });
    }

    public void loginOnAction(Button _button, TextField _userName, PasswordField _password) {
        _button.setOnAction((event) -> {
            this.user = this.databaseAdapter.Login(_userName.getText(), _password.getText());
            if (this.user != null) {
                EventBus.throwEvent(new LoginSuccessEvent());
                this.changeViewTo(ViewManager.MAINAPPLICATION);
            } else {
                EventBus.throwEvent(new LoginFailEvent());
            }
        });
    }

    public void LocalUser() {
        this.user = this.databaseAdapter.Login();
        if (this.user != null) {
            EventBus.throwEvent(new LoginSuccessEvent());
        } else {
            EventBus.throwEvent(new LoginFailEvent());
        }
    }

    public void registerAction(Button _button, TextField _userName, TextField _firstname, TextField _lastname, PasswordField _password) {
        _button.setOnAction((event) -> {
            int user = this.databaseAdapter.Register(_userName.getText(), _password.getText(), _firstname.getText(), _lastname.getText());
            EventBus.throwEvent(new RegistrationStatusEvent(user));
        });
    }


    public Stage getStage() {
        return this.stage;
    }

    private Pane getView(int _viewName) {
        Pane parentPane = new Pane();
        switch (_viewName) {
            case ViewManager.MAINAPPLICATION: {
                try {
                    parentPane = this.applicationView.getParentPane();
                } catch (Exception e) {
                    this.applicationView = new ApplicationView(this);
                    parentPane = this.applicationView.getParentPane();
                }
                break;
            }
            case ViewManager.LOGIN:
                try {
                    parentPane = this.loginView.getParentPane();
                } catch (Exception e) {
                    this.loginView = (new LoginView(this));
                    parentPane = this.loginView.getParentPane();
                }
                break;
            case ViewManager.REGISTER:
                try {
                    parentPane = this.registerView.getParentPane();
                } catch (Exception e) {
                    this.registerView = (new RegisterView(this));
                    parentPane = this.registerView.getParentPane();
                }
                break;
            default:
                // Update this to error view.
                parentPane = new Pane();
        }
        return parentPane;
    }

    public UserObject getUser() {
        return this.user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }

    public UIObjectAdaptor getUiObjectAdaptor() {
        return this.uiObjectAdaptor;
    }

    public UIObject getUiObject() {
        return this.uiObject;
    }

    public void setUiObject(UIObject uiObject) {
        this.uiObject = uiObject;
    }

    public boolean hasNetworkConnection() {
        return this.hasNetworkConnection;
    }
}
