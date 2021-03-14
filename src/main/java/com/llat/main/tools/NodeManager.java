package com.llat.main.tools;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class NodeManager {
    public static Button createButton(String _id, String _text, String _styleSheetPath) {
        Button curButton = new Button();
        curButton.setId(_id);
        curButton.setText(_text);
        curButton.getStylesheets().add(_styleSheetPath);
        return curButton;
    }

    public static Button createGridButton(String _id, String _text, int _column, int _row, String _styleSheetPath) {
        Button curButton = new Button();
        curButton.setId(_id);
        curButton.setText(_text);
        curButton.getStylesheets().add(_styleSheetPath);
        GridPane.setColumnIndex(curButton, _column);
        GridPane.setRowIndex(curButton, _row);
        return curButton;
    }

    public static Label createLabel(String _id, String _text, String _styleSheetPath) {
        Label curLabel = new Label();
        curLabel.setId(_id);
        curLabel.setText(_text);
        curLabel.getStylesheets().add(_styleSheetPath);
        return curLabel;
    }

    public static Separator createSeparator(String _id, Orientation _orientation, String _styleSheetPath) {
        Separator curSeparator = new Separator();
        curSeparator.setOrientation(_orientation);
        curSeparator.getStylesheets().add(_styleSheetPath);
        curSeparator.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return curSeparator;
    }

    public static VBox createVBox(String _id, int _minWidth, ArrayList<Node> _nodes, String _styleSheetPath) {
        VBox curVBox = new VBox();
        curVBox.setSpacing(50);
        curVBox.setId(_id);
        curVBox.getChildren().addAll(_nodes);
        curVBox.setAlignment(Pos.CENTER);
        curVBox.getStylesheets().add(_styleSheetPath);
        curVBox.setMinWidth(_minWidth);
        return curVBox;
    }

    public static TextField createTextField(String _id, int _minWidth, String _styleSheetPath) {
        TextField curTextField = new TextField();
        curTextField.setId(_id);
        curTextField.setMinWidth(_minWidth);
        curTextField.getStylesheets().add(_styleSheetPath);
        return curTextField;
    }

    public static HBox createHBox(String _id, int _minWidth, int _minHeight, ArrayList<Node> _nodes, String _styleSheetPath) {
        HBox curHBox = new HBox();
        curHBox.setSpacing(100);
        curHBox.setId(_id);
        curHBox.getChildren().addAll(_nodes);
        curHBox.setAlignment(Pos.CENTER);
        curHBox.getStylesheets().add(_styleSheetPath);
        curHBox.setMinHeight(_minHeight);
        curHBox.setMinWidth(_minWidth);
        curHBox.setMaxWidth(Double.MAX_VALUE);
        return curHBox;
    }

    public static AnchorPane createAnchorPane(String _id, ArrayList<Node> _nodes, String _styleSheetPath) {
        AnchorPane curPane = new AnchorPane();
        curPane.setId(_id);
        curPane.getChildren().addAll(_nodes);
        curPane.getStylesheets().add(_styleSheetPath);
        curPane.setMinWidth(250);
        return curPane;
    }

    public static GridPane createGridPane(String _id, ArrayList<Node> _nodes, String _styleSheetPath) {
        GridPane curPane = new GridPane();
        curPane.setId(_id);
        curPane.getChildren().addAll(_nodes);
        curPane.getStylesheets().add(_styleSheetPath);
        return curPane;
    }

}
