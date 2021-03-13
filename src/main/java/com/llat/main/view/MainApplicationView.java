package sample.view;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.controller.Controller;

public class MainApplicationView {
    private Controller controller;
    private BorderPane parentPane = new BorderPane();
    private SymbolInputView symbolInputview;
    public MainApplicationView (Controller _controller) {
        this.controller = _controller;
        this.symbolInputview = new SymbolInputView(this.controller);
        SymbolInputView test1 = new SymbolInputView(this.controller);
        this.parentPane.setLeft(this.symbolInputview.getParentPane());
        this.parentPane.setRight(new AnchorPane());
        this.parentPane.setBottom(new AnchorPane());
        this.parentPane.setTop(test1.getParentPane());
        this.parentPane.setMinHeight(720);
        this.parentPane.setMinWidth(1280);
    }
    public Pane getParentPane() {
        return this.parentPane;
    }
}
