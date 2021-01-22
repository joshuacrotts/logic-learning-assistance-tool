module org.LLAT {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.LLAT to javafx.fxml;
    exports org.LLAT;
}