package com.example.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaccia che estende l'interfaccia Remote.
 * Qui vengono dichiarti i metodi per salvare, modificare ed eliminare i dati
 *
 * @see Remote
 * @see CentroVaccinale
 * @see Vaccinazione
 * @see Cittadino
 * @see EventoAvverso
 */
public interface ServerInterface extends Remote {
    //metodi centro vaccinale
    /**
     * Dichiarazione metodo per registrare un centro vaccinale
     * @param c Centrovaccinale
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see CentroVaccinale
     */
    boolean registraCentroVaccinale(CentroVaccinale c) throws RemoteException;

    /**
     * Dichiarazione metodo per eliminare un centro vaccinale
     * @param c
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see CentroVaccinale
     */
    boolean rimuoviCentroVaccinale(CentroVaccinale c) throws RemoteException;

    /**
     * Dichiarazione metodo per aggiungere la vaccinazione ad un cittadino
     * @param c Centrovaccinale
     * @param v Vaccinazione
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see CentroVaccinale
     * @see Vaccinazione
     */
    boolean aggiungiDoseAPaziente(CentroVaccinale c, Vaccinazione v) throws RemoteException;

    //metodi cittadino

    /**
     * Dichiarazione metodo per registrare un nuovo cittadino
     * @param c Cittadino
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see Cittadino
     */
    boolean registraCittadino(Cittadino c) throws RemoteException;

    /**
     * Dichiarazione metodo per aggiungere un EventoAvverso
     * @param ea EventoAvverso
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see EventoAvverso
     */
    boolean registraEventoAvverso(EventoAvverso ea) throws RemoteException;

    /**
     *
     * @param nomeCV String: nome del centro vaccinale
     * @return List: lista di centri vaccinali
     * @throws RemoteException
     * @see CentroVaccinale
     */
    List<CentroVaccinale> getElencoCentriVaccinali(String nomeCV) throws RemoteException;

    /**
     *
     * @param comune String: comune del centro vaccinale
     * @param tipologia String: tipologia del centro vaccinale
     * @return List: lista di centri vaccinali
     * @throws RemoteException
     * @see CentroVaccinale
     */
    List<CentroVaccinale> getListaCvComuneTipologia(String comune, String tipologia) throws RemoteException;

    /**
     *
     * @param username String: username cittadino
     * @param password String: password cittadino
     * @return true se esito positivo, false altrimenti
     * @throws RemoteException
     * @see Cittadino
     */
    boolean accessoCentroVaccinale(String username, String password) throws RemoteException;

    byte[] getInfoCentroVaccinale(String nome) throws RemoteException;
}
