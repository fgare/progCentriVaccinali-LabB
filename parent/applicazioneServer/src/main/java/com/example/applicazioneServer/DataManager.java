//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.applicazioneServer;
import com.example.applicazioneServer.File.DBHandler;
import com.example.common.*;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsabile della gestione dei dati all'interno del database.
 *
 */

public class DataManager {

    /**
     * Variabile statica che rappresenta l'istanza unica della classe DataManager.
      *Utilizzata per garantire che vi sia una sola istanza della classe.
     */
    public static DataManager instance = null;

    /**
     * Costruttore privato della classe DataManager.
     * Il costruttore istanzia un oggetto di tipo DBHandler e richiama il metodo initDB().
     *
     * @throws SQLException in caso di errore durante la creazione del database
     */
    public DataManager() throws SQLException {
        new DBHandler().initDB(); //crea il database nel caso non esistesse
    }

    /**
     * La classe DataManager garantisce l'utilizzo di un'unica istanza di se stessa
     * durante l'esecuzione dell'applicazione.
     *
     * @return L'unica istanza di DataManager.
     * @throws SQLException in caso di problemi con la connessione al database.
     */
    public static DataManager getInstance() throws SQLException {
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * Questo metodo registra un nuovo centro vaccinale nel database.
     * Prepara le query necessarie per l'inserimento dei dati del centro vaccinale e
     * dei suoi dettagli di indirizzo.
     *
     * @param c Centro Vaccinale
     * @return boolean
     * @throws SQLException
     */
    public boolean registraCentroVaccinale(CentroVaccinale c) throws SQLException {
        final String NUOVO_CV =
                "INSERT INTO " + SWVar.TAB_CENTRIVACCINALI +
                " VALUES('" + c.getNome() + "','" +
                c.getTipologia().name() + "');";

        Indirizzo i = c.getIndirizzo();
        final String NUOVO_INDIRIZZO =
                "INSERT INTO " + SWVar.TAB_INDIRIZZI +
                "(identificatore,localizzazione,civico,comune,provincia,zip,centro_vaccinale)" +
                " VALUES('" + i.identificatore() + "','" +
                i.localizzazione() + "','" +
                i.numCivico() + "','" +
                i.comune() + "','" +
                i.provincia() + "','" +
                i.ZIP() + "','" +
                c.getNome() + "');";

        DBHandler handler = new DBHandler();
        Connection conn = handler.connectDbCv();

        boolean esito = handler.insert(NUOVO_CV + "\n" + NUOVO_INDIRIZZO);

        handler.disconnect();

        return esito;
    }

    /**
     * Questo metodo registraCittadino serve per registrare un oggetto Cittadino nel database.
     * Prepara una query per inserireun nuovo cittadino con i dati forniti dall'oggetto Cittadino.
     *
     * @param c Cittadino
     * @return boolean
     * @throws SQLException
     */
    public boolean registraCittadino(Cittadino c) throws SQLException {
        final String NUOVO_CITTADINO =
                "INSERT INTO " + SWVar.TAB_CITTADINI + " VALUES('" +
                        c.getCf() + "','" +
                        c.getNome() + "','" +
                        c.getCognome() + "','" +
                        c.getEmail() + "','" +
                        c.getPsw() + "','" +
                        c.getUsername() + "');";
        final String INSERISCI_REGISTRAZIONE =
                "INSERT INTO " + SWVar.TAB_REGISTRAZIONE + " VALUES ('" +
                        c.getCentroVaccinale() + "', '" +
                        c.getCf() + "');";

        DBHandler handler = new DBHandler();
        boolean esito = handler.insert(NUOVO_CITTADINO + "\n" + INSERISCI_REGISTRAZIONE);
        handler.disconnect();
        return esito;
    }

    /**
     * Questo metodo registra una vaccinazione per un cittadino in un determinato centro vaccinale.
     * Il metodo esegue prima una verifica sul fatto che il cittadino sia già registrato nel centro specificato.
     *
     * @param c Centro vaccinale
     * @param v Vaccinazione
     * @return boolean
     * @throws SQLException
     */
    public boolean registraVaccinazione(String cv, Vaccinazione v) throws SQLException {
        final String VERIFICA_CITTADINO_REGISTRATO =
                "SELECT * FROM " + SWVar.TAB_REGISTRAZIONE +
                " WHERE cf= '" + v.getCfCitt() + "' AND centro_vaccinale= '" + cv + "';";
        final String NUOVA_VACCINAZIONE =
                "INSERT INTO " + SWVar.TAB_VACCINAZIONI + "(data, vaccino, CF_citt) " +
                " VALUES('" +
                v.getData() + "','" +
                v.getVaccino().name() + "','" +
                v.getCfCitt() + "');";

        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs = handler.select(VERIFICA_CITTADINO_REGISTRATO);
        if(!rs.next()) {
            System.out.println("Il cittadino " + v.getCfCitt() + " non è ancora registrato");
            return false;
        }

        boolean esito = handler.insert(NUOVA_VACCINAZIONE);
        System.out.println("Esito inserimento cittadino > " + esito);
        handler.disconnect();
        return esito;
    }

    /**
     * Questo codice registra un evento avverso in un database.
     * Prepara le query necessarie per l'inserimento dei dati
     *
     * @param ea Evento Avverso
     * @return boolean
     * @throws SQLException
     */
    public boolean registraEventoAvverso(EventoAvverso ea, String username) throws SQLException {
        /* prima verifico che il cittadino si sia vaccinato almeno una volta.
            Se si è vacchinato più volte considero la vaccinazione più recente.
         */
        final String VERIFICA_REGISTRAZIONE =
                "SELECT id_vaccino " +
                "FROM " + SWVar.TAB_VACCINAZIONI + " JOIN " + SWVar.TAB_CITTADINI + " ON cf=cf_citt " +
                "WHERE username='" + username + "' " +
                "ORDER BY data DESC;";

        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs1 = handler.select(VERIFICA_REGISTRAZIONE);
        if(!rs1.next()) {
            System.out.println("Il cittadino non si è ancora vaccinato");
            return false;
        }
        int idVac = rs1.getInt(1);
        System.out.println("IDvaccinazione = " + idVac);

        final String NUOVO_EVENTOAVVERSO =
                "INSERT INTO " + SWVar.TAB_EVENTIAVVERSI + "(evento,intensita,ID_vaccino,Note)" +
                        " VALUES('" +
                        ea.getQualeEvento() + "','" +
                        ea.getSeverita() + "','" +
                        idVac + "','" +
                        ea.getNota() + "');";

        boolean esito = handler.insert(NUOVO_EVENTOAVVERSO);
        handler.disconnect();
        return esito;
    }

    /**
     * Questo metodo restituisce una lista dei centri vaccinali presenti nel sistema
     * che corrispondono alla stringa di ricerca fornita.
     * La ricerca viene effettuata sul nome del centro vaccinale.
     *
     * @param searchString La stringa di ricerca per i centri vaccinali.
     * @return La lista dei centri vaccinali corrispondenti alla stringa di ricerca.
     * @throws SQLException Se si verifica un errore durante la comunicazione con il database.
     */
    public List<CentroVaccinale> elencoCentriVaccinali(String searchString) throws SQLException {
        final String GET_ELENCO_CV =
                "SELECT * FROM " + SWVar.TAB_CENTRIVACCINALI + " JOIN " + SWVar.TAB_INDIRIZZI + " ON nome = centro_vaccinale " +
                        "WHERE nome LIKE '%" + searchString + "%'";
        final String GET_TUTTI_CV = "SELECT * FROM " + SWVar.TAB_CENTRIVACCINALI + " JOIN " + SWVar.TAB_INDIRIZZI + " ON nome=centro_vaccinale;";

        //connessione al database
        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs;
        if(searchString.equals("") || searchString == null) {
            System.out.println("Eseguo query > " + GET_TUTTI_CV);
            rs = handler.select(GET_TUTTI_CV);
        } else {
            System.out.println("Eseguo query > " + GET_ELENCO_CV);
            rs = handler.select(GET_ELENCO_CV);
        }

        List<CentroVaccinale> lsCV = new ArrayList<>();
        //System.out.println("Inizializzo arraylist lscv");
        //rs.last();
        //System.out.println("Dimensione = " + rs.getRow());

        while(rs.next()) {
            //costruisco prima l'indirizzo
            Indirizzo i = new Indirizzo(
                    rs.getInt(3),
                    Indirizzo.Identificatore.parse(rs.getString(4)),
                    rs.getString(5),
                    rs.getShort(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
            );
            //System.out.println("Indirizzo letto = " + i.toString());
            //costruisco l'oggetto centro vaccinale e lo aggiungo alla lista
            CentroVaccinale c = new CentroVaccinale(
                    rs.getString(1),
                    i,
                    CentroVaccinale.Tipologia.parse(rs.getString(2))
            );
            lsCV.add(
                    c
            );
        }

        handler.disconnect();
        System.out.println("Ritorno lista di " + lsCV.size() + " elementi");
        return lsCV;
    }

    /**
     * Questo metodo restituisce una lista dei centri vaccinali presenti nel sistema
     * che corrispondono alle stringhe di ricerca fornite
     * La ricerca viene effettuata sul comune del centro vaccinale e sulla tipologia
     *
     * @param comune Stringa che corrisponde al comune
     * @param tipologia Stringa che corrisponde alla tipologia
     * @return La lista dei centri vaccinali corrispondenti alla stringa di ricerca.
     * @throws SQLException Se si verifica un errore durante la comunicazione con il database.
     */
    //TODO
    public List<CentroVaccinale> elencoCentriVaccinaliPerComune(String comune, String tipologia) throws SQLException {
        final String GET_ELENCO_TIPOLOGIA =
                "SELECT * " +
                "FROM " + SWVar.TAB_CENTRIVACCINALI + " JOIN " + SWVar.TAB_INDIRIZZI + " ON nome = centro_vaccinale " +
                "WHERE comune LIKE '%" + comune + "%' AND tipologia = '" + tipologia + "';";

        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs = handler.select(GET_ELENCO_TIPOLOGIA);

        List<CentroVaccinale> lsCV = new ArrayList<>();
        while(rs.next()) {
            //costruisco prima l'indirizzo
            Indirizzo i = new Indirizzo(
                    rs.getInt("3"),
                    Indirizzo.Identificatore.parse(rs.getString(4)),
                    rs.getString(5),
                    rs.getShort(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
            );

            //costruisco l'oggetto centro vaccinale e lo aggiungo alla lista
            lsCV.add(
                    new CentroVaccinale(
                            rs.getString(1),
                            i,
                            CentroVaccinale.Tipologia.parse(rs.getString(2))
                    )
            );
        }

        System.out.println("Ritorno lista di " + lsCV.size() + " elementi");
        handler.disconnect();
        return lsCV;
    }

    /**
     * Questo metodo registra un indirizzo nel relativo database.
     *
     * @param indirizzo Indirizzo
     * @return boolean
     * @throws SQLException
     */
    public boolean registraIndirizzo(Indirizzo indirizzo) throws SQLException {
        final String NUOVO_INDIRIZZO = "INSERT INTO " + SWVar.TAB_INDIRIZZI + "(identificatore, localizzazione, civico, comune, provincia, ZIP) " +
                " VALUES('" +
                indirizzo.identificatore() + "','" +
                indirizzo.localizzazione() + "','" +
                indirizzo.numCivico() + "','" +
                indirizzo.comune() + "','" +
                indirizzo.provincia() + "','" +
                indirizzo.ZIP() + "');";

        DBHandler handler = new DBHandler();
        Connection conn = handler.connectDbCv();
        boolean esito = handler.insert(NUOVO_INDIRIZZO);
        handler.disconnect();
        return esito;
    }

    /**
     * Questo metodo verifica se le credenziali inserite corrispondono a quelle di un utente registrato.
     * @param username nome utente inserito
     * @param password password inserita
     * @return nome del centro vaccinale in cui si è registrato il cittadino, oppure NULL se le credenziali sono errate
     * @throws SQLException in caso di errore di connessione al database
     */
    public String login(String username, String password) throws SQLException {
        final String ESISTE =
                "SELECT centro_vaccinale " +
                "FROM " + SWVar.TAB_REGISTRAZIONE + " NATURAL JOIN " + SWVar.TAB_CITTADINI +
                " WHERE username = '" + username + "' AND password = '" + password + "';";

        DBHandler dbh = new DBHandler();
        dbh.connectDbCv();

        ResultSet rs = dbh.select(ESISTE);
        //System.out.println("ResultSet > " + rs.toString());

        String nome;
        if(rs.next()) nome = rs.getString(1);
        else nome = "";

        System.out.println("Nome centro vaccinale -> " + nome);
        dbh.disconnect();
        return nome;
    }

    public HashMap<String,float[]> getInfoCentroVaccinale(String nomeCentroVaccinale) throws SQLException {
        final String CALCOLA_MEDIE =
                "SELECT evento, COUNT(*), AVG(intensita)" +
                "FROM " + SWVar.TAB_EVENTIAVVERSI +
                "WHERE IDvacc IN (" +
                        "SELECT IDvacc " +
                        "FROM vaccinazione JOIN cittadino ON CfCitt=CF JOIN registrazione USING CF " +
                        "WHERE centro_vaccinale = '" + nomeCentroVaccinale + "'" +
                ") GROUP BY evento " +
                "ORDER BY evento ASC";

        HashMap<String,float[]> medieHM = new HashMap<>(EventoAvverso.QualeEvento.values().length);

        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs = handler.select(CALCOLA_MEDIE);
        float[] valori = new float[2];
        while(rs.next()) {
            valori[0] = rs.getFloat(2);
            valori[1] = rs.getFloat(3);
            medieHM.put(rs.getString(1),valori);
        }
        handler.disconnect();
        System.out.println("Ritorno hashmap " + medieHM.toString());

        return medieHM;
    }

    public boolean accessoCvRegistrato(String nome) throws SQLException {
        final String VERIFICA_CV_ESISTE =
                "SELECT nome " +
                "FROM " + SWVar.TAB_CENTRIVACCINALI +
                " WHERE nome = '" + nome + "'";

        DBHandler handler = new DBHandler();
        handler.connectDbCv();

        ResultSet rs = handler.select(VERIFICA_CV_ESISTE);
        if(!rs.next()) {
            System.out.println("Il centro vaccinale '" + nome + "' non è ancora registrato");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            DataManager.getInstance().elencoCentriVaccinali("");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
