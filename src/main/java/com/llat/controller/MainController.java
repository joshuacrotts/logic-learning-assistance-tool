package com.llat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

import java.net.URL;
import java.util.ResourceBundle;

import static com.llat.models.log.DisplayLogs.note;

public class MainController implements Initializable {
    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleMenuBar();
    }


    private void handleMenuBar() {
        final String os = System.getProperty("os.name");
        note("Current Operating system is: " + os, true);
        if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
        }
    }
}