package com.example.applicazionecittadini.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ControllerVisualizzaCVperComuneTipologia {
    @FXML
    private Button btCercaCV;
    @FXML
    private ListView lvVisualizzaCV;
    @FXML
    private Button btIndietro;

    public void tornaSceltaRicerca(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("SceltaRicerca.fxml", "TATUM VACCINI - Scelta ricerca centro vaccinale");
    }

    public void cercaCVperComuneTipologia(ActionEvent event) {
        //TODO metodo per visualizzare nella list view i cv
    }
}
