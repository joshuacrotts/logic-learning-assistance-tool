package sample.view;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.controller.Controller;
import sample.tools.NodeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolInputView {
    private Controller controller;
    private AnchorPane parentPane = new AnchorPane();

    private GridPane propositionalLogicGrid;
    private ArrayList<Button> propositionalLogicButtons = new ArrayList<Button>();
    Map<String, String> propositionalLogicSymbols = new HashMap<String, String>() {
        {
            put("andButton", "∧");
            put("orButton", "∨");
            put("negationButton", "¬");
            put("xorButton", "xor");
            put("biconditionalButton", "⇔");
            put("implicationButton", "⇒");
            put("thereforeButton", "⊢");
            put("modelButton", "|=");
        }
    };
    Map<String, String> predicateLogicSymbols = new HashMap<String, String>() {
        {
            put("existentialQuantifierSymbol", "Existential Quantifier");
            put("universalQuantifierSymbol", "Universal Quantifier");
        }
    };

    public SymbolInputView(Controller _controller) {
        this.controller = _controller;
        this.propositionalLogicGrid = new GridPane();
        propositionalLogicSymbols.forEach((buttonId, buttonText) -> {
            propositionalLogicButtons.add(NodeManager.createButton(buttonId, buttonText));
        });
        int counter = 0;
        for (int row = 0; row < 2; row ++) {
            for (int col = 0; col < 4; col ++) {
                propositionalLogicGrid.add(propositionalLogicButtons.get(counter), row, col);
                counter++;
                System.out.println(counter);
            }
        }
        this.parentPane.getChildren().add(propositionalLogicGrid);
    }

    public AnchorPane getParentPane() {
        return this.parentPane;
    }
}
