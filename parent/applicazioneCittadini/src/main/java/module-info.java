module com.example.applicazionecittadini {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires com.example.common;
    requires javafx.graphics;


    opens com.example.applicazionecittadini to javafx.fxml;
    exports com.example.applicazionecittadini;
    exports com.example.applicazionecittadini.GUI;
    opens com.example.applicazionecittadini.GUI to javafx.fxml;
}