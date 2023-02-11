//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.applicazioneServer;
import com.example.applicazioneServer.File.DBHandler;
import com.example.common.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

        boolean esito = handler.insert(NUOVO_CV);

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

    //TODO: da finire
    public boolean registraVaccinazione(CentroVaccinale c, Vaccinazione v) throws SQLException {
        final String VERIFICA_CITTADINO_REGISTRATO = "SELECT cf, centrovaccinale FROM "+ SWVar.TAB_CITTADINI +
                " WHERE .cf= " + v.getCfCitt() + " AND centrovaccinale= " + c.getNome();
        final String NUOVA_VACCINAZIONE = "INSERT INTO " + SWVar.TAB_VACCINAZIONI + "(data, vaccino, CF_citt) " +
                " VALUES('" +
                v.getData() + "','" +
                v.getVaccino() + "','" +
                v.getCfCitt() + "');";
        //TODO: controllare query + nomi tabelle

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement verificaCittadino = conn.prepareStatement(VERIFICA_CITTADINO_REGISTRATO);
        verificaCittadino.setString(1,SWVar.TAB_CITTADINI);
        verificaCittadino.setString(2,"'" + v.getCfCitt() + "'");
        verificaCittadino.setString(3,"'" + c.getNome() + "'");

        ResultSet rs = handler.select(VERIFICA_CITTADINO_REGISTRATO);

        //Bisognerebbe controllare che il cv esista, ma lo è per forza perchè il cittadino si è precedentemente registrato

        if(DBHandler.resultSetSize(rs) == 0) return false; //termina segnalando che l'operazione non è riuscita

        //riapro la connessione che viene chiusa nella chiamata al metodo select

        PreparedStatement nuovaVaccinazione = conn.prepareStatement(NUOVA_VACCINAZIONE);
        nuovaVaccinazione.setString(1,SWVar.TAB_VACCINAZIONI);
        nuovaVaccinazione.setString(2,v.getCfCitt());
        LocalDate dataVac = v.getData();
        nuovaVaccinazione.setDate(3,new Date(dataVac.getYear(),dataVac.getMonthValue(),dataVac.getDayOfMonth()));
        nuovaVaccinazione.setString(3,v.getVaccino().name());

        boolean esito = handler.insert(NUOVA_VACCINAZIONE);
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
    public boolean registraEventoAvverso(EventoAvverso ea) throws SQLException {
        final String NUOVO_EVENTOAVVERSO = "INSERT INTO " + SWVar.TAB_EVENTIAVVERSI +
                " VALUES('" +
                ea.getIdVaccinazione() + "','" +
                ea.getQualeEvento() + "','" +
                ea.getSeverita() + "','" +
                ea.getNota() + "');";

        DBHandler handler = new DBHandler();
        Connection conn = handler.connectDbCv();
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
        System.out.println("Inizializzo arraylist lscv");
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
            System.out.println("Indirizzo letto = " + i.toString());
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
                "SELECT * FROM " + SWVar.TAB_CENTRIVACCINALI + " JOIN  " + SWVar.TAB_INDIRIZZI +
                        " ON " + SWVar.TAB_CENTRIVACCINALI + ".nome = " + SWVar.TAB_INDIRIZZI + ".centro_vaccinale" +
                        " WHERE " + SWVar.TAB_INDIRIZZI + ".comune = '" + comune + "' AND " +
                        SWVar.TAB_CENTRIVACCINALI + ".tipologia = '" + tipologia + "'";

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement chiediLista = conn.prepareStatement(GET_ELENCO_TIPOLOGIA);

        ResultSet rs = handler.select(GET_ELENCO_TIPOLOGIA);

        List<CentroVaccinale> lsCV = new ArrayList<>(DBHandler.resultSetSize(rs));
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
     * @return true se le credenziali sono valide, false altrimenti
     * @throws SQLException in caso di errore di connessione al database
     */
    public boolean login(String username, String password) throws SQLException {
        final String ESISTE =
                "SELECT 1 FROM " + SWVar.TAB_CITTADINI +
                        " WHERE username = '" + username + "' AND password = '" + password + "'";
        DBHandler dbh = new DBHandler();
        Connection conn = dbh.connectDbCv();
        ResultSet result = dbh.select(ESISTE);
        boolean esito = result.next();
        dbh.disconnect();
        return esito;
    }

    public List<EventoAvverso> calcolaMedia() throws SQLException {
        final String GET_ELENCO_EVENTI =
                ("SELECT evento, AVG(intensita) AS media_intensita FROM " + SWVar.TAB_EVENTIAVVERSI + " GROUP BY evento");

        List<EventoAvverso> listaEventiAvversi = new ArrayList<>();

        DBHandler handler = new DBHandler();
        Connection conn = handler.connectDbCv();

        try (ResultSet result = handler.select(GET_ELENCO_EVENTI)) {
            while (result.next()) {
                int id = result.getInt("id");
                String evento = result.getString("evento");
                int mediaInt = result.getByte("media_intensita");
                int id_vacc = result.getInt("ID_vaccino");
                String note = result.getString("note");
                //listaEventiAvversi.add(new EventoAvverso(id, id_vacc, evento, mediaInt, note));
            }
        }

        handler.disconnect();

        return listaEventiAvversi;
    }

    public static void main(String[] args) {
        try {
            DataManager.getInstance().elencoCentriVaccinali("");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
