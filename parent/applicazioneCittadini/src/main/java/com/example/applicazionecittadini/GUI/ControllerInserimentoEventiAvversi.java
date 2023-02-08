package com.example.applicazionecittadini.GUI;

import com.example.common.EventoAvverso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerInserimentoEventiAvversi {
    @FXML
    private Button btIndietro;
    @FXML
    private Button btInserisciEventoAvverso;

    public void inserisciEventoAvversoCittadino(ActionEvent event)throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btInserisciEventoAvverso);
        UniversalMethods.vediFinestra("InserimentoEventiAvversi.fxml", "TATUM VACCINI - Inserimento eventi avversi");

        //new EventoAvverso(); //TODO
    }

    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Ricerca centro vaccinale");
    }
}
