package com.example.applicazionecittadini.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerSceltaRicerca {
    @FXML
    private Button btCercaCVperComuneTipologia;
    @FXML
    private Button btCercaCVperNome;
    @FXML
    private Button btIndietro;

    public void cercaCvPerNome(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCVperNome);
        UniversalMethods.vediFinestra("VisualizzaCVperNome.fxml", "TATUM VACCINI - Ricerca centro vaccinale per nome");
    }

    public void cercaCvPerComuneTipologia(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btCercaCVperComuneTipologia);
        UniversalMethods.vediFinestra("VisualizzaCVperComuneTipologia.fxml", "TATUM VACCINI - Ricerca centro vaccinale per comune e tipologia");
    }

    public void tornaHompage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Ricerca centro vaccinale");
    }
}
