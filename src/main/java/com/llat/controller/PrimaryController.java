package com.llat.controller;

import java.io.IOException;

import com.llat.main.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/fxml/secondary");
    }
}
