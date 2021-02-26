module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires org.antlr.antlr4.runtime;
    requires com.google.gson;
    requires org.opentest4j;
    requires org.junit.jupiter.api;

    opens com.llat.models.settings; //this is a fucking bitch!
    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}