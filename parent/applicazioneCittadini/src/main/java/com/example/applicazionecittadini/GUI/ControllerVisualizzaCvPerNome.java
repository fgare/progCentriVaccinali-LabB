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
import java.rmi.RemoteException;
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
        //TODO DA RIFARE CON LA BOX
      /*  System.out.println("Bottone premuto cercaCV");
        CentroVaccinale cv = new CentroVaccinale();

        //se il campo viene lasciato vuoto viene assegnata la stringa vuota
        if(tFcercaCV.getText().isEmpty()) tFcercaCV.setText("");

        try {
            ObservableList<CentroVaccinale> listaCv = FXCollections.observableArrayList();
            ArrayList<CentroVaccinale> listaRisultato = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome(tFcercaCV.getText());
            if(listaRisultato == null) System.out.println("listaRisultato = NULL");
            System.out.println("testo ricercato : " + tFcercaCV.getText());
            for(CentroVaccinale c: listaRisultato) {
                listaCv.add(c);
            }

            //crea tabella
            TableView<CentroVaccinale> tableView = new TableView<>();

            tableView.setItems(listaCv);

            //crea colonne
            TableColumn<CentroVaccinale, String> colonnaNomeCV = new TableColumn<>("Nome");
            TableColumn<CentroVaccinale, String> colonnaIndirizzoCV = new TableColumn<>("Indirizzo");
            TableColumn<CentroVaccinale, String> colonnaTipologia = new TableColumn<>("Tipologia");

            colonnaNomeCV.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colonnaIndirizzoCV.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
            colonnaTipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

            tableView.getColumns().addAll(colonnaNomeCV, colonnaIndirizzoCV, colonnaTipologia);

            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            //TODO metodo per visualizzare nella list view i cv
            //TODO CV da cliccare
        }catch (RemoteException e){
            System.out.println("RemoteException cercaCVperNome");
        }*/
        ArrayList<CentroVaccinale> listaRisultato = new ArrayList<>();
        try {
            listaRisultato = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome(tFcercaCV.getText());
        } catch (RemoteException e) {
            System.out.println("RemoteException cercaCVperNome");
        }
        if (listaRisultato == null) {
            System.out.println("Lista vuota");
            return;
        }
        for(CentroVaccinale c: listaRisultato) {
            System.out.println(c.toString());
        }
    }


    /*tableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
        lblValue.setText(values.get(newValue.intValue()).toString());
    });*/
}
