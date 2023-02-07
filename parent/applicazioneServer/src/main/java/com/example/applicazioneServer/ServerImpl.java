package com.example.applicazioneServer;

import com.example.common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ServerImpl extends Thread implements ServerInterface {
    private final short REGISTRYPORT = 1099;
    private final short SERVERPORT = 1100;
    //private List<ClientCVInterface> listaMediciConnessi;
    //private List<ClientCittadinoInterface> listaCittadiniConnessi;


    public ServerImpl() throws RemoteException {
        //listaMediciConnessi = new LinkedList<>();
        //listaCittadiniConnessi = new LinkedList<>();
    }

    private void pubblica() throws RemoteException {
        ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, SERVERPORT);
        Registry registro = LocateRegistry.createRegistry(REGISTRYPORT);
        registro.rebind("com.example.applicazioneServer.Server-appCV", stub);
        System.out.println("com.example.applicazioneServer.Server pronto");
    }

    public void run(){
        try {
            pubblica();
        }catch (RemoteException e){System.out.println("Errore nell'avvio del server");}
    }

    /*@Override
    public synchronized void aggiungiApplicazioneCV(ClientCVInterface ccvi) throws RemoteException {
        listaMediciConnessi.add(ccvi);
        System.out.println("Registrata nuova applicazione Centro Vaccinale");
    }

    @Override
    public synchronized void rimuoviApplicazioneCV(ClientCVInterface ccvi) throws RemoteException {
        listaMediciConnessi.remove(ccvi);
        System.out.println("Rimossa applicazione Centro Vaccinale");
    }*/

    @Override
    public synchronized boolean registraCentroVaccinale(CentroVaccinale c) throws RemoteException {
        try {
            System.out.println("Sono stato chiamato");
            Indirizzo i = new Indirizzo(Indirizzo.Identificatore.VIA,"cremona",(short) 23,"varese","VA","12345");
            CentroVaccinale cv = new CentroVaccinale("Ospedale di varese",i, CentroVaccinale.Tipologia.OSPEDALE);
            DataManager.getInstance().registraCentroVaccinale(cv);
            //DataManager.getInstance().registraCentroVaccinale(c);
        } catch(SQLException e) {
            System.out.println("registraCentroVaccinale(" + c.getNome() + ") > SQLException");
            e.printStackTrace();
            return false;
        }
        System.out.println("Inserito nuovo centro vaccinale > " + c.toString());
        return true;
    }

    @Override
    public synchronized boolean rimuoviCentroVaccinale(CentroVaccinale c) throws RemoteException {
        System.out.println("Rimosso centro vaccinale > " + c.toString());
        return false;
    }

    @Override
    public synchronized boolean aggiungiDoseAPaziente(CentroVaccinale c, Vaccinazione v) throws RemoteException {
        try {
            DataManager.getInstance().registraVaccinazione(c,v);
        } catch(SQLException e) {
            System.out.printf("aggiungiDoseAPaziente(cf=%s, vaccino=%s) > SQLException\n", v.getCodPrenotazione(), v.getVaccino().name());
            return false;
        }
        System.out.println("Registrata nuova vaccinazione > " + v.toString() + " presso " + c.getNome());
        return true;
    }

    @Override
    public synchronized List<CentroVaccinale> getElencoCentriVaccinali(String ricerca) throws RemoteException {
        List<CentroVaccinale> ls;
        try {
            ls = DataManager.getInstance().elencoCentriVaccinali(ricerca);
        } catch(SQLException e) {
            return null;
        }
        System.out.println("Restituita lista di " + ls.size() + " centri vaccinali");
        return ls;
    }

    /*@Override
    public synchronized void aggiungiApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException {
        listaCittadiniConnessi.add(cci);
        System.out.println("Registrata nuova applicazione cittadino");
    }

    @Override
    public synchronized void rimuoviApplicazioneCittadino(ClientCittadinoInterface cci) throws RemoteException {
        listaCittadiniConnessi.remove(cci);
        System.out.println("Rimossa applicazione cittadino");
    }*/

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

    @Override
    public synchronized void rimuoviPaziente(Cittadino c) throws RemoteException {

    }

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

    @Override
    public synchronized void cercaCentroVaccinale(String s) throws RemoteException {

    }

    @Override
    public synchronized CentroVaccinale consultaInfoCentroVaccinale(CentroVaccinale c) throws RemoteException {
        return null;
    }
}
