package com.example.applicazionecittadini.GUI;

import com.example.common.CentroVaccinale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ControllerVisualizzaCVperComuneTipologia {
    @FXML
    private Button btCercaCV;
    @FXML
    private TableView<CentroVaccinale> tvVisualizzaCV;
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
