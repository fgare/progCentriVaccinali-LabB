package com.example.applicazionecittadini.GUI;

import com.example.common.EventoAvverso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;

/**
 *Classe per il controller dell'inserimento degli eventi avversi
 */
public class ControllerInserimentoEventiAvversi {
    @FXML
    private ComboBox cbEventoAvverso;
    @FXML
    private ComboBox cbSeveritaEventoAvverso;
    @FXML
    private Button btIndietro;
    @FXML
    private Button btInserisciEventoAvverso;

    /**
     *Questo metodo gestisce l'inserimento di un evento avverso da parte di un cittadino.
     * @param event l'evento che scatena l'invocazione del metodo
     * @throws IOException in caso di errore nella lettura del file FXML corrispondente alla finestra da aprire
     */
    public void inserisciEventoAvversoCittadino(ActionEvent event)throws IOException {
        String ea = cbEventoAvverso.getValue().toString();
        String sea = cbSeveritaEventoAvverso.getValue().toString();

        UniversalMethods.handleCloseButtonAction(event, btInserisciEventoAvverso);
        UniversalMethods.vediFinestra("InserimentoEventiAvversi.fxml", "TATUM VACCINI - Inserimento eventi avversi");

    }

    /**
     *Metodo che gestisce il click sul bottone "btIndietro" e permette di tornare alla schermata di "Homepage".
     *@param event l'evento generato dal click sul bottone
     *@throws IOException in caso di errore nell'apertura della finestra "Homepage.fxml"
     */
    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Ricerca centro vaccinale");
    }
}
