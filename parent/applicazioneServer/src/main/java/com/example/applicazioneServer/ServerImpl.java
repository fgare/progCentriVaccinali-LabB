package com.example.applicazioneServer;

import com.example.common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Questa classe implementa un server remoto per un'applicazione Java.
 * Estende la classe Thread e implementa l'interfaccia ServerInterface.
 */
public class ServerImpl extends Thread implements ServerInterface {
    private final short REGISTRYPORT = 1099;
    private final short SERVERPORT = 1100;

    /**
     * Costruttore della classe
     * @throws RemoteException
     */
    public ServerImpl() throws RemoteException {}

    /**
     * Metodo privato che pubblica l'oggetto remoto sulla rete.
     * @throws RemoteException se si verifica un errore durante la pubblicazione dell'oggetto.
     */
    private void pubblica() throws RemoteException {
        ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, SERVERPORT);
        Registry registro = LocateRegistry.createRegistry(REGISTRYPORT);
        registro.rebind("com.example.applicazioneServer.Server-appCV", stub);
        System.out.println("com.example.applicazioneServer.Server pronto");
    }

    /**
     * Questo metodo viene eseguito quando il thread viene avviato.
     * Chiama il metodo pubblica() per pubblicare l'oggetto remoto sulla rete.
     * In caso di errore durante l'avvio del server, stampa un messaggio di errore.
     */
    public void run(){
        try {
            pubblica();
        }catch (RemoteException e){System.out.println("Errore nell'avvio del server");}
    }

    /**
     * Questo metodo registra un centro vaccinale nel database.
     * Utilizza il DataManager per registrare il centro vaccinale nel database.
     * @param c Il centro vaccinale da registrare nel database.
     * @return true se la registrazione è avvenuta con successo, false altrimenti.
     * @throws RemoteException se si verifica un errore durante la chiamata remota.
     */

    @Override
    public synchronized boolean registraCentroVaccinale(CentroVaccinale c) throws RemoteException {
        try {
            System.out.println("Sono stato chiamato"); //TODO da cancellare
            DataManager.getInstance().registraCentroVaccinale(c);
        } catch(SQLException e) {
            System.out.println("registraCentroVaccinale(" + c.getNome() + ") > SQLException");
            e.printStackTrace();
            return false;
        }
        System.out.println("Inserito nuovo centro vaccinale > " + c.toString());
        return true;
    }

    /**
     * Questo metodo rimuove un centro vaccinale dal database.
     * @param c Il centro vaccinale da rimuovere dal database.
     * @throws RemoteException se si verifica un errore durante la chiamata remota.
     */
    @Override
    public synchronized boolean rimuoviCentroVaccinale(CentroVaccinale c) throws RemoteException {
        System.out.println("Rimosso centro vaccinale > " + c.toString());
        return false;
    }

    /**
    *Questo metodo sincronizzato registra una vaccinazione per un paziente in un determinato centro vaccinale.
    *@param c Il centro vaccinale in cui è stata effettuata la vaccinazione.
    *@param v La vaccinazione da registrare.
    *@return true se la registrazione è andata a buon fine, false altrimenti.
    */
    @Override
    public synchronized boolean aggiungiDoseAPaziente(CentroVaccinale c, Vaccinazione v) throws RemoteException {
        try {
            DataManager.getInstance().registraVaccinazione(c,v);
        } catch(SQLException e) {
            System.out.printf("aggiungiDoseAPaziente(cf=%s, vaccino=%s) > SQLException\n", v.getCfCitt(), v.getVaccino().name());
            return false;
        }
        System.out.println("Registrata nuova vaccinazione > " + v.toString() + " presso " + c.getNome());
        return true;
    }

    /**
     *Questo metodo restituisce una lista di centri vaccinali che corrispondono alla stringa di ricerca fornita.
     *La stringa di ricerca viene utilizzata per filtrare i centri vaccinali presenti nel database.
     *@param ricerca stringa di ricerca per filtrare i centri vaccinali.
     *@return una lista di oggetti CentroVaccinale che corrisponde alla stringa di ricerca fornita.
     *@throws RemoteException in caso di errore nella comunicazione remota.
     */
    @Override
    public synchronized List<CentroVaccinale> getElencoCentriVaccinali(String ricerca) throws RemoteException {
        List<CentroVaccinale> ls;
        try {
            ls = DataManager.getInstance().elencoCentriVaccinali(ricerca);
            System.out.println("Restituita lista di " + ls.size() + " centri vaccinali");
            return ls;
        } catch(SQLException e) {
            System.out.println("Restituisco NULL");
            return null;
        }
    }

    /**
     * Questo metodo restituisce una lista di centri vaccinali filtrati per comune e tipologia.
     * @param comune Il nome del comune per il quale filtrare la lista dei centri vaccinali
     * @param tipologia La tipologia di centro vaccinale per il quale filtrare la lista
     * @return La lista di centri vaccinali filtrati per comune e tipologia, oppure null in caso di errore
     * @throws RemoteException in caso di errore nella comunicazione remota
     */
    public synchronized List<CentroVaccinale> getListaCvComuneTipologia(String comune, String tipologia) throws RemoteException {
        List<CentroVaccinale> ls;
        try {
            ls = DataManager.getInstance().elencoCentriVaccinaliPerComune(comune,tipologia);
        } catch(SQLException e) {
            return null;
        }
        System.out.println("Restituita lista di " + ls.size() + " centri vaccinali");
        return ls;
    }

    /**
     * Questo metodo permette di registrare un nuovo cittadino nel sistema.
     * @param c l'oggetto Cittadino che rappresenta il cittadino da registrare
     * @return true se la registrazione è avvenuta con successo, false altrimenti.
     * @throws RemoteException in caso di problemi di connessione remota.
     */
    @Override
    public synchronized boolean registraCittadino(Cittadino c) throws RemoteException {
        try {
            DataManager.getInstance().registraCittadino(c);
        } catch(SQLException e) {
            System.out.printf("registraCittadino(%s %s) > SQLException\n",c.getNome(),c.getCognome());
            return false;
        }
        System.out.println("Registrato nuovo cittadino > " + c.toString());
        return true;
    }

    /**
     *Questo metodo consente di registrare un nuovo evento avverso.
     *@param ea L'evento avverso da registrare
     *@return true se l'evento è stato registrato con successo, false altrimenti
     *@throws RemoteException In caso di problemi di connessione remota
     */
    @Override
    public synchronized boolean registraEventoAvverso(EventoAvverso ea) throws RemoteException {
        try {
            DataManager.getInstance().registraEventoAvverso(ea);
        } catch(SQLException e) {
            System.out.printf("registraEventoAvverso(%s, %d) > SQLException\n", ea.getQualeEvento().name(), ea.getSeverita());
            return false;
        }
        System.out.println("Registrato nuovo evento avverso " + ea.toString());
        return true;
    }

    /**
     *Verifica se l'accesso al centro vaccinale sia possibile con le credenziali fornite.
     *@param username nome utente per l'accesso
     *@param password password per l'accesso
     *@throws RemoteException in caso di problemi di connessione con il server
     */
    @Override
    public synchronized String accessoCentroVaccinale(String username, String password) throws RemoteException {
        try {
            String nomeCentro = DataManager.getInstance().login(username, password);
            if(nomeCentro.equals("")) {
                System.out.println("(" + username + "," + password + ") > credenziali errate");
                return nomeCentro;
            }
            System.out.println("(" + username + "," + password + ") > CV = " + nomeCentro);
            return nomeCentro;
        } catch(SQLException e) {
            System.out.println("SQLException");
            return null;
        }
    }

    @Override
    public HashMap<String, float[]> getInfoCentroVaccinale(String nomeCentroVaccinale) throws RemoteException {
        try {
            return DataManager.getInstance().getInfoCentroVaccinale(nomeCentroVaccinale);
        } catch(SQLException e) {
            return null;
        }
    }
}
