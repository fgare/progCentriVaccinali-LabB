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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControllerVisualizzaCvPerNome {
    @FXML
    private ChoiceBox cbSceltaCV;
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
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText((String) cbSceltaCV.getSelectionModel().getSelectedItem());
        a.show();

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

    public void cercaCV(MouseEvent event) throws RemoteException {
        ArrayList<CentroVaccinale> tuttiCv = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome(tFcercaCV.getText());
        //ObservableList<CentroVaccinale> options = FXCollections.observableArrayList(tuttiCv);
        ObservableList<String> opts = FXCollections.observableArrayList();
        for(CentroVaccinale c: tuttiCv) {
            opts.add(c.getNome());
        }
        cbSceltaCV.setItems(opts);
    }


    /*tableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
        lblValue.setText(values.get(newValue.intValue()).toString());
    });*/
}
