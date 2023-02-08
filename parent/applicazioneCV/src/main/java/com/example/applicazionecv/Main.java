package com.example.applicazionecv;

import com.example.applicazionecv.Client.ClientMedico;
import com.example.applicazionecv.GUI.UniversalMethods;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Classe principale che estende la classe Application di JavaFX e serve come punto di ingresso per l'applicazione.
 * Contiene il metodo start, che viene chiamato quando viene avviata l'applicazione, e il metodo main,
 * che viene utilizzato per avviare l'applicazione.
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            ClientMedico.getInstance().start();
        } catch(RemoteException e) {
            System.out.println("Connessione al server fallita");
        }
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI PER CENTRI VACCINALI - Homepage");
    }

    public static void main(String[] args) {
        launch();
    }
}