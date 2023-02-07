package com.example.applicazionecv.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.applicazionecv.GUI.UniversalMethods;

import java.io.IOException;

/**
 * Classe del package GUI contenente metodi per la gestione dell'interfaccia grafica di Homepage
 * @see UniversalMethods
 */
public class ControllerHomepage {
    @FXML
    private Button btNuovoCv;
    @FXML
    private Button btCvRegistrato;

    /**
     * Metodo della classe ControllerHomepage che permette di aprire l'interfaccia grafica per la registrazione di un nuovo centro vaccinale
     * @param event ActionEvent: evento generico
     * @throws IOException
     * @see UniversalMethods
     */
    public void sonoNuovoCv(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btNuovoCv);
        UniversalMethods.vediFinestra("NuovoCV.fxml", "TATUM VACCINI - Registazione centro vaccinale");
    }
    /**
     * Metodo della classe ControllerHomepage che permette di aprire l'interfaccia grafica per l'accesso al sistema
     * di un centro vaccinale già registrato
     * @param event ActionEvent: evento generico
     * @throws IOException
     * @see UniversalMethods
     */
    public void sonoCvRegistrato(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCvRegistrato);
        UniversalMethods.vediFinestra("CVRegistrato.fxml", "TATUM VACCINI - Registrazione vaccinato");
    }

}