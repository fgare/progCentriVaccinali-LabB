package com.example.applicazionecittadini.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 *La classe ControllerHomepage gestisce la finestra della homepage dell'applicazione.
 */
public class ControllerHomepage {
    @FXML
    private Button btCercaCV;

    /**
     *Questo metodo gestisce l'evento di pressione del pulsante per cercare un centro vaccinale.
     *Chiude la finestra corrente e apre la finestra per la scelta dei criteri di ricerca.
     *@param event evento di pressione del pulsante
     *@throws IOException in caso di errore nell'apertura della finestra
     */
    public void cercaCv(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCV);
        UniversalMethods.vediFinestra("SceltaRicerca.fxml", "TATUM VACCINI - Scelta ricerca centro vaccinale");
    }

    /**
     * Questo metodo gestisce la logica per l'accesso dei cittadini all'applicazione.
     * @param event evento generato dal click sul pulsante "btCercaCV".
     * @throws IOException in caso di errore nell'apertura della finestra "AccessoCittadino.fxml".
     */
    public void loginCittadini(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCV);
        UniversalMethods.vediFinestra("AccessoCittadino.fxml", "TATUM VACCINI - Richiesta accesso cittadino ");
    }
}