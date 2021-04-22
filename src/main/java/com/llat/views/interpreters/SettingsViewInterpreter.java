package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.models.localstorage.settings.language.LanguageObject;
import com.llat.models.localstorage.settings.theme.ThemeObject;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.tools.ViewManager;
import com.llat.views.SettingsView;
import com.llat.views.menu.items.CustomMenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewInterpreter implements Listener {
    private Controller controller;
    private final SettingsView settingsView;
    private VBox selectedCategory = null;
    private ArrayList<Button> categoryButtonsList;

    public SettingsViewInterpreter(Controller _controller, SettingsView _settingsView) {
        this.controller = _controller;
        this.settingsView = _settingsView;
        this.categoryButtonsList = new ArrayList<Button>() {
            {
                {
                    this.add(settingsView.getAppearanceButton());
                    this.add(settingsView.getLanguageButton());
                    this.add(settingsView.getAdvanceButton());
                }
            }
        };
        this.settingsView.getSettingsScene().getStylesheets().add(ViewManager.getDefaultStyle(this.controller.getSettingsObject().getTheme().getApplied().getCode()));
        // Setting the text so that it matches the user's selected language.
        this.settingsView.getAppearanceButton().setText(this.controller.getUiObject().getSettingsView().getCategories().getAppearance().getLabel());
        this.settingsView.getAppearanceTitle().setText(this.controller.getUiObject().getSettingsView().getCategories().getAppearance().getLabel());
        this.settingsView.getThemeLabel().setText(this.controller.getUiObject().getSettingsView().getCategories().getAppearance().getTheme().getLabel() + ":");
        this.settingsView.getLanguageButton().setText(this.controller.getUiObject().getSettingsView().getCategories().getLanguage().getLabel());
        this.settingsView.getLanguageTitle().setText(this.controller.getUiObject().getSettingsView().getCategories().getLanguage().getLabel());
        this.settingsView.getLanguageLabel().setText(this.controller.getUiObject().getSettingsView().getCategories().getLanguage().getLabel() + ":");
        this.settingsView.getAdvanceButton().setText(this.controller.getUiObject().getSettingsView().getCategories().getAdvanced().getLabel());
        this.settingsView.getAdvanceTitle().setText(this.controller.getUiObject().getSettingsView().getCategories().getAdvanced().getLabel());
        this.settingsView.getCancelButton().setText(this.controller.getUiObject().getSettingsView().getCancel());
        this.settingsView.getSaveButton().setText(this.controller.getUiObject().getSettingsView().getSave());
        // Setting the default selected category.
        this.selectedCategory = this.settingsView.getAppearanceVBox();
        // Populating the theme MenuButton with themes.
        this.populateThemes();
        // Setting theme MenuButton's item's on action.
        this.onActionSetTheme();
        // Populating the language MenuButton with themes.
        this.populateLanguages();
        // Setting language MenuButton's item's on action.
        this.onActionSetLanguage();
        // Setting the actions of the category buttons.
        this.showSelectedCategory(this.settingsView.getAppearanceButton(), this.settingsView.getAppearanceVBox());
        this.showSelectedCategory(this.settingsView.getLanguageButton(), this.settingsView.getLanguageVBox());
        this.showSelectedCategory(this.settingsView.getAdvanceButton(), this.settingsView.getAdvanceVBox());
        // Setting the actions of the cancel button.
        this.settingsView.getCancelButton().setOnAction((event) -> {
            this.settingsView.getSettingsStage().close();
        });
        // Setting the actions of the save button.
        this.onActionSaveAlert(this.settingsView.getSaveButton());
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
    }

    public void showSelectedCategory(Button _button, VBox _categoryVBox) {
        _button.setOnAction((event) -> {
            this.settingsView.getCategoryAndSelectionHBox().getChildren().remove(this.selectedCategory);
            this.selectedCategory = _categoryVBox;
            this.settingsView.getCategoryAndSelectionHBox().getChildren().add(this.selectedCategory);
            for (Button button : this.categoryButtonsList) {
                if (_button == button) {
                    button.setId("settingsCategoryButtonOnPress");
                } else {
                    button.setId("settingsCategoryButton");
                }
            }
        });
    }

    public void onActionSaveAlert(Button _button) {
        _button.setOnAction((event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(this.controller.getUiObject().getSettingsView().getConfirmation().getLabel());
            alert.setHeaderText(this.controller.getUiObject().getSettingsView().getConfirmation().getAlertHeader());
            alert.setContentText(this.controller.getUiObject().getSettingsView().getConfirmation().getAlertContent());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(ViewManager.getDefaultStyle());
            alert.widthProperty().addListener((obs, oldVal, newVal) -> {
                alert.setX((this.settingsView.getSettingsStage().getX()) + this.settingsView.getSettingsStage().getScene().getX() + (this.settingsView.getSettingsStage().getWidth() / 2) - (alert.getWidth() / 2));
            });
            alert.heightProperty().addListener((obs, oldVal, newVal) -> {
                alert.setY((this.settingsView.getSettingsStage().getY()) + this.settingsView.getSettingsStage().getScene().getY() +  (this.settingsView.getSettingsStage().getHeight() / 2) - (alert.getHeight() / 2));
            });
            if ((alert.showAndWait()).get() == ButtonType.OK) {
                this.controller.updateLocalStorage();
                this.controller.updateDatabase();
                this.settingsView.getSettingsStage().close();
                this.controller.getStage().close();
                this.controller.restartApplication();
            } else {
                alert.close();
            }
        });
    }

    public void populateThemes() {
        this.settingsView.getThemeMenuButton().setText(this.controller.getAppliedTheme());
        List<ThemeObject> themesList = this.controller.getAllThemes();
        themesList.forEach((theme) -> {
            this.settingsView.getThemeMenuButton().getItems().add(new CustomMenuItem(theme));
        });
    }

    public void onActionSetTheme() {
        this.settingsView.getThemeMenuButton().getItems().forEach((menuItem) -> {
            menuItem.setOnAction((event) -> {
                this.settingsView.getThemeMenuButton().setText(menuItem.getText());
                this.controller.setAppliedTheme((ThemeObject) ((CustomMenuItem) menuItem).getContent());
            });
        });
    }

    public void populateLanguages() {
        this.settingsView.getLanguageMenuButton().setText(this.controller.getAppliedLanguage());
        List<LanguageObject> languageList = this.controller.getAllLanguages();
        languageList.forEach((language) -> {
            this.settingsView.getLanguageMenuButton().getItems().add(new CustomMenuItem(language));
        });
    }

    public void onActionSetLanguage() {
        this.settingsView.getLanguageMenuButton().getItems().forEach((menuItem) -> {
            menuItem.setOnAction((event) -> {
                this.settingsView.getLanguageMenuButton().setText(menuItem.getText());
                this.controller.setAppliedLanguage((LanguageObject) ((CustomMenuItem) menuItem).getContent());
            });
        });
    }

}
