package com.example.applicazionecittadini.Client;

import com.example.common.CentroVaccinale;
import com.example.common.Cittadino;
import com.example.common.EventoAvverso;
import com.example.common.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

//TODO FARE JAVADOC
public class ClientCittadino extends Thread {
    private static ClientCittadino instance = null;
    private final short REGISTRYPORT = 1099;
    private final short CLIENTCITTPORT = 1101;
    private ServerInterface server;

    public ClientCittadino() throws RemoteException,NotBoundException {
        super("ClientCittadino");
        connetti();
    }

    public static ClientCittadino getInstance() throws RemoteException {
        if(instance == null) {
            try {
                instance = new ClientCittadino();
            } catch(NotBoundException e) {}
        }
        return instance;
    }

    private void connetti() throws RemoteException, NotBoundException {
        Registry registro = LocateRegistry.getRegistry(REGISTRYPORT);
        ServerInterface server = (ServerInterface) registro.lookup("com.example.applicazioneServer.Server-appCV");
        System.out.println("Connesso al server: " + server.toString());
    }

    public void run() { }

    public boolean nuovoCittadino(Cittadino c) {
        try {
            return server.registraCittadino(c);
        } catch(RemoteException e) {
            return false;
        }
    }

    public List<CentroVaccinale> ricercaCVperNome(String ricerca) {
        try {
            return server.getElencoCentriVaccinali(ricerca);
        } catch (RemoteException e) {
            return null;
        }
    }

    public List<CentroVaccinale> ricercaCvNomeTipologia(String comune, String tipologia) {
        try {
            return server.getListaCvComuneTipologia(comune,tipologia);
        } catch(RemoteException e) {
            return null;
        }
    }

    public boolean nuovoEventoAvverso(EventoAvverso e) {
        try {
            return server.registraEventoAvverso(e);
        } catch(RemoteException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            new ClientCittadino().start();
        } catch (RemoteException e) {
            System.out.println("RemoteException");
        } catch (NotBoundException e) {
            System.out.println("NotBoundException");
        }
    }

}
