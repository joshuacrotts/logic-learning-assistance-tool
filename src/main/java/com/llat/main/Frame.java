package com.llat.main;

//import com.llat.models.theme.Theme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.llat.models.log.DisplayLogs.*;

/**
 *
 */
public class Frame {

    /**
     *
     */
    private final static String FXML_PATH = "/fxml/";

    /**
     *
     */
    private static Scene scene;

    /**
     *
     */
    private final String os = System.getProperty("os.name");

    public Frame(Stage _primaryStage, String _fileName) {
        state("Running " + _fileName + " view");
        scene = new Scene(Objects.requireNonNull(loadFXML(FXML_PATH + _fileName)));
        loadTheme();
        _primaryStage.setScene(scene);
        _primaryStage.show();

    }

    /**
     *
     * @param fxml
     * @return
     */
    private Parent loadFXML(String fxml) {
        subState("Loading FXML static file", true);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        subState("Loading FXML static file", false);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    private void loadTheme() {
        //Get the theme that been saved from setting file
        String theme = "";
        try {
            subState("Loading CSS static file", true);
//            theme = Theme.getTheme();
            subState("Loading CSS static file", false);
        } catch (Exception e) {
            error(e.getMessage());
        }

        subState("Apply CSS style to the scene", true);
        try {
            scene.getStylesheets().add(theme);
            subState("Apply CSS style to the scene", false);

        } catch (Exception x) {
            error("Failed to load css");
        }
    }

    /**
     *
     */
    private void menubar() {
        MenuBar menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true);
        Menu menu = new Menu("java");
        MenuItem item = new MenuItem("Test");
        menu.getItems().add(item);
        menuBar.getMenus().add(menu);
//        scene(new Pane(menuBar));

    }
}
