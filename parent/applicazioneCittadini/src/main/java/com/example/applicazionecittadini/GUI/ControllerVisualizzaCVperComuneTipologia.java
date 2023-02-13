package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.common.CentroVaccinale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControllerVisualizzaCVperComuneTipologia {
    @FXML
    private ChoiceBox cbCercaCV;
    @FXML
    private TextField tfComune;
    @FXML
    private ComboBox cb_tipo;
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

    }

    public void cercaCV(MouseEvent mouseEvent) {
        String comune=tfComune.getText();
        String tipo=cb_tipo.getValue().toString();

        ArrayList<CentroVaccinale> cvCercati;
        try {
             cvCercati = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCvNomeTipologia(comune,tipo);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> opts = FXCollections.observableArrayList();
        if(cvCercati != null) {
            for (CentroVaccinale c : cvCercati) {
                opts.add(c.getNome());
            }
            cbCercaCV.setItems(opts);
        }
    }
}
