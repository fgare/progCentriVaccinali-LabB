module com.example.common {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens com.example.common to javafx.fxml;
    exports com.example.common;

}