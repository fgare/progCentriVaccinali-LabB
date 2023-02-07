package com.example.applicazionecv.GUI;

import com.example.applicazionecv.Client.ClientMedico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.applicazionecv.GUI.UniversalMethods;

import java.io.IOException;

/**
 * Classe del package GUI contenente metodi per la gestione dell'interfaccia grafica di
 * accesso al sistema per un centro vaccinale registrato
 * @see UniversalMethods
 */
public class ControllerCVRegistrato {

    @FXML
    private Button btIndietro;
    @FXML
    private TextField textFieldIdCv;
    @FXML
    private Label labelIdCv;
    @FXML
    private Label labelMessage;
    @FXML
    private Button btAccedi;

    /**
     * Metodo della classe ControllerCVRegistrato che permette di tornare alla finestra di Homepage
     * @param event ActionEvent: evento generico
     * @throws IOException
     */
    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

    /**
     * Metodo della classe ControllerCVRegistrato che permette di verificare se il centro vaccinale è effetivamente già registrato
     * @param event
     * @throws IOException
     * @see UniversalMethods
     *
     */
    public void accessoCentriVaccinali(ActionEvent event) throws IOException {
        /* TODO:
            - l'utente inserisce il nome del centro vaccinale (o parte del nome).
            - si riceve dal server una lista con tutti i centri vaccinali che coincidono con il testo inserito.
            - l'utente seleziona uno tra i centri visualizzati
            - si salva in ClientMedico il riferimento al centro vaccinale selezionato
         */
        Alert alert=new Alert(Alert.AlertType.ERROR);
        if (textFieldIdCv.getText().length() == 0) {
            labelMessage.setText("QUALCOSA E' ANDATO STORTO");
            tornaCvRegistrato(event);
        } else {
            boolean accessoConfermato = ClientMedico.getInstance().accediACv(textFieldIdCv.getText());

            if(accessoConfermato) {
                UniversalMethods.handleCloseButtonAction(event, btAccedi);
                UniversalMethods.vediFinestra("RegistraVaccinato.fxml", "TATUM VACCINI - Registrazione vaccinati");
            } else {
                //TODO: visualizza messaggio di errore ("Centro vaccinale non registrato")
                //alert.setContentText("OPERAZIONE NON ANDATA A BUON FINE");
                //alert.showAndWait();
            }
        }
    }

    /**
     * Metodo della classe ControllerCVRegistrato che permette di riaprire la finestra CVRegistrato
     * @param event ActionEvent: evento generico
     * @throws IOException
     */
    private void tornaCvRegistrato(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btAccedi);
        UniversalMethods.vediFinestra("CVRegistrato.fxml", "TATUM VACCINI - Registrazione vaccinato");
    }

}
