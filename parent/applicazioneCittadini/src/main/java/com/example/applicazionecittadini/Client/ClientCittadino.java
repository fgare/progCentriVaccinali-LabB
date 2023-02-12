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
import java.util.HashMap;
import java.util.List;

/**
 * Questa classe è utilizzata per creare un'istanza di client che rappresenta un cittadino.
 * Questo client ha come compito di interfacciarsi con il server remoto
 * e di effettuare le richieste al server stesso per poter interagire con l'applicazione.
 */
public class ClientCittadino extends Thread {
    private static ClientCittadino instance = null;
    private final short REGISTRYPORT = 1099;
    private ServerInterface server;
    private String username;

    /**
     * Costruttore del ClientCittadino.
     * Inizializza la connessione con il server.
     * @throws RemoteException in caso di problemi con la connessione remota
     * @throws NotBoundException in caso di problemi con il registry RMI
     */
    public ClientCittadino() throws RemoteException,NotBoundException {
        super("ClientCittadino");
        connetti();
    }

    /**
     * Questo metodo restituisce l'istanza del client Cittadino. Se l'istanza non è stata ancora creata,
     * viene creata una nuova istanza di ClientCittadino.
     * @return L'istanza di ClientCittadino.
     * @throws RemoteException In caso di problemi di connessione con il server.
     */
    public static ClientCittadino getInstance() throws RemoteException {
        if(instance == null) {
            try {
                instance = new ClientCittadino();
            } catch(NotBoundException e) {}
        }
        return instance;
    }

    /**
     * Questo metodo connette il client al server tramite il registro RMI
     * @throws RemoteException in caso di problemi di connessione remota
     * @throws NotBoundException in caso di non ritrovamento del servizio sul registro RMI
     */
    private void connetti() throws RemoteException, NotBoundException {
        Registry registro = LocateRegistry.getRegistry(REGISTRYPORT);
        server = (ServerInterface) registro.lookup("com.example.applicazioneServer.Server-appCV");
        System.out.println("Connesso al server: " + server.toString());
    }

    /**
     * Questo metodo run viene eseguito quando il thread viene avviato.
     */
    public void run() { }

    public String getUsernamee() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Metodo per registrare un nuovo cittadino presso il server.
     * @param c Oggetto di tipo Cittadino da registrare.
     * @return True se la registrazione è andata a buon fine, False altrimenti.
     */
    public boolean nuovoCittadino(Cittadino c) {
        try {
            return server.registraCittadino(c);
        } catch(RemoteException e) {
            return false;
        }
    }

    /**
     * Questo metodo permette di cercare un centro vaccinale per nome.
     * @param ricerca Il nome del centro vaccinale che si vuole cercare
     * @return La lista dei centri vaccinali che soddisfano i criteri di ricerca
     */
    public List<CentroVaccinale> ricercaCVperNome(String ricerca) {
        try {
            System.out.println("sono in ricercaCVxNome");
            return server.getElencoCentriVaccinali(ricerca);
        } catch (RemoteException e) {
            return null;
        }
    }

    /**
     * Questo metodo consente di cercare i centri vaccinali in base a un comune e una tipologia specifica.
     * @param comune il nome del comune in cui si vuole cercare il centro vaccinale.
     * @param tipologia la tipologia del centro vaccinale che si sta cercando.
     *@return una lista di centri vaccinali che corrispondono ai criteri di ricerca specificati
     */
    public List<CentroVaccinale> ricercaCvNomeTipologia(String comune, String tipologia) {
        try {
            return server.getListaCvComuneTipologia(comune,tipologia);
        } catch(RemoteException e) {
            return null;
        }
    }

    /**
     * Questo metodo registra un nuovo evento avverso presso il server.
     * @param e L'evento avverso che si vuole registrare.
     * @return true se l'evento avverso è stato registrato correttamente
     */
    public boolean nuovoEventoAvverso(EventoAvverso e) {
        try {
            return server.registraEventoAvverso(e,username);
        } catch(RemoteException ex) {
            return false;
        }
    }

    /**
     * Questo metodo rappresenta la funzione di login del client cittadino.
     * @param username username del centro vaccinale
     * @param password password del centro vaccinale
     * @return true se il login è avvenuto con successo, false altrimenti
     */
    public String login(String username, String password) {
        try {
            String nomeCentro = server.accessoCentroVaccinale(username,password);
            if(nomeCentro.equals("")) return "";
            this.username = username;
            return nomeCentro;
        } catch(RemoteException e) {
            return null;
        }
    }

    public HashMap<String, float[]> getInfoCentroVaccinale(String nomeCentroVaccinale) {
        try {
            return server.getInfoCentroVaccinale(nomeCentroVaccinale);
        } catch(RemoteException e) {
            return null;
        }
    }

    /**
     * La classe main del ClientCittadino.
     * Questo metodo viene eseguito all'avvio del programma. Crea una nuova istanza di ClientCittadino e avvia il thread.
     * In caso di eccezioni RemoteException o NotBoundException, viene stampato il relativo messaggio di errore.
     * @param args Argomenti della riga di comando.
     */
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
