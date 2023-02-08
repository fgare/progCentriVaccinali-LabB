
package com.example.applicazionecv.GUI;

import com.example.applicazionecv.Client.ClientMedico;
import com.example.common.CentroVaccinale;
import com.example.common.Indirizzo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.*;

/**
 * Classe del package GUI contenente metodi per la gestione dell'interfaccia grafica di registrazione nuovo cv
 *
 * @see CentroVaccinale
 * @see UniversalMethods
 */
public class ControllerNuovoCV {

    @FXML
    private TextField tFnomeCentro;
    @FXML
    private ComboBox cb_qual;
    @FXML
    private TextField tFnomeVia;
    @FXML
    private TextField tFnC;
    @FXML
    private TextField tFcomuneCentro;
    @FXML
    private ComboBox cb_prov;
    @FXML
    private TextField tFCAP;
    @FXML
    private ComboBox cb_tipo;
    @FXML
    private Button btRegistraCV;
    @FXML
    private Button btIndietro;
    @FXML
    private Label lbErr;

    /**
     * Metodo della classe ControllerNuovoCV che permette di registrare un nuovo centro vaccinale
     *
     * @param event ActionEvent: evento generico
     * @see UniversalMethods
     * @see CentroVaccinale
     * @see Indirizzo
     */
    public void registaCentroVaccinale(ActionEvent event) {

        String nomeCentro, qual, nomevia, nc, comunecentro, prov, cap, tipologiaCentro;
        Indirizzo indirizzoCentro;
        boolean err = false;
        try {
            nomeCentro = tFnomeCentro.getText();
            qual = cb_qual.getValue().toString();
            nomevia = tFnomeVia.getText();
            nc = tFnC.getText();
            prov = cb_prov.getValue().toString().charAt(0) + "" + cb_prov.getValue().toString().charAt(1);
            comunecentro = tFcomuneCentro.getText();
            cap = tFCAP.getText();
            tipologiaCentro = cb_tipo.getValue().toString();

            if (nomeCentro.isEmpty()) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("nome errato");
                tFnomeCentro.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else tFnomeCentro.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (qual.equals(cb_qual.getPromptText())) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("qualificatore non selezionato");
                cb_qual.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else cb_qual.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (nomevia.isEmpty()) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("nome via errata");
                tFnomeVia.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else tFnomeVia.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (!ncCorretto(nc)) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("nc vuoto o errato");
                tFnC.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else tFnC.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (!comuneCorretto(comunecentro)) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("comune errato");
                tFcomuneCentro.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else tFcomuneCentro.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (prov.equals(cb_prov.getPromptText())) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("provincia non selezionata");
                cb_prov.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else cb_prov.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (!capCorretto(cap)) {
                err = true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("cap errato");
                tFCAP.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else tFCAP.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if(tipologiaCentro.equals(cb_tipo.getPromptText())){
                err=true;
                lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
                System.out.println("Tipologia centro non selezionata");
                cb_tipo.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            } else cb_tipo.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (!err) {
                indirizzoCentro = new Indirizzo(qual, nomevia, Short.parseShort(nc), comunecentro, prov, cap);
                CentroVaccinale cv = new CentroVaccinale(nomeCentro, indirizzoCentro, CentroVaccinale.Tipologia.parse(tipologiaCentro));
                System.out.println(cv);

                boolean esito = ClientMedico.getInstance().registraCentroVaccinale(cv);
                System.out.println("esito: " + esito);
                //if(!esito)
                // TODO: mostra una scritta di errore ("com.example.applicazioneServer.Server disconnesso")
                //lbErr.setText("com.example.applicazioneServer.Server disconnesso");

                UniversalMethods.handleCloseButtonAction(event, btRegistraCV);
                UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
            }
        } catch (Exception e) {
            lbErr.setText("CAMPI ERRATI O NON SELZIONATI!");
            tFnomeCentro.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            cb_qual.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            tFnomeVia.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            tFnC.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            tFcomuneCentro.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            cb_prov.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            tFCAP.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            cb_tipo.setStyle("-fx-text-fill: red; -fx-border-color: red;");
        }
    }

    /**
     * Metodo della classe ControllerNuovoCV che permette di tornare alla finestra di Homepage
     *
     * @param event ActionEvent: evento generico
     * @throws IOException
     */
    public void tornaHompage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

    /**
     * Metodo che permette di controllare la validità del CAP
     *
     * @param cap String: CAP centro vaccinale
     * @return boolean: true se valido, false altrimenti
     */
    private boolean capCorretto(String cap) {
        Pattern p = Pattern.compile("\\d{5}");
        Matcher m = p.matcher(cap);
        return m.matches();
    }

    /**
     * Metodo che permette di controllare la validità del comune
     *
     * @param comune String: comune centro vaccinale
     * @return boolean: true se valido, false altrimenti
     */
    private boolean comuneCorretto(String comune) {
        Pattern p = Pattern.compile("[a-zA-Z]{2,}");
        Matcher m = p.matcher(comune);
        return m.matches();
    }

    /**
     *Questo metodo verifica che una stringa rappresenti un numero intero positivo.
     *@param nc stringa da verificare
     *@return true se la stringa rappresenta un numero intero positivo, false altrimenti
     */
    private boolean ncCorretto(String nc) {
        Pattern p = Pattern.compile("[0-9]{1,}");
        Matcher m = p.matcher(nc);
        return m.matches();
    }

    /**
     *Verifica se una stringa contiene almeno un carattere numerico.
     *@param strIn stringa da verificare
     *@return true se la stringa contiene almeno un carattere numerico, false altrimenti
     */
    private static boolean contieneNumeri(String strIn) {
        for (int i = 0; i < strIn.length(); i++) {
            char c = strIn.charAt(i);
            if (c >= '0' && c <= '9')
                return true;
        }
        return false;
    }


    /**
     *Verifica se una stringa contiene solo caratteri numerici.
     *@param strIn stringa da verificare
     *@return true se la stringa contiene solo caratteri numerici, false altrimenti
     */
    private static boolean contienesoloNumeri(String strIn) {
        for (int i = 0; i < strIn.length(); i++) {
            char c = strIn.charAt(i);
            if (c >= '0' & c <= '9')
                return false;
        }
        return true;
    }

}
