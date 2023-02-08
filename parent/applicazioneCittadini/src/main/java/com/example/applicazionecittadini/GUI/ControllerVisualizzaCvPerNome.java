package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.common.CentroVaccinale;
import com.example.common.Indirizzo;
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
        CentroVaccinale cv = new CentroVaccinale();
        try {
            TableView<CentroVaccinale> tableView = new TableView<>();
            TableColumn<CentroVaccinale, String> colonnaNomeCV = new TableColumn<>("Nome");
            colonnaNomeCV.setCellValueFactory(new PropertyValueFactory<>(cv.getNome()));
            TableColumn<CentroVaccinale, String> colonnaIndirizzoCV = new TableColumn<>("Indirizzo centro vaccinale");
            colonnaIndirizzoCV.setCellValueFactory(new PropertyValueFactory<>(cv.getIndirizzo().toString()));
            TableColumn<CentroVaccinale, String> colonnaTipologia = new TableColumn<>("Tipologia centro vaccinale");
            colonnaTipologia.setCellValueFactory(new PropertyValueFactory<>(cv.getTipologia().toString()));

            tableView.getColumns().addAll(colonnaNomeCV, colonnaIndirizzoCV, colonnaTipologia);

            ObservableList<CentroVaccinale> listaCv = FXCollections.observableArrayList();
            ArrayList<CentroVaccinale> listaRisultato = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome(tFcercaCV.getText());
            for(CentroVaccinale c: listaRisultato) {
                listaCv.add(c);
            }
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setItems(listaCv);

            //TODO metodo per visualizzare nella list view i cv
            //TODO CV da cliccare
        }catch (Exception e){}
    }


    /*tableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
        lblValue.setText(values.get(newValue.intValue()).toString());
    });*/
}
