package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import com.example.common.CentroVaccinale;
import com.example.common.Cittadino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Classe che gestisce la finestra di registrazione del cittadino.
 */
public class ControllerRegistraCittadino {

    @FXML
    private ChoiceBox cbSceltaCV;
    @FXML
    private Button btSelCVxCittadino;
    @FXML
    private Label lbErr;
    @FXML
    private PasswordField pfPswCittadini;
    @FXML
    private TextField tfEmailCittadino;
    @FXML
    private TextField tfUserCittadino;
    @FXML
    private TextField tFcfCittadino;
    @FXML
    private TextField tFcognomeCittadino;
    @FXML
    private TextField tFnomeCittadino;
    @FXML
    private Button btRegistraCittadino;
    @FXML
    private Button btIndietro;
    @FXML
    private Button btIndietro1;
    @FXML
    private Button btAvanti;
    @FXML
    private Button btSelCV;

    /**
     *Questo metodo gestisce l'evento di chiusura della finestra corrente".
     *@param event l'evento che ha causato l'esecuzione del metodo
     *@throws IOException eccezione lanciata in caso di errore nell'apertura della finestra "AccessoCittadino.fxml"
     */
    public void tornaAccessoCittadini(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("AccessoCittadino.fxml", "TATUM VACCINI - Richiesta accesso cittadino");
    }

    /**
     * Questo metodo getsisce la registrazione di un cittadino a sistema
     * @param s La stringa da verificare.
     * @return true se la stringa è un palindromo, false altrimenti.
     */
    public void registraCittadino(ActionEvent event) throws IOException {
        boolean err = false;
        String nomeCittadino, cognomeCittadino, cfCittadino, usernameCittadino, emailCittadino, passwordCittadino;
        Alert a = new Alert(Alert.AlertType.INFORMATION);

        try {
            nomeCittadino = tFnomeCittadino.getText();
            cognomeCittadino = tFcognomeCittadino.getText();
            cfCittadino = tFcfCittadino.getText();
            usernameCittadino = tfUserCittadino.getText();
            emailCittadino = tfEmailCittadino.getText();
            passwordCittadino = pfPswCittadini.getText();

            if (!nomeCorretto(nomeCittadino)) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                tFnomeCittadino.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("nome errato");
            } else tFnomeCittadino.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if (!nomeCorretto(cognomeCittadino)) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                tFcognomeCittadino.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("cognome errato");
            } else tFcognomeCittadino.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if (!cfCorretto(cfCittadino.toUpperCase())) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                tFcfCittadino.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("Codice fiscale errato");
            } else tFcfCittadino.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if (usernameCittadino.isEmpty()) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                tfUserCittadino.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("username vuoto");
            } else tfUserCittadino.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if (!emailCorretta(emailCittadino)) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                tfEmailCittadino.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("email errata");
            } else tfEmailCittadino.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if (passwordCittadino.length() < 8) {
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                pfPswCittadini.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("password errata");
            } else pfPswCittadini.setStyle("-fx-text-fill: green; -fx-border-color: green;");
            if(cbSceltaCV.getItems().isEmpty()){
                err = true;
                lbErr.setText("CAMPI ERRATI!");
                cbSceltaCV.setStyle("-fx-text-fill: red; -fx-border-color: red;");
                System.out.println("cv non selezionato");
            } else cbSceltaCV.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            if (!err) {
                Cittadino c = new Cittadino(nomeCittadino, cognomeCittadino, cfCittadino, usernameCittadino, emailCittadino, passwordCittadino, cbSceltaCV.getItems().toString());
                //TODO: inserire elementi in tabella --> FARE
                boolean esito = ClientCittadino.getInstance().nuovoCittadino(c);
                System.out.printf("Inserimento cittadino: esito %b per cittadino >\n %s\n",esito,c.toString());

                UniversalMethods.handleCloseButtonAction(event, btRegistraCittadino);
                UniversalMethods.vediFinestra("AccessoCittadino.fxml", "TATUM VACCINI - Richiesta accesso cittadino");
            }
        } catch (Exception e) {
            System.out.println("ERRORONE!!!");
        }
    }

    public void prendiCV(ActionEvent event) throws IOException{
        //carica nella BOX tutti i centri vaccinali
        UniversalMethods.vediFinestra("selezionaCV.fxml", "TATUM VACCINI - Scelta centro vaccinale");
        /*cbSceltaCV = new ChoiceBox<>();
        //ArrayList<CentroVaccinale> tuttiCv = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome("");
        ObservableList<String> observableList = FXCollections.observableArrayList("a", "b", "c");
        cbSceltaCV.setItems(observableList);*/
    }

    private boolean pswCorretta(String str) {
        Pattern p = Pattern.compile("");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private boolean emailCorretta(String str) {
        Pattern p = Pattern.compile("([0-9a-zA-Z]+)@([0-9a-zA-Z]+).([a-z]{2,3})");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private boolean nomeCorretto(String str) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private boolean cfCorretto(String cf) {
        Pattern p = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$");
        Matcher m = p.matcher(cf);
        return m.matches();
    }


    public void tornaRegistraCittadini(ActionEvent event) {
        UniversalMethods.handleCloseButtonAction(event, btIndietro1);
    }

    public void sceltaCVVaccinazione(ActionEvent event) {
    }

    public void caricaCV(MouseEvent event) throws RemoteException {
        ArrayList<CentroVaccinale> tuttiCv = (ArrayList<CentroVaccinale>) ClientCittadino.getInstance().ricercaCVperNome("");
        ObservableList<CentroVaccinale> options = FXCollections.observableArrayList(tuttiCv);
        cbSceltaCV.setItems(options);
    }
}
