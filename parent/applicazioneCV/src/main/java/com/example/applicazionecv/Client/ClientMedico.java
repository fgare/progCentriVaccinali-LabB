package com.example.applicazionecv.Client;


import com.example.common.CentroVaccinale;
import com.example.common.ServerInterface;
import com.example.common.Vaccinazione;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *La classe ClientMedico rappresenta il client lato medico nel sistema di gestione delle vaccinazioni.
 *La classe è una classe singleton, ovvero sarà possibile avere un'unica istanza della classe.
 *Il medico tramite questa classe può accedere ai servizi offerti dal server per gestire le vaccinazioni.
 *@author Federico Garegnani
 */
public class ClientMedico extends Thread {
    private static ClientMedico instance = null;
    private final short REGISTRYPORT = 1099;
    private final short CLIENTCVPORT = 1101;
    private ServerInterface server;
    private CentroVaccinale centroInUso;

    /**
     *Costruttore privato della classe ClientMedico.
     *@throws RemoteException
     *@throws NotBoundException
     */
    private ClientMedico() throws RemoteException, NotBoundException {
        super("ClientMedico");
        connetti();
    }

    /**
     *Metodo che fornisce un'istanza singleton del client medico.
     *@return l'istanza singleton del client medico
     *@throws RemoteException se si verifica un errore durante la connessione al server
     */
    public static ClientMedico getInstance() throws RemoteException {
        if(instance == null) {
            try {
                instance = new ClientMedico();
            } catch(NotBoundException e) {}
        }
        return instance;
    }

    /**
     *Questo metodo si connette al registry presente sulla porta specificata in REGISTRYPORT e cerca il riferimento al server tramite il suo nome, "com.example.applicazioneServer.Server-appCV".
     *Se la connessione al registry e all'oggetto remoto sul server viene effettuata correttamente, viene stampato un messaggio di conferma sulla console.
     *@throws RemoteException Se viene sollevata un'eccezione durante la connessione al registry o all'oggetto remoto sul server.
     *@throws NotBoundException Se l'oggetto cercato con il nome "com.example.applicazioneServer.Server-appCV" non è registrato sul registry.
     */
    private void connetti() throws RemoteException, NotBoundException {
        Registry registro = LocateRegistry.getRegistry(REGISTRYPORT);
        System.out.println(registro.list());
        server = (ServerInterface) registro.lookup("com.example.applicazioneServer.Server-appCV");
        System.out.println("Connesso al server: " + server.toString());
    }

    public void disconnetti() {}

    public void run(){

    }

    public void setCentroInUso(CentroVaccinale c) {
        centroInUso = c;
    }

    public CentroVaccinale getCentroInUso() {
        return centroInUso;
    }

    /**
     *Questo metodo invia una richiesta al server per registrare un Centro Vaccinale.
     *@param c Centro Vaccinale da registrare
     *@return true se la registrazione è avvenuta con successo, false altrimenti
     */
    public boolean registraCentroVaccinale(CentroVaccinale c) {
        try {
            System.out.println("Invio cv al server");
            return server.registraCentroVaccinale(c);
        } catch(RemoteException e) {
            return false;
        }
    }

    public boolean accediACv(String c) throws RemoteException {
        return false;
    }

    /**
     *Questo metodo permette di aggiungere una dose di vaccinazione ad un paziente.
     *@param c Centro vaccinale del paziente.
     *@param v Dose da vaccinare.
     *@return true se l'aggiunta è stata effettuata con successo, false altrimenti.
     */
    public boolean aggiungiDose(CentroVaccinale c, Vaccinazione v) {
        try{
            return server.aggiungiDoseAPaziente(c,v);
        } catch(RemoteException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            new ClientMedico().start();
        } catch(RemoteException | NotBoundException e) {
            System.out.println("Impossibile connettersi al server");
        }
    }
}
