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
            /*try {
                //DataManager.getInstance(); //inizializza il DB se non esiste
                DataManager d = new DataManager();
                DBHandler dbh = new DBHandler();
                dbh.initDB();
                dbh.connectDbCv();
                Indirizzo.Identificatore id = Indirizzo.Identificatore.VIA;
                Indirizzo i = new Indirizzo(id, "liberta",(short) 1,"Varese","VA", String.valueOf(21100));
                CentroVaccinale.Tipologia tip = CentroVaccinale.Tipologia.HUB;
                CentroVaccinale cv = new CentroVaccinale("San Ambrogio",i, tip);
                d.registraCentroVaccinale(cv);
            } catch(SQLException e) {
                System.out.println("Impossibile creare il database");*/
        } catch (RemoteException e) {
            System.out.println("RemoteException - errore connessione del server");
        } catch (SQLException e) {
            System.out.println("SQLException - errore connessione del server");
        }
    }
}