package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerAccessoCittadino {

    @FXML
    private Button btRegistraCittadino;
    @FXML
    private Button btIndietro;
    @FXML
    private Button btAccessoCittadino;

    public void registraCittadino(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btRegistraCittadino);
        UniversalMethods.vediFinestra("RegistraCittadino.fxml", "TATUM VACCINI - Registrazione cittadino");
    }

    public void accessoCittadino(ActionEvent event) throws IOException{
        UniversalMethods.handleCloseButtonAction(event, btAccessoCittadino);
        UniversalMethods.vediFinestra("InserimentoEventiAvversi.fxml", "TATUM VACCINI - Inserimento eventi avversi");

        //boolean accessoConsentito = ClientCittadino.getInstance().login(); //TODO
    }

    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

}
