module com.example.applicazionecv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires com.example.common;


    opens com.example.applicazionecv to javafx.fxml;
    exports com.example.applicazionecv;
    exports com.example.applicazionecv.Client;
    opens com.example.applicazionecv.Client to javafx.fxml;
    exports com.example.applicazionecv.GUI;
    opens com.example.applicazionecv.GUI to javafx.fxml;
}