package com.llat.controller;

import java.io.IOException;

import com.llat.main.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("/fxml/primary");
    }
}