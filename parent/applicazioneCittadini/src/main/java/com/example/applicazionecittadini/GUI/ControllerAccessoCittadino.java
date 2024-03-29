package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Client.ClientCittadino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 *Classe che gestisce l'interfaccia grafica per l'accesso e la registrazione dei cittadini.
 */
public class ControllerAccessoCittadino {
    @FXML
    private PasswordField pswF;
    @FXML
    private TextField tfUser;
    @FXML
    private Button btRegistraCittadino;
    @FXML
    private Button btIndietro;
    @FXML
    private Button btAccessoCittadino;

    /**
     * Questo metodo gestisce l'evento di pressione del pulsante per la registrazione di un nuovo cittadino.
     * Chiude la finestra corrente e apre la finestra per la registrazione.
     * @param event evento di pressione del pulsante
     * @throws IOException in caso di errore nell'apertura della finestra
     */
    public void registraCittadino(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btRegistraCittadino);
        UniversalMethods.vediFinestra("RegistraCittadino.fxml", "TATUM VACCINI - Registrazione cittadino");
    }

    /**
     *Questo metodo gestisce l'evento di pressione del pulsante per l'accesso del cittadino.
     *Chiude la finestra corrente e apre la finestra per l'inserimento degli eventi avversi.
     *Effettua anche il tentativo di login del cittadino.
     *@param event evento di pressione del pulsante
     *@throws IOException in caso di errore nell'apertura della finestra
     */
    public void accessoCittadino(ActionEvent event) throws IOException{
        String user = tfUser.getText();
        String psw = pswF.getText();
        //System.out.println("user = " + user + "\npsw = " + psw);

        String nomeCentro = ClientCittadino.getInstance().login(user,psw);
        if(nomeCentro == null || nomeCentro.equals("")) {
            System.out.println("Accesso negato");
            tfUser.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            pswF.setStyle("-fx-text-fill: red; -fx-border-color: red;");
        } else {
            UniversalMethods.handleCloseButtonAction(event, btAccessoCittadino);
            UniversalMethods.vediFinestra("InserimentoEventiAvversi.fxml", "TATUM VACCINI - Inserimento eventi avversi");
        }
    }

    /**
     *Questo metodo gestisce l'evento di pressione del pulsante per tornare alla homepage.
     *Chiude la finestra corrente e apre la finestra per la homepage.
     *@param event evento di pressione del pulsante
     *@throws IOException in caso di errore nell'apertura della finestra
     */
    public void tornaHomepage(ActionEvent event) throws IOException {
        UniversalMethods.handleCloseButtonAction(event, btIndietro);
        UniversalMethods.vediFinestra("Homepage.fxml", "TATUM VACCINI - Homepage");
    }

}
