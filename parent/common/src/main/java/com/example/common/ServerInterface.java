package com.example.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {
    //metodi centro vaccinale
    //void aggiungiApplicazioneCV(ClientCVInterface ccvi) throws RemoteException;
    //void rimuoviApplicazioneCV(ClientCVInterface ccvi) throws RemoteException;
    boolean registraCentroVaccinale(CentroVaccinale c) throws RemoteException;
    boolean rimuoviCentroVaccinale(CentroVaccinale c) throws RemoteException;
    boolean aggiungiDoseAPaziente(CentroVaccinale c, Vaccinazione v) throws RemoteException;

    //metodi cittadino
    //void aggiungiApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException;
    //void rimuoviApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException;
    boolean registraCittadino(Cittadino c) throws RemoteException;
    void rimuoviPaziente(Cittadino c) throws RemoteException;
    boolean registraEventoAvverso(EventoAvverso ea) throws RemoteException;
    void cercaCentroVaccinale(String s) throws RemoteException;
    CentroVaccinale consultaInfoCentroVaccinale(CentroVaccinale c) throws RemoteException;
    List<CentroVaccinale> getElencoCentriVaccinali(String nomeCV) throws RemoteException;
}
