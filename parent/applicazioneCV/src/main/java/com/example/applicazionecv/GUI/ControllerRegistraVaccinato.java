package com.example.applicazionecv.GUI;

import com.example.applicazionecv.Client.ClientMedico;

import com.example.common.Vaccinazione;
import com.example.common.CentroVaccinale;
import com.example.common.EventoAvverso;
import com.example.common.Cittadino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateTimeStringConverter;
import com.example.applicazionecv.GUI.UniversalMethods;

import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe del package GUI contenente metodi per la registrazione di una vacciazione
 *
 * @see CentroVaccinale
 * @see Vaccinazione
 * @see Cittadino
 * @see UniversalMethods
 */
public class ControllerRegistraVaccinato {
    @FXML
    private Label lbErr;
    @FXML
    private Button btIndietro;
    @FXML
    private Button btRegistraVaccinato;
    @FXML
    private TextField tFcfV;
    @FXML
    private ComboBox cbTipo;

    /**
     * Metodo della classe ControllerRegistraVaccinato che permette di salvare una vaccinazione ad un cittadino
     *
     * @param event ActionEvent: evento generico
     * @throws IOException
     * @see Vaccinazione
     * @see UniversalMethods
     */
    public void registraVaccinato(ActionEvent event) throws IOException {
        String nomeCittadino, cognomeCittadino, cfCittadino, tipoVaccino;
        Vaccinazione.Vaccino v = null;
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        boolean err = false;

        try {
            cfCittadino = tFcfV.getText();
            tipoVaccino = cbTipo.getValue().toString();

            if (!cfCorretto(cfCittadino.toUpperCase())) {
                err = true;
                tFcfV.clear();
                System.out.println("CF errato");
                lbErr.setText("CAMPI ERRATI O CAMPI NON SELZIONATI!");
            }

            if (!err) {
                Vaccinazione recvacc = new Vaccinazione(Vaccinazione.Vaccino.parse(tipoVaccino), LocalDate.now(), cfCittadino);
                boolean esito = ClientMedico.getInstance().aggiungiDose(ClientMedico.getInstance().getCentroInUso(), recvacc);

                UniversalMethods.handleCloseButtonAction(event, btRegistraVaccinato);
                UniversalMethods.vediFinestra("RegistraVaccinato.fxml", "TATUM VACCINI - Registrazione vaccinato");
            }

        } catch (Exception e) {
            System.out.println("ERRORE");
        }

    }

    /**
     * Metodo della classe ControllerRegistraVaccinato che permette di aprire la finestra CVRegistrato
     *
     * @param event ActionEvent: evento generico
     * @throws IOException
     * @see UniversalMethods
     */
    public void tornaCvRegistrato(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("CVRegistrato.fxml", "TATUM VACCINI - Registrazione vaccinato");
    }

    /**
     * Metodo che permette di controllare la validità del nome e del cognome
     *
     * @param str String: stringa per verfica della correttezza
     * @return boolean: true se valido, false altrimenti
     */
    private boolean nomeCorretto(String str) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Metodo che permette di controllare la validità del codice fiscale di un Vaccinato
     *
     * @param cf String: cf cittadino vaccinato
     * @return boolean: true se valido, false altrimenti
     */
    private boolean cfCorretto(String cf) {
        Pattern p = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$");
        Matcher m = p.matcher(cf);
        return m.matches();
    }
}
