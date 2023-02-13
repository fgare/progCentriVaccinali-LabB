package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.common.CentroVaccinale;
import com.example.common.EventoAvverso;
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
import java.util.HashMap;

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
        a.setHeaderText("Informazione centro vaccinale");
        String nomeCentroVaccinale = (String) cbSceltaCV.getSelectionModel().getSelectedItem();
        String tabella = nomeCentroVaccinale + "\nEvento\tCount\tSeverita' media\n";

        HashMap<String,float[]> hm = null;
        try {
            hm = ClientCittadino.getInstance().getInfoCentroVaccinale(nomeCentroVaccinale);
        } catch (RemoteException e) {
            System.out.println("RemoteException in cercaCVperNome");
        }
        if(hm == null) System.out.println("Hashmap > NULL");
        else System.out.println("Hashmap > " + hm.toString());
        if(hm == null) {
            for(int i=0; i < EventoAvverso.QualeEvento.values().length; i++) {
                tabella += EventoAvverso.QualeEvento.values()[i].name() + "\t0\t0\n";
            }
        } else {
            for (int i = 0; i < EventoAvverso.QualeEvento.values().length; i++) {
                String evento = EventoAvverso.QualeEvento.values()[i].name();
                float[] valori = hm.get(evento);
                if (valori == null) {
                    tabella += EventoAvverso.QualeEvento.values()[i].name() + "\t0\t0\n";
                } else
                    tabella += EventoAvverso.QualeEvento.values()[i].name() + "\t" + valori[0] + "\t" + valori[1] + "\n";
            }
        }

        a.setContentText(tabella);

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
}
