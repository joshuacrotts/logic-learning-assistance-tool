module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.antlr.antlr4.runtime;
    requires com.google.gson;

    opens com.llat.models.settings; //this is a fucking bitch!
    opens com.llat.models.uidescription;
    opens com.llat.models.gson;
    opens com.llat.tools;
    opens com.llat.controller;

    //opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}