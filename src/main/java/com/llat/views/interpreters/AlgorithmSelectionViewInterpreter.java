package com.llat.views.interpreters;

import com.llat.controller.Controller;
import com.llat.models.AlgorithmType;
import com.llat.models.events.SetAlgorithmInputEvent;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.AlgorithmSelectionView;
import com.llat.views.events.ApplyAlgorithmButtonEvent;
import com.llat.views.events.ApplyAlgorithmEvent;
import com.llat.views.events.SolveButtonEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class AlgorithmSelectionViewInterpreter implements Listener {
    Controller controller;
    AlgorithmSelectionView algorithmSelectionView;
    String currentAlgorithm = null;

    public AlgorithmSelectionViewInterpreter(Controller _controller, AlgorithmSelectionView _algorithmSelectionView) {
        this.controller = _controller;
        this.algorithmSelectionView = _algorithmSelectionView;

        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolveButtonEvent) {
            this.algorithmSelectionView.clearComboBoxes();
            this.algorithmSelectionView.createComboBoxes();
        } else if (_event instanceof SetAlgorithmInputEvent) {
            this.currentAlgorithm = null;
            ((SetAlgorithmInputEvent) _event).getAlgorithmOptions().forEach((_algorithmList) -> {
                List<Object> algorithmList = FXCollections.observableArrayList(_algorithmList);
                ComboBox tempBox;
                if (algorithmList.get(0).equals(AlgorithmType.GENERAL)) {
                    tempBox = this.algorithmSelectionView.getGeneralComboBox();
                } else if (algorithmList.get(0).equals(AlgorithmType.PREDICATE)) {
                    tempBox = this.algorithmSelectionView.getPredicateComboBox();
                } else if (algorithmList.get(0).equals(AlgorithmType.PROPOSITIONAL)) {
                    tempBox = this.algorithmSelectionView.getPropositionalComboBox();
                } else {
                    tempBox = new ComboBox();
                }
                algorithmList.remove(0);
                tempBox.getItems().addAll(algorithmList);
            });
            this.algorithmSelectionView.getGeneralComboBox().setOnAction(event -> {
                this.currentAlgorithm = ((ComboBox) (event.getSource())).getValue().toString();
            });
            this.algorithmSelectionView.getPredicateComboBox().setOnAction(event -> {
                this.currentAlgorithm = ((ComboBox) (event.getSource())).getValue().toString();
            });
            this.algorithmSelectionView.getPropositionalComboBox().setOnAction(event -> {
                this.currentAlgorithm = ((ComboBox) (event.getSource())).getValue().toString();
            });
        } else if (_event instanceof ApplyAlgorithmButtonEvent) {
            if (this.currentAlgorithm != null) {
                EventBus.throwEvent(new ApplyAlgorithmEvent(this.currentAlgorithm));
            }
        }
    }

}
