package sample.tools;

import javafx.scene.control.Button;

public class NodeManager {
    public static Button createButton(String _id, String _text) {
        Button newButton = new Button();
        newButton.setId(_id);
        newButton.setText(_text);
        return newButton;
    }
}
