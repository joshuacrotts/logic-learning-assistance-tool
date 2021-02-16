module org.LLAT {
    opens com.llat.models.settings; //this is a fucking bitch!
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.google.gson;

    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}