module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires org.antlr.antlr4.runtime;

    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}