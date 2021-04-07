package com.llat.views;

import com.llat.controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AlgorithmSelectionView {
    Controller controller;
    Stage stage;
    HBox algorithmSelectionHBox = new HBox();
    ComboBox generalComboBox = new ComboBox();
    ComboBox propositionalComboBox = new ComboBox();
    ComboBox predicateComboBox = new ComboBox();



    public AlgorithmSelectionView(Controller _controller) {
        this.controller = _controller;
        this.stage = controller.getStage();
        // Setting HBox algorithmSelectionHBox properties.
        this.algorithmSelectionHBox.setSpacing(100);
        this.algorithmSelectionHBox.setAlignment(Pos.CENTER);
        this.algorithmSelectionHBox.setPadding(new Insets(10, 10, 10, 10));
        this.generalComboBox.setPromptText("General:");
        // Setting ComboBox generalComboBox properties.
        this.generalComboBox.getItems().addAll(
                "Argument Truth Tree Validator (>=2)",
                "Closed Tree Determiner (1)",
                "Logical Falsehood Determiner (1)",
                "Logically Consistent Determiner (2)",
                "Logically Contingent Determiner (1)",
                "Logically Contradictory Determiner (2)",
                "Logically Contrary Determiner (2)",
                "Logically Equivalent Determiner (2)",
                "Logically Implied Determiner (2)",
                "Logical Tautology Determiner (1)",
                "Main operator detector (1)",
                "Open Tree Determiner (1)"
        );
        // setting ComboBox propositionalComboBox properties.
        this.propositionalComboBox.setPromptText("Propositional:");
        this.propositionalComboBox.getItems().addAll(
                "Propositional Truth Tree Generator (1)",
                "Random Formula generation",
                "Truth Table Generator (1)"
        );
        // Setting ComboBox predicateComboBox properties.
        this.predicateComboBox.setPromptText("Predicate:");
        this.predicateComboBox.getItems().addAll(
                "Bound Variable Detector (1)",
                "Closed Sentence Determiner (1)",
                "Free Variable Detector (1)",
                "Ground Sentence Determiner (1)",
                "Open Sentence Determiner (1)",
                "Predicate Truth Tree Generator (1)"
        );
        // Adding children nodes to their parents nodes.
        this.algorithmSelectionHBox.getChildren().addAll(this.generalComboBox, this.propositionalComboBox, this.predicateComboBox);

    }

    public Pane getParentPane() {
        return this.algorithmSelectionHBox;
    }

}
