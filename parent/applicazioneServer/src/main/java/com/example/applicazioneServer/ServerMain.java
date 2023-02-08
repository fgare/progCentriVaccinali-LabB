package com.example.applicazioneServer;

import com.example.applicazioneServer.File.DBHandler;
import com.example.common.CentroVaccinale;
import com.example.common.Indirizzo;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Classe Main di applicazioneServer
 */
public class ServerMain {
    /**
     * Metodo main di ServerMain
     * @param args
     */
    public static void main(String[] args) {
        try {
            new ServerImpl().start();
            DataManager.getInstance(); //inizializza il DB se non esiste
        } catch (RemoteException e) {
            System.out.println("RemoteException - errore connessione del server");
        } catch (SQLException e) {
            System.out.println("SQLException - Database non trovato");
        }
    }
}