package com.example.applicazionecv.Client;

import com.example.common.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Prova extends Thread {
    private ServerInterface server;

    public Prova() {
        super("thread prova");
    }

    public void run() {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);
            server = (ServerInterface) reg.lookup("com.example.applicazioneServer.Server-appCV");
        } catch(RemoteException e) {
            System.out.println("RemoteException");
        } catch(NotBoundException f) {
            System.out.println("NotBoundException");
        }
    }

    public static void main(String[] args) {
        new Prova().start();
    }
}
