package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.common.CentroVaccinale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerVisualizzaCvPerNome {
    @FXML
    private Button btCercaCV;
    @FXML
    private TableView<CentroVaccinale> tvVisualizzaCV;
    @FXML
    private Button btIndietro;
    @FXML
    private TextField tFcercaCV;


    public void tornaSceltaRicerca(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("SceltaRicerca.fxml", "TATUM VACCINI - Scelta ricerca centro vaccinale");
    }

    public void cercaCVperNome(ActionEvent event) {
        CentroVaccinale cv = new CentroVaccinale("centro1");
        try {
            TableView<CentroVaccinale> tableView = new TableView<>();
            TableColumn colonnaNomeCV = new TableColumn<>("Nome");
            colonnaNomeCV.setCellValueFactory(new PropertyValueFactory<>(cv.getNome()));
            TableColumn colonnaIndirizzoCV = new TableColumn<>("Indirizzo centro vaccinale");
            colonnaIndirizzoCV.setCellValueFactory(new PropertyValueFactory<>(cv.getIndirizzo().toString()));
            TableColumn colonnaTipologia = new TableColumn<>("Tipologia centro vaccinale");
            colonnaTipologia.setCellValueFactory(new PropertyValueFactory<>(cv.getIndirizzo().toString()));

            tableView.getColumns().addAll(colonnaNomeCV, colonnaIndirizzoCV, colonnaTipologia);

            /*ObservableList<CentroVaccinale> listaCv = FXCollections.observableArrayList();
            listaCv.add(new CentroVaccinale(tFcercaCV.toString(), new Indirizzo("Via", "pippo18", (short) 1, "comune", "AO", "66666"), CentroVaccinale.Tipologia.HUB));
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setItems(listaCv);*/

            ArrayList listaRisultato = ClientCittadino.getInstance().ricercaCVperNome(); //TODO inserire stringa di ricerca


            //TODO metodo per visualizzare nella list view i cv
            //TODO CV da cliccare
        }catch (Exception e){}
    }


    /*tableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
        lblValue.setText(values.get(newValue.intValue()).toString());
    });*/
}
