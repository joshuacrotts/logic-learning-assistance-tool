module org.LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.antlr.antlr4.runtime;

    opens com.llat.models.settings; //this is a fucking bitch!
    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}