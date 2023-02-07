package com.example.applicazionecittadini.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerAccessoCittadino {

    @FXML
    private Button btRegistraCittadino;
    @FXML
    private Button btIndietro;

    public void registraCittadino(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btRegistraCittadino);
        UniversalMethods.vediFinestra("RegistraCittadino.fxml", "TATUM VACCINI - Registrazione cittadino");
    }

    public void accessoCittadino(ActionEvent event) {

    }

    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

}
