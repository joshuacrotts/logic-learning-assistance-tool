module com.llat.LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.llat.LLAT to javafx.fxml;
    exports com.llat.LLAT;
}