package com.example.applicazioneServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) throws SQLException {
        try {
            new ServerImpl().start();
            try {
                DataManager.getInstance(); //inizializza il DB se non esiste
            } catch(SQLException e) {
                System.out.println("Impossibile creare il database");
            }
        } catch(RemoteException e) {
            System.out.println("RemoteException - errore connessione del server");
        }
    }
}