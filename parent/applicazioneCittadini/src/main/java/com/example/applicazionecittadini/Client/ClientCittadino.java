package com.example.applicazionecittadini.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
//TODO FARE JAVADOC
public class ClientCittadino extends Thread implements ClientCittadinoInterface {
    private final short REGISTRYPORT = 1099;
    private final short CLIENTCITTPORT = 1101;
    private ServIntCittadino server;

    public ClientCittadino() throws RemoteException,NotBoundException {
        super("ClientCittadino");
        connetti();
    }

    private void connetti() throws RemoteException, NotBoundException {
        Registry registro = LocateRegistry.getRegistry(REGISTRYPORT);
        ServIntCittadino server = (ServIntCittadino) registro.lookup("com.example.applicazioneServer.Server-appCV");
    }

    public void registraApplicazioneCittadino() throws RemoteException {
        ClientCittadinoInterface stub = (ClientCittadinoInterface) UnicastRemoteObject.exportObject(this,CLIENTCITTPORT);
        server.aggiungiApplicazioneCittadino(this);
    }
}
