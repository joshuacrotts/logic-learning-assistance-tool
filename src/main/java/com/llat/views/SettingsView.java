package com.llat.views;

import com.llat.controller.Controller;
import com.llat.main.App;
import com.llat.tools.ViewManager;
import com.llat.views.interpreters.SettingsViewInterpreter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SettingsView {
    private final int MINWIDTH = 750;
    private final int MINHEIGHT = 500;
    private Controller controller;
    private Stage settingsStage = new Stage();
    private Scene settingsScene = new Scene(new Pane());
    private VBox parentPane = new VBox();
    private HBox categoryAndSelectionHBox = new HBox();
    private VBox categoryVBox = new VBox();
    private Button appearanceButton = new Button("Appearance");
    private Button languageButton = new Button("Language");
    private Button advanceButton = new Button("Advance");
    // Appearance settings VBox.
    private VBox appearanceVBox = new VBox();
    private Label appearanceTitle = new Label("Appearance");
    private HBox themeHBox = new HBox();
    private Label themeLabel = new Label("Theme:");
    private MenuButton themeMenuButton = new MenuButton();
    // Language settings VBox.
    private VBox languageVBox = new VBox();
    private Label languageTitle = new Label("Language");
    private HBox languageHBox = new HBox();
    private Label languageLabel = new Label("Language");
    private MenuButton languageMenuButton = new MenuButton();
    // Advance settings VBox.
    private VBox advanceVBox = new VBox();
    private Label advanceTitle = new Label("Advance");
    // Bottom settings HBox.
    private HBox bottomHBox = new HBox();
    private Region emptyRegion = new Region();
    private Button cancelButton = new Button("Cancel");
    private Button saveButton = new Button("Save");
    private SettingsViewInterpreter settingsViewInterpreter;

    public SettingsView(Controller _controller) {
        this.controller = _controller;
        // Setting Stage settingsStage properties.
        this.settingsStage.setTitle("Settings");
        this.settingsStage.getIcons().add(new Image(App.class.getResourceAsStream("/assets/images/LogoStatic.png")));
        this.settingsStage.setScene(this.settingsScene);
        this.settingsStage.setMinWidth(this.MINWIDTH);
        this.settingsStage.setWidth(this.MINWIDTH);
        this.settingsStage.setMaxWidth(this.MINWIDTH);
        this.settingsStage.setMinHeight(this.MINHEIGHT);
        this.settingsStage.setHeight(this.MINHEIGHT);
        this.settingsStage.setMaxHeight(this.MINHEIGHT);
        this.settingsStage.setResizable(false);
        this.settingsStage.toFront();
        this.settingsStage.initModality(Modality.WINDOW_MODAL);
        this.settingsStage.initOwner(this.controller.getStage().getScene().getWindow());
        this.settingsStage.centerOnScreen();
        // Setting Scene settingsScene properties.
        this.settingsScene.getStylesheets().add(ViewManager.getDefaultStyle("settings.css"));
        // Setting VBox parentPane properties.
        this.parentPane.setId("settingsParentPane");
        this.parentPane.setMinWidth(this.settingsStage.getWidth());
        this.parentPane.setMaxWidth(this.settingsStage.getWidth());
        this.parentPane.setMinHeight(this.settingsStage.getHeight());
        this.parentPane.setMaxHeight(this.settingsStage.getHeight());
        // Setting HBox categoryAndSelectionHBox properties.
        VBox.setVgrow(this.categoryAndSelectionHBox, Priority.ALWAYS);
        // Setting VBox categoryVBox properties.
        this.categoryVBox.setId("categoryVBox");
        //VBox.setVgrow(this.categoryVBox, Priority.ALWAYS);
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.categoryVBox.setMinWidth(newVal.doubleValue() * .5);
            this.categoryVBox.setMaxWidth(newVal.doubleValue() * .5);
        });
        // Setting Button appearanceButton properties.
        this.appearanceButton.requestFocus();
        this.appearanceButton.setId("settingsCategoryButton");
        this.appearanceButton.setMaxHeight(this.categoryVBox.getHeight() * .10);
        this.categoryVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.appearanceButton.setMaxWidth(newVal.doubleValue());
        });
        this.categoryVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.appearanceButton.setMaxHeight(newVal.doubleValue() * .10);
        });
        // Setting Button languageButton properties.
        this.languageButton.setId("settingsCategoryButton");
        this.languageButton.setMaxHeight(this.categoryVBox.getHeight() * .10);
        this.categoryVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.languageButton.setMaxWidth(newVal.doubleValue());
        });
        this.categoryVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.languageButton.setMaxHeight(newVal.doubleValue() * .10);
        });
        // Setting Button advanceButton properties.
        this.advanceButton.setId("settingsCategoryButton");
        this.advanceButton.setMaxHeight(this.categoryVBox.getHeight() * .10);
        this.categoryVBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.advanceButton.setMaxWidth(newVal.doubleValue());
        });
        this.categoryVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.advanceButton.setMaxHeight(newVal.doubleValue() * .10);
        });
        // Setting VBox appearanceVBox properties.
        this.appearanceVBox.setId("selectedCategoryVBox");
        HBox.setHgrow(this.appearanceVBox, Priority.ALWAYS);
        this.appearanceVBox.setAlignment(Pos.TOP_CENTER);
        // Setting Label appearanceTitle properties.
        this.appearanceTitle.setId("categoryTitle");
        // Setting HBox themeHBox properties.
        VBox.setVgrow(this.themeHBox, Priority.ALWAYS);
        this.themeHBox.setAlignment(Pos.CENTER);
        // Setting Label themeLabel properties.
        this.themeLabel.setId("comboBoxLabel");
        this.themeLabel.setPadding(new Insets(0, 10, 0, 0));
        // Setting ComboBox themeComboBox properties.
        // Setting VBox languageVBox properties.
        this.languageVBox.setId("selectedCategoryVBox");
        HBox.setHgrow(this.languageVBox, Priority.ALWAYS);
        this.languageVBox.setAlignment(Pos.TOP_CENTER);
        // Setting Label languageTitle properties.
        this.languageTitle.setId("categoryTitle");
        // Setting HBox languageHBox properties.
        VBox.setVgrow(this.languageHBox, Priority.ALWAYS);
        this.languageHBox.setAlignment(Pos.CENTER);
        // Setting Label languageLabel properties.
        this.languageLabel.setId("comboBoxLabel");
        this.languageLabel.setPadding(new Insets(0, 10, 0, 0));
        // Setting VBox advanceVBox properties.
        this.advanceVBox.setId("selectedCategoryVBox");
        HBox.setHgrow(this.advanceVBox, Priority.ALWAYS);
        this.advanceVBox.setAlignment(Pos.TOP_CENTER);
        // Setting Label advanceTitle properties.
        this.advanceTitle.setId("categoryTitle");
        // Setting HBox bottomHBox properties.
        this.bottomHBox.setId("bottomHBox");
        this.bottomHBox.setAlignment(Pos.CENTER_RIGHT);
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.bottomHBox.setMinHeight(newVal.doubleValue() * .10);
            this.bottomHBox.setMaxHeight(newVal.doubleValue() * .10);
        });
        // Setting Region emptyRegion properties.
        this.bottomHBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.emptyRegion.setMinWidth(newVal.doubleValue() * .5);
            this.emptyRegion.setMaxWidth(newVal.doubleValue() * .5);
        });
        // Setting Button cancelButton properties.
        this.cancelButton.setId("cancelButton");
        HBox.setMargin(this.cancelButton, new Insets(0, 10, 0, 10));
        HBox.setHgrow(this.cancelButton, Priority.ALWAYS);
        this.cancelButton.setMaxWidth(Double.MAX_VALUE);
        this.bottomHBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.cancelButton.setMinHeight(newVal.doubleValue() * 0.80);
            this.cancelButton.setMaxHeight(newVal.doubleValue() * 0.80);
        });
        // Setting Button saveButton properties.
        this.saveButton.setId("saveButton");
        HBox.setMargin(this.saveButton, new Insets(0, 10, 0, 0));
        HBox.setHgrow(this.saveButton, Priority.ALWAYS);
        this.saveButton.setMaxWidth(Double.MAX_VALUE);
        this.bottomHBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.saveButton.setMinHeight(newVal.doubleValue() * 0.80);
            this.saveButton.setMaxHeight(newVal.doubleValue() * 0.80);
        });
        // Adding children nodes to their parents nodes.
        this.categoryVBox.getChildren().addAll(this.appearanceButton, this.languageButton, this.advanceButton);
        this.themeHBox.getChildren().addAll(this.themeLabel, this.themeMenuButton);
        this.appearanceVBox.getChildren().addAll(this.appearanceTitle, this.themeHBox);
        this.languageHBox.getChildren().addAll(this.languageLabel, this.languageMenuButton);
        this.languageVBox.getChildren().addAll(this.languageTitle, this.languageHBox);
        this.categoryAndSelectionHBox.getChildren().addAll(this.categoryVBox, this.appearanceVBox);
        this.advanceVBox.getChildren().addAll(this.advanceTitle);
        this.bottomHBox.getChildren().addAll(this.emptyRegion, this.cancelButton, this.saveButton);
        this.parentPane.getChildren().addAll(this.categoryAndSelectionHBox, this.bottomHBox);
        this.settingsStage.getScene().setRoot(this.parentPane);
        this.settingsViewInterpreter = new SettingsViewInterpreter(this.controller, this);
    }

    public int getMINWIDTH() {
        return MINWIDTH;
    }

    public int getMINHEIGHT() {
        return MINHEIGHT;
    }

    public Controller getController() {
        return controller;
    }

    public Stage getSettingsStage() {
        return settingsStage;
    }

    public Scene getSettingsScene() {
        return settingsScene;
    }

    public VBox getParentPane() {
        return parentPane;
    }

    public HBox getCategoryAndSelectionHBox() {
        return categoryAndSelectionHBox;
    }

    public VBox getCategoryVBox() {
        return categoryVBox;
    }

    public Button getAppearanceButton() {
        return appearanceButton;
    }

    public Button getLanguageButton() {
        return languageButton;
    }

    public Button getAdvanceButton() {
        return advanceButton;
    }

    public VBox getAppearanceVBox() {
        return appearanceVBox;
    }

    public Label getAppearanceTitle() {
        return appearanceTitle;
    }

    public HBox getThemeHBox() {
        return themeHBox;
    }

    public Label getThemeLabel() {
        return themeLabel;
    }

    public MenuButton getThemeMenuButton() {
        return themeMenuButton;
    }

    public VBox getLanguageVBox() {
        return languageVBox;
    }

    public Label getLanguageTitle() {
        return languageTitle;
    }

    public HBox getLanguageHBox() {
        return languageHBox;
    }

    public Label getLanguageLabel() {
        return languageLabel;
    }

    public MenuButton getLanguageMenuButton() {
        return languageMenuButton;
    }

    public VBox getAdvanceVBox() {
        return advanceVBox;
    }

    public Label getAdvanceTitle() {
        return advanceTitle;
    }

    public HBox getBottomHBox() {
        return bottomHBox;
    }

    public Region getEmptyRegion() {
        return emptyRegion;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

}
