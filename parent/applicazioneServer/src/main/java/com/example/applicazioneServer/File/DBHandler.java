//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.applicazioneServer.File;

import com.example.applicazioneServer.DataManager;
import com.example.applicazioneServer.SWVar;
import com.example.applicazioneServer.ServerImpl;
import com.example.common.CentroVaccinale;
import com.example.common.Indirizzo;

import java.rmi.RemoteException;
import java.sql.*;

/**
 * Classe gestionale del database per la sua creazione
 * e per la manipolazione delle tabelle
 *
 * @author Luca Canali
 */

public class DBHandler {

    /**
     * Il nome del database
     */
    private final String dbCV = "databasecv"; //nome del database

    /**
     * URL del database
     */
    private final String JDBCurl = "jdbc:postgresql://localhost:5432/";

    /**
     * Nome dell'utente per accedere al database
     */
    private final String user = "postgres";

    /**
     * Password per accedere al database
     */
    private final String password = "admin";

    /**
     * Oggetto di connessione al database
     */
    private Connection conn;

    /**
     *Costruttore di default che inizializza un oggetto DBHandler.
     * @throws SQLException in caso di errore nella connessione al database.
     */
    public DBHandler() throws SQLException { }

    /**
     * Metodo che istanzia la connessione al database
     * @throws SQLException
     */
    //metodo che instanzia la connessione al server (la istanzia al DB standard postgres, non al databaseCV!)
    private void connectDBMS() throws SQLException {
        conn = DriverManager.getConnection(JDBCurl, user, password);
        System.out.println("Connesso al database postgres");
    }

    /**
     * Metodo che istanzia la connessione al databaseCV
     * @throws SQLException
     */
    //metodo che istanzia la connessione al databasecv (da usare con cautela)
    public Connection connectDbCv() throws SQLException {
        String dbCvUrl = JDBCurl + dbCV;
        conn = DriverManager.getConnection(dbCvUrl, user, password);
        System.out.println("Connesso al databaseCV");
        return conn;

    }

    /**
     * Metodo che termina la connessione al database
     * @throws SQLException
     */
    private void disconnect() throws SQLException {
        conn.close();
    }

    /**
     * Metodo che restituisce la connessione
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if(conn == null || conn.isClosed()) {
            return connectDbCv();
        }
        return conn;
    }

    /**
     * Metodo che inizializza il databaseCV.
     * Se non esiste viene creato e popolato con le tabelle necessarie
     *
     * @throws SQLException
     */
    public void initDB() throws SQLException {
        connectDBMS();
        String createQuery = "SELECT 1 FROM pg_database WHERE datname = ?";
        PreparedStatement st = conn.prepareStatement(createQuery);
        st.setString(1, dbCV);
        ResultSet rs = st.executeQuery();
        if(!rs.next()) {
            createQuery = "CREATE DATABASE " + dbCV;
            st = conn.prepareStatement(createQuery);
            st.executeUpdate();
            disconnect(); //disconnessione dal server
            connectDbCv(); //conessione al server e al databaseCV
            createTableCV();
            createTableCittadino();
            createTableRegistrazione();
            createTableIndirizzi();
            createTableVaccinazioni();
            createTableVaccinazioniEventiAvversi();
            System.out.println("Tabelle create");
        }
        disconnect();
    }

    /**
     * Questo metodo esegue una serie di query su una connessione al database,
     * utilizzando un oggetto PreparedStatement. Le query vengono eseguite
     * come una transazione, che viene confermata (commit) se non vengono
     * sollevate eccezioni.
     *
     * @param query Una serie di query da eseguire come transazione
     * @return true se tutte le query sono state eseguite correttamente, false altrimenti
     * @throws SQLException Se viene sollevata un'eccezione durante l'esecuzione delle query
     */
    public boolean insert(String query) throws SQLException {
        this.connectDbCv();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
        conn.close();

        return true; //se non sono state sollevate eccezioni restituisci TRUE
    }

    /**
     * Questo metodo crea la tabella "TAB_CENTRIVACCINALI" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    private void createTableCV() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_CENTRIVACCINALI + "( " +
                        " nome VARCHAR(255), " +
                        " tipologia VARCHAR(10), " +
                        " PRIMARY KEY ( nome ));"); {
            stmt.executeUpdate();
        }
    }

    /**
     * Questo metodo crea la tabella "TAB_CITTADINI" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    private void createTableCittadino() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_CITTADINI + "( " +
                        " CF VARCHAR(16), " +
                        " nome VARCHAR(128), " +
                        " cognome VARCHAR(128), " +
                        " email VARCHAR(255), " +
                        " password VARCHAR(255), " +
                        " username VARCHAR(255) UNIQUE , " +
                        " PRIMARY KEY (CF) )" );
        {
            stmt.executeUpdate();
        }
    }

    /**
     * Questo metodo crea la tabella "TAB_INDIRIZZI" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    private void createTableIndirizzi() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_INDIRIZZI + "( " +
                        " id_ind SERIAL PRIMARY KEY, " +
                        " identificatore VARCHAR(10), " +
                        " localizzazione VARCHAR(255), " +
                        " civico NUMERIC(4), " +
                        " comune VARCHAR(255)," +
                        " provincia CHAR(2), " +
                        " zip CHAR(5), " +
                        " centro_vaccinale VARCHAR(255), " +
                        " FOREIGN KEY (centro_vaccinale) REFERENCES " + SWVar.TAB_CENTRIVACCINALI + "(nome) " +
                        " ON UPDATE CASCADE " +
                        " ON DELETE CASCADE)");
        {
            stmt.executeUpdate();
        }
    }

    /**
     * Questo metodo crea la tabella "TAB_REGISTRAZIONE" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    private void createTableRegistrazione() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_REGISTRAZIONE + "( " +
                        " centro_vaccinale VARCHAR(255) " +
                        " REFERENCES " + SWVar.TAB_CENTRIVACCINALI +
                        " ON UPDATE CASCADE " +
                        " ON DELETE CASCADE," +
                        " CF VARCHAR(16) UNIQUE " +
                        " REFERENCES " + SWVar.TAB_CITTADINI +
                        " ON UPDATE CASCADE " +
                        " ON DELETE CASCADE," +
                        " codice SERIAL, " +
                        " PRIMARY KEY (centro_vaccinale,CF))");
        {
            stmt.executeUpdate();
        }
    }

    /**
     * Questo metodo crea la tabella "TAB_VACCINAZIONI" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    private void createTableVaccinazioni() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_VACCINAZIONI + "( " +
                        " ID_vaccino SERIAL PRIMARY KEY, " +
                        " data DATE, " +
                        " vaccino VARCHAR(20), " +
                        " CF_citt VARCHAR(16), " +
                        " FOREIGN KEY (CF_citt) REFERENCES " + SWVar.TAB_CITTADINI +
                        " ON UPDATE CASCADE " +
                        " ON DELETE CASCADE " +
                        ")");
        {
            stmt.executeUpdate();
        }
    }

    /**
     * Questo metodo crea la tabella "TAB_EVENTIAVVERSI" nel database databaseCV.
     *
     * @throws SQLException Se viene sollevata un'eccezione durante la creazione della tabella
     */
    public void createTableVaccinazioniEventiAvversi() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE " + SWVar.TAB_EVENTIAVVERSI + "( " +
                        " ID_ea SERIAL PRIMARY KEY , " +
                        " evento VARCHAR(32), " +
                        " intensita SMALLINT, " +
                        " ID_vaccino SERIAL, " +
                        " Note VARCHAR(255), " +
                        " FOREIGN KEY (ID_vaccino) REFERENCES " + SWVar.TAB_VACCINAZIONI +
                        " ON UPDATE CASCADE ON DELETE CASCADE" +
                        ")");
        stmt.executeUpdate();
    }

    /**
     * Questo metodo esegue una query di selezione sul database e restituisce i risultati.
     *
     * @param query La query da eseguire come oggetto PreparedStatement
     * @return Il ResultSet con i risultati della query
     * @throws SQLException Se viene sollevata un'eccezione durante l'esecuzione della query
     */
    public ResultSet select(String query) throws SQLException {
        this.connectDbCv();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);
        conn.close();
        return rs;
    }

    /**
     * Questo metodo restituisce il numero di righe in un ResultSet.
     *
     * @param rs Il ResultSet da analizzare
     * @return Il numero di righe nel ResultSet
     * @throws SQLException Se viene sollevata un'eccezione durante l'analisi del ResultSet
     */
    public static int resultSetSize(ResultSet rs) throws SQLException {
        rs.last(); //cursore sull'ultima tupla
        int tupleCount = rs.getRow(); //ottine numero dell'ultima tupla
        rs.beforeFirst(); //cursore di nuovo alla posizione inziale (prima della prima tupla)
        return tupleCount;
    }

    public static void main(String[] args) {
        try {
            new ServerImpl().start();
            try {
                //DataManager.getInstance(); //inizializza il DB se non esiste
                DataManager d = new DataManager();
                DBHandler dbh = new DBHandler();
                dbh.initDB();
                dbh.connectDbCv();
                Indirizzo.Identificatore id = Indirizzo.Identificatore.VIA;
                Indirizzo i = new Indirizzo(id, "liberta",(short) 1,"Varese","VA", String.valueOf(21100));
                CentroVaccinale.Tipologia tip = CentroVaccinale.Tipologia.HUB;
                CentroVaccinale cv = new CentroVaccinale("San Ambrogio",i, tip);
                d.registraCentroVaccinale(cv);
            } catch(SQLException e) {
                System.out.println("Impossibile creare il database");
            }
        } catch(RemoteException e) {
            System.out.println("RemoteException - errore connessione del server");
        }
    }
}
