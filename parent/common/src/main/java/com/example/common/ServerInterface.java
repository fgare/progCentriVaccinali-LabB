package com.example.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {
    //metodi centro vaccinale
    boolean registraCentroVaccinale(CentroVaccinale c) throws RemoteException;
    boolean rimuoviCentroVaccinale(CentroVaccinale c) throws RemoteException;
    boolean aggiungiDoseAPaziente(CentroVaccinale c, Vaccinazione v) throws RemoteException;

    //metodi cittadino
    boolean registraCittadino(Cittadino c) throws RemoteException;
    boolean registraEventoAvverso(EventoAvverso ea) throws RemoteException;
    List<CentroVaccinale> getElencoCentriVaccinali(String nomeCV) throws RemoteException;
    List<CentroVaccinale> getListaCvComuneTipologia(String comune, String tipologia) throws RemoteException;
    boolean accessoCentroVaccinale(String username, String password) throws RemoteException;
}
