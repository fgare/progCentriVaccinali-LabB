package com.example.applicazionecittadini;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.applicazionecittadini.GUI.UniversalMethods;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.RemoteException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            ClientCittadino.getInstance().start();
        } catch(RemoteException e) {
            e.printStackTrace();
            System.out.println("Connessione al server fallita");
        }
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

    public static void main(String[] args) {
        launch();
    }
}