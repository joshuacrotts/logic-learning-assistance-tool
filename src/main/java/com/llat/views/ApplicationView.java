package com.llat.views;

import com.llat.controller.Controller;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ApplicationView {
    Controller controller;
    AnchorPane parentPane = new AnchorPane();
    VBox leftVBox = new VBox();
    VBox centerVBox = new VBox();
    VBox rightVBox = new VBox();
    public ApplicationView (Controller _controller) {

    }

}
