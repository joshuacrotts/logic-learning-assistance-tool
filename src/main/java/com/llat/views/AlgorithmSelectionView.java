package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.interpreters.AlgorithmSelectionViewInterpreter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AlgorithmSelectionView {

    private final Controller controller;
    private final Stage stage;
    private final HBox comboHBox = new HBox();
    private final AlgorithmSelectionViewInterpreter algorithmSelectionViewInterpreter;
    private ComboBox generalComboBox = new ComboBox();
    private ComboBox propositionalComboBox = new ComboBox();
    private ComboBox predicateComboBox = new ComboBox();

    public AlgorithmSelectionView(Controller _controller) {
        this.controller = _controller;
        this.stage = this.controller.getStage();

        // Setting HBox algorithmSelectionHBox properties.
        this.comboHBox.setSpacing(50);
        this.comboHBox.setAlignment(Pos.CENTER);
        this.comboHBox.setPadding(new Insets(10, 10, 10, 10));

        // Adding children nodes to their parents nodes.
        this.createComboBoxes();
        this.algorithmSelectionViewInterpreter = new AlgorithmSelectionViewInterpreter(this.controller, this);
    }

    /**
     * @param _comboBox
     * @param _id
     */
    public void setComboBoxProperties(ComboBox _comboBox, String _id) {
        _comboBox.setPromptText(_id);
        _comboBox.setMinWidth(this.stage.getScene().getWidth() * .15);
        _comboBox.setMaxWidth(this.stage.getScene().getWidth() * .15);
        this.stage.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            _comboBox.setMinWidth(newVal.doubleValue() * .15);
            _comboBox.setMaxWidth(newVal.doubleValue() * .15);
        });
    }

    /**
     *
     */
    public void clearComboBoxes() {
        this.comboHBox.getChildren().removeAll(this.getGeneralComboBox(), this.getPropositionalComboBox(), this.getPredicateComboBox());
    }

    /**
     *
     */
    public void createComboBoxes() {
        this.setGeneralComboBox(new ComboBox());
        this.setComboBoxProperties(this.getGeneralComboBox(), "General:");
        this.setPropositionalComboBox(new ComboBox());
        this.setComboBoxProperties(this.getPropositionalComboBox(), "Propositional:");
        this.setPredicateComboBox(new ComboBox());
        this.setComboBoxProperties(this.getPredicateComboBox(), "Predicate:");
        this.comboHBox.getChildren().addAll(this.getGeneralComboBox(), this.getPropositionalComboBox(), this.getPredicateComboBox());
    }

    public Pane getParentPane() {
        return this.comboHBox;
    }

    public ComboBox getGeneralComboBox() {
        return this.generalComboBox;
    }

    private void setGeneralComboBox(ComboBox _generalComboBox) {
        this.generalComboBox = _generalComboBox;
    }

    public ComboBox getPropositionalComboBox() {
        return this.propositionalComboBox;
    }

    private void setPropositionalComboBox(ComboBox _propositionalComboBox) {
        this.propositionalComboBox = _propositionalComboBox;
    }

    public ComboBox getPredicateComboBox() {
        return this.predicateComboBox;
    }

    private void setPredicateComboBox(ComboBox _predicateComboBox) {
        this.predicateComboBox = _predicateComboBox;
    }
}
