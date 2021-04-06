package com.llat.views;

import com.llat.controller.Controller;
import com.llat.main.Window;
import com.llat.models.localstorage.settings.ItemObject;
import com.llat.models.localstorage.settings.SettingsAdaptor;
import com.llat.models.localstorage.settings.SettingsObject;
import com.llat.models.localstorage.settings.langague.LangaugeObject;
import com.llat.models.localstorage.settings.theme.ThemeObject;
import com.llat.tools.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.llat.views.SettingsView.WIDTH;

public class SettingsView {
    private Controller controller;
    private AnchorPane parentPane = new AnchorPane();
    private Stage stage;
    private Stage settingsStage;
    private Pane leftPane = new Pane();
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    SettingsAdaptor sa = new SettingsAdaptor();
    SettingsObject so = (SettingsObject) sa.getData();


    private HBox hBox = new HBox();
    private VBox vBox1 = new VBox();
    private VBox vBox2 = new VBox();

    private Button appearanceButton = new Button("Appearance");
    private Button languageButton = new Button("Language");
    private Button advanceButton = new Button("Advance");
    private ArrayList<Button> categoryButtonsList = new ArrayList<Button>() {
        {
            {
                add(appearanceButton);
                add(languageButton);
                add(advanceButton);
            }
        }
    };

    private Button cancelButton = new Button("Cancel");
    private Button saveButton = new Button("Save");


    // pane
    private List<Pane> paneList = new ArrayList<>();
    private Pane appearancePane;
    private Pane languagePane;
    private Pane advancePane;

    // view id
    private final static int APPEARANCE_ID = 0;
    private final static int LANGUAGE_ID = 1;
    private final static int ADVANCE_ID = 2;

    public SettingsView(Controller controller) {
        this.controller = controller;
        this.stage = this.controller.getStage();
        appearancePane = new SettingsPane(this.controller, "Appearance").getParentPane();
        languagePane = new SettingsPane(this.controller, "Language").getParentPane();
        advancePane = new SettingsPane(this.controller, "Advance").getParentPane();
        // setting the vBox size
        this.hBox.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.hBox.setMinWidth(stage.getWidth());
            this.hBox.setMinHeight(stage.getHeight() * .01);
            this.hBox.setMaxHeight(stage.getHeight());
        }));
        // setting the buttons
        appearanceButton.setId("settingsCategoryOnPress");
        languageButton.setId("settingsCategory");
        advanceButton.setId("settingsCategory");
        leftPane.setId("leftPane");
        parentPane.setId("parentPane");
        appearancePane.setId("settingsMainPane");
        languagePane.setId("settingsMainPane");
        advancePane.setId("settingsMainPane");

        this.appearanceButton.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.appearanceButton.setMinWidth(vBox2.getLayoutBounds().getWidth());
        }));
        this.languageButton.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.languageButton.setMinWidth(vBox2.getLayoutBounds().getWidth());
        }));
        this.advanceButton.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.advanceButton.setMinWidth(vBox2.getLayoutBounds().getWidth());
        }));

        appearanceSetUp();
        languageSetUp();
        // adding the view panes to the list
        this.paneList.add(appearancePane);
        this.paneList.add(languagePane);
        this.paneList.add(advancePane);

        this.leftPane.heightProperty().addListener(((observable, oldValue, newValue) -> {
            this.leftPane.setMinHeight(stage.getScene().getHeight() * 0.454);
        }));
        this.hBox.getChildren().addAll(leftPane, paneList.get(APPEARANCE_ID));
        this.languageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hBox.getChildren().remove(1);
                hBox.getChildren().add(languagePane);
                onPress(languageButton, languagePane);

            }
        });
        this.appearanceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hBox.getChildren().remove(1);
                hBox.getChildren().add(appearancePane);
                onPress(appearanceButton, appearancePane);
            }
        });
        this.advanceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hBox.getChildren().remove(1);
                hBox.getChildren().add(advancePane);
                onPress(advanceButton, advancePane);

            }
        });
        Pane bottomPane = new Pane();
        HBox bottomPaneHbox = new HBox();
        bottomPane.setStyle("-fx-background-color: black");

        // adding buttons to the vBox
        this.vBox2.getChildren().addAll(appearanceButton, languageButton, advanceButton);

        leftPane.getChildren().addAll(vBox2);


//        bottomPaneHbox.widthProperty().addListener(((observable, oldValue, newValue) -> {
//            bottomPaneHbox.setMinWidth(bottomPane.getLayoutBounds().getWidth());
//        }));
//        bottomPaneHbox.setStyle("-fx-background-color: black");

        // bottom pane buttons
        cancelButton.setId("bottonPaneButtons");
        saveButton.setId("bottonPaneButtons");

        cancelButton.setOnAction(e -> {
            settingsStage.close();
        });
        saveButton.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Saving the current changes will require the application to restart. Are you sure you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                updateLocalStorage();
                this.settingsStage.close();
                this.stage.close();
                new Window(new Stage());

            } else {
                // ... user chose CANCEL or closed the dialog

            }

        });

        bottomPaneHbox.setSpacing(10);
        bottomPaneHbox.setMargin(cancelButton, new Insets(5, 10, 10, 550));
        bottomPaneHbox.setMargin(saveButton, new Insets(5, 20, 10, 20));
        bottomPaneHbox.setId("bottomPaneHbox");
        bottomPaneHbox.getChildren().addAll(cancelButton, saveButton);
//        bottomPane.getChildren().add(bottomPaneHbox);

        vBox1.getChildren().addAll(hBox, bottomPaneHbox);
        this.parentPane.getChildren().addAll(vBox1);


        Scene secondScene = new Scene(parentPane, 720, 500);
        secondScene.getStylesheets().add(ViewManager.getDefaultStyle("settings.css"));

        // New window (Stage)
        this.settingsStage = new Stage();
        this.settingsStage.setTitle("Settings");
        this.settingsStage.setScene(secondScene);

        // Specifies the modality for new window.
        this.settingsStage.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        this.settingsStage.initOwner(stage);
        this.settingsStage.setResizable(false);

    }

    private void updateLocalStorage() {
        sa.update(so);
    }


    public void onPress(Button btn, Pane pane) {
        hBox.getChildren().remove(1);
        hBox.getChildren().add(pane);
        for (int i = 0; i < categoryButtonsList.size(); i++) {
            if (btn == categoryButtonsList.get(i)) {
                categoryButtonsList.get(i).setId("settingsCategoryOnPress");
            } else {
                categoryButtonsList.get(i).setId("settingsCategory");

            }
        }
    }


    public AnchorPane getParentPane() {
        return this.parentPane;
    }

    public Stage getStage() {
        return settingsStage;
    }

    public void appearanceSetUp() {
        String appliedTheme = so.getTheme().getApplied().getName();
        List<ThemeObject> themeList = so.getTheme().getAllThemes();
        Label themeLabel = new Label("Theme");
        HBox appearanceHBox = new HBox();
        MenuButton themeMenu = new MenuButton(appliedTheme);
        // populate the theme menu
        for (int i = 0; i < themeList.size(); i++) {
            CustomMenuItem menuItem = new CustomMenuItem(themeList.get(i));
            themeMenu.getItems().add(menuItem);

            menuItem.setOnAction(e -> {
                themeMenu.setText(menuItem.getText());
                so.getTheme().setApplied((ThemeObject) menuItem.getContent());
            });
        }

        themeLabel.setId("themeLabel");
        themeMenu.setId("themeMenu");


        //Setting the space between the nodes of a VBox pane
        appearanceHBox.setSpacing(10);
        //Setting the margin to the nodes
        appearanceHBox.setMargin(themeLabel, new Insets(150, 20, 20, 20));
        appearanceHBox.setMargin(themeMenu, new Insets(150, 20, 20, 20));

//        appearanceHBox.setStyle("-fx-background-color: black");
        appearanceHBox.getChildren().addAll(themeLabel, themeMenu);
        this.appearancePane.getChildren().addAll(appearanceHBox);
    }

    public void languageSetUp() {
        String appliedLang = so.getLanguage().getApplied().getName();
        List<LangaugeObject> langList = so.getLanguage().getAllLanguages();
        Label langLabel = new Label("Language");
        HBox langHBox = new HBox();
        MenuButton langMenu = new MenuButton(appliedLang);
        // populate the theme menu
        for (int i = 0; i < langList.size(); i++) {
            CustomMenuItem menuItem = new CustomMenuItem(langList.get(i));
            langMenu.getItems().add(menuItem);

            menuItem.setOnAction(e -> {
                langMenu.setText(menuItem.getText());
                langMenu.setText(menuItem.getText());
                so.getLanguage().setApplied((LangaugeObject) menuItem.getContent());

            });
        }

        langLabel.setId("langLabel");
        langMenu.setId("langMenu");


        //Setting the space between the nodes of a VBox pane
        langHBox.setSpacing(10);
        //Setting the margin to the nodes
        langHBox.setMargin(langLabel, new Insets(150, 20, 20, 20));
        langHBox.setMargin(langMenu, new Insets(150, 20, 20, 20));

        langHBox.getChildren().addAll(langLabel, langMenu);
        this.languagePane.getChildren().addAll(langHBox);
    }

    public void advanceSetUp() {

    }
}

class SettingsPane {
    Controller controller;
    Stage stage;
    Pane parentPane = new Pane();
    VBox newVBox = new VBox();
    // labels
    private Label paneTitle = new Label();

    public SettingsPane(Controller _controller, String _title) {
        this.controller = _controller;
        this.stage = controller.getStage();
        paneTitle.setId("categoryTitle");
        paneTitle.setText(_title);

        newVBox.getChildren().add(paneTitle);
        this.newVBox.setAlignment(Pos.CENTER);
        this.newVBox.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.newVBox.setMinWidth(WIDTH * 0.7);
        }));
        newVBox.setId("vBoxTest");
        this.parentPane.widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.parentPane.setMinWidth(stage.getScene().getWidth());
        }));
        parentPane.getChildren().addAll(newVBox);
    }

    public Pane getParentPane() {
        return parentPane;
    }
}

class CustomMenuItem extends MenuItem{
    ItemObject content;

    public CustomMenuItem( ItemObject content) {
        this.setText(content.getName());
        this.content = content;
    }

    public ItemObject getContent() {
        return content;
    }

    public void setContent(ItemObject content) {
        this.content = content;
    }
}