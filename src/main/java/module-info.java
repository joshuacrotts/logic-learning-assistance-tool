module org.LLAT {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}