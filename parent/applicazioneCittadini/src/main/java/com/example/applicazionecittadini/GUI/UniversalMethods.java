package com.example.applicazionecittadini.GUI;

import com.example.applicazionecittadini.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class UniversalMethods {

    public static void handleCloseButtonAction(ActionEvent event, Button bt) {
        Stage stage = (Stage) bt.getScene().getWindow();
        stage.close();
    }

    public static int assegnaId() {
        Random r = new Random();
        int id = r.nextInt(65536);
        return id;
    }

    public static void vediFinestra(String res, String tit) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/" + res));
        Scene scene;
        if (res.equals("Homepage.fxml"))
            scene = new Scene(loader.load(), 615, 275);
        else if (res.equals("SceltaRicerca.fxml")) {
            scene = new Scene(loader.load(), 650, 335);
        } else if (res.equals("AccessoCittadino.fxml"))
            scene = new Scene(loader.load(), 600, 300);
        else if (res.equals("RegistraCittadino.fxml"))
            scene = new Scene(loader.load(), 630, 370);
        else if (res.equals("VisualizzaCVperNome.fxml") || res.equals("VisualizzaCVperComuneTipologia.fxml"))
            scene = new Scene(loader.load(), 800, 600);
        else
            scene = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle(tit);
        stage.setScene(scene);
        stage.show();
    }


}