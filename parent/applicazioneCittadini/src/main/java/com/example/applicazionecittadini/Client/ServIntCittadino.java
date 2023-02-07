package com.example.applicazionecittadini.Client;

import com.example.common.CentroVaccinale;
import com.example.common.Cittadino;
import com.example.common.EventoAvverso;
import com.example.common.ServerInterface;

import java.rmi.RemoteException;
//TODO FARE JAVADOC
public interface ServIntCittadino extends ServerInterface {
    void aggiungiApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException;
    void rimuoviApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException;
    boolean registraCittadino(Cittadino c) throws RemoteException;
    void rimuoviPaziente(Cittadino c) throws RemoteException;
    boolean registraEventoAvverso(EventoAvverso ea) throws RemoteException;
    void cercaCentroVaccinale(String s) throws RemoteException;
    CentroVaccinale consultaInfoCentroVaccinale(CentroVaccinale c) throws RemoteException;
}
