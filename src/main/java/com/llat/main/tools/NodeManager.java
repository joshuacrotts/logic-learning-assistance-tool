package com.llat.main.tools;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class NodeManager {
    public static Button createButton(String _id, String _text) {
        Button newButton = new Button();
        newButton.setId(_id);
        newButton.setText(_text);
        return newButton;
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
}
