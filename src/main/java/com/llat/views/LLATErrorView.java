package com.llat.views;


import com.llat.controller.Controller;
import com.llat.views.interpreters.LLATErrorViewInterpreter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class LLATErrorView {

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
    private final ScrollPane logScrollPane = new ScrollPane();

    /**
     *
     */
    private final VBox logBox = new VBox();

    /**
     *
     */
    private final LLATErrorViewInterpreter llatErrorViewInterpreter;

    /**
     *
     */
    private final Label errorLabel;

    public LLATErrorView(Controller _controller) {
        this.controller = _controller;
        // Setting VBox parentPane's properties.
        this.parentPane.setId("errorMessageVBox");
        this.parentPane.setAlignment(Pos.CENTER);
        // Setting Label errorLabel properties.
        this.errorLabel = new Label(this.controller.getUiObject().getMainView().getMainViewLabels().getErrorAndWarningLabel());
        this.errorLabel.setId("errorLabel");
        // Setting ScrollPane logScrollPane's properties.
        this.logScrollPane.setId("errorScrollPane");
        this.logScrollPane.setFitToWidth(true);
        this.parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.logScrollPane.setMinHeight(newVal.doubleValue() * .70);
            this.logScrollPane.setMaxHeight(newVal.doubleValue() * .70);
        });
        this.parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.logScrollPane.setMinWidth(newVal.doubleValue() * .80);
            this.logScrollPane.setMaxWidth(newVal.doubleValue() * .80);
        });
        // Setting VBox logBox's properties.
        this.logBox.setAlignment(Pos.TOP_LEFT);
        // Adding children nodes to their parents.
        this.logScrollPane.setContent(this.logBox);
        this.parentPane.getChildren().addAll(this.errorLabel, this.logScrollPane);
        // Creating interpreter to handle events and actions.
        this.llatErrorViewInterpreter = new LLATErrorViewInterpreter(this.controller, this);
    }

    public LLATErrorViewInterpreter getLlatErrorViewInterpreter() {
        return this.llatErrorViewInterpreter;
    }

    public VBox getParentPane() {
        return this.parentPane;
    }

    public ScrollPane getLogScrollPane() {
        return this.logScrollPane;
    }

    public VBox getLogBox() {
        return this.logBox;
    }
}
