module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.antlr.antlr4.runtime;
    requires com.google.gson;
    requires bcrypt;

    opens com.llat.models.localstorage.settings;
    opens com.llat.models.localstorage.uidescription;
    opens com.llat.models.symbols;
    opens com.llat.models.gson;
    opens com.llat.tools;
    opens com.llat.controller;

    //opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}