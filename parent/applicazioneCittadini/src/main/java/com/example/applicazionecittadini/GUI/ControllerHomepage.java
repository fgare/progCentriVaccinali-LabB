package com.example.applicazionecittadini.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ControllerHomepage {
    @FXML
    private Button btCercaCV;

    public void cercaCv(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCV);
        UniversalMethods.vediFinestra("SceltaRicerca.fxml", "TATUM VACCINI - Scelta ricerca centro vaccinale");
    }

    public void loginCittadini(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCV);
        UniversalMethods.vediFinestra("AccessoCittadino.fxml", "TATUM VACCINI - Richiesta accesso cittadino ");
    }
}