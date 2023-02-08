package com.example.applicazionecv.GUI;

import com.example.applicazionecv.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe del package GUI contenente metodi generali per la gestione delle interfacce grafiche
 */
public class UniversalMethods {
    /**
     * Metodo della classe UniversalMethods che permette di chiudere la finestra aperta
     * @param event ActionEvent: evento generico
     * @param bt Button: bottone che permette di chiudere la pagina
     */
    public static void handleCloseButtonAction(ActionEvent event, Button bt) {
        Stage stage = (Stage) bt.getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo della classe UniversalMethods che permette di aprire una delle interfacce grafiche
     * @param res String: nome della resources (file fxml)
     * @param tit String: titolo da dare alla finestra
     * @throws IOException
     */
    public static void vediFinestra(String res, String tit) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(res));
        Scene scene;
        if (res.equals("Homepage.fxml"))
            scene = new Scene(fxmlLoader.load(), 650, 300);
        else
            scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle(tit);
        stage.setScene(scene);
        stage.show();
    }


    /**
     *Assegna un identificativo univoco a un elemento.
     *@return un numero intero casuale compreso tra 0 e 65535
     */
    public static int assegnaId() {
        Random r = new Random();
        int id = r.nextInt(65536);
        return id;
    }
}
