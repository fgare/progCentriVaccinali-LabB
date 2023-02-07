package com.example.applicazionecittadini;

import com.example.applicazionecittadini.GUI.UniversalMethods;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

    public static void main(String[] args) {
        launch();
    }
}