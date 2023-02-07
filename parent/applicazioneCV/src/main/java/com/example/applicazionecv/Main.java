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

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            ClientMedico.getInstance().start();
        } catch(RemoteException e) {
            e.printStackTrace();
            System.out.println("Connessione al server fallita");
        }
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI PER CENTRI VACCINALI - Homepage");
    }

    public static void main(String[] args) {
        launch();
    }
}