package com.llat.views;

import com.llat.controller.Controller;
import com.llat.views.InputButtonsView;
import com.llat.views.LLATErrorView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftView {
    private Controller controller;
    private VBox parentPane = new VBox();
    private InputButtonsView inputButtonsView;
    private LLATErrorView llatErrorView;
    public LeftView (Controller _controller) {
        this.controller = _controller;
        this.inputButtonsView = new InputButtonsView(this.controller);
        this.llatErrorView = new LLATErrorView(this.controller);
        // Setting VBox parentPane's properties.
        this.controller.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.parentPane.setMinWidth(newVal.doubleValue() * .20);
            this.parentPane.setMaxWidth(newVal.doubleValue() * .20);
        });
        this.parentPane.setAlignment(Pos.TOP_CENTER);
        // Setting InputButtonsView inputButtonsView's properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.inputButtonsView.getParentPane().setMinWidth(newVal.doubleValue());
            this.inputButtonsView.getParentPane().setMaxWidth(newVal.doubleValue());
        });
        VBox.setVgrow(this.inputButtonsView.getParentPane(), Priority.ALWAYS);
        // Setting LLATErrorView llatErrorView's properties.
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.llatErrorView.getParentPane().setMinWidth(newVal.doubleValue());
            this.llatErrorView.getParentPane().setMaxWidth(newVal.doubleValue());
        });
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {});
        VBox.setVgrow(this.llatErrorView.getParentPane(), Priority.ALWAYS);
        // Adding children nodes to their parents.
        parentPane.getChildren().addAll(this.inputButtonsView.getParentPane(), this.llatErrorView.getParentPane());

    }

    public VBox getParentPane() {
        return parentPane;
    }

    public InputButtonsView getInputButtonsView() {
        return inputButtonsView;
    }

    public LLATErrorView getLlatErrorView() {
        return llatErrorView;
    }

}
