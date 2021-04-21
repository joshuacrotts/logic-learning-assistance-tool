package com.llat.views;

import com.llat.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftView {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final VBox parentPane = new VBox();

    /**
     *
     */
    private final InputButtonsView inputButtonsView;

    /**
     *
     */
    private final LLATErrorView llatErrorView;

    public LeftView(Controller _controller) {
        this.controller = _controller;
        this.inputButtonsView = new InputButtonsView(this.controller);
        this.llatErrorView = new LLATErrorView(this.controller);
        this.parentPane.setId("leftView");
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
        VBox.setVgrow(this.llatErrorView.getParentPane(), Priority.ALWAYS);
        // Adding children nodes to their parents.
//        this.parentPane.getChildren().addAll(this.inputButtonsView.getParentPane());
        this.parentPane.getChildren().addAll(this.inputButtonsView.getParentPane(), this.llatErrorView.getParentPane());

    }

    public VBox getParentPane() {
        return this.parentPane;
    }

    public InputButtonsView getInputButtonsView() {
        return this.inputButtonsView;
    }

    public LLATErrorView getLlatErrorView() {
        return this.llatErrorView;
    }

}
