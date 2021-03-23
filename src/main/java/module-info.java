module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.antlr.antlr4.runtime;
    requires com.google.gson;
    requires google.cloud.translate;
    requires google.cloud.core;

    opens com.llat.models.settings;
    opens com.llat.models.uidescription;
    opens com.llat.models.gson;

    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}