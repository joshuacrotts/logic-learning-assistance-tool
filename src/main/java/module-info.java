module org.LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.google.gson;

    opens com.llat.models.settings; //this is a fucking bitch!
    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}