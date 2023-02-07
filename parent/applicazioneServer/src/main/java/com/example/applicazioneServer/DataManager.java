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
 * @author Luca Canali
 * @author Federico Garegnani
 */

public class DataManager {

    /**
     * Variabile statica che rappresenta l'istanza unica della classe DataManager.
      *Utilizzata per garantire che vi sia una sola istanza della classe.
     */
    private static DataManager instance = null;

    /**
     * Costruttore privato della classe DataManager.
     * Il costruttore istanzia un oggetto di tipo DBHandler e richiama il metodo initDB().
     *
     * @throws SQLException in caso di errore durante la creazione del database
     */
    private DataManager() throws SQLException {
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
     * Utilizza una istanza di DBHandler per ottenere una connessione al database e
     * prepara le query necessarie per l'inserimento dei dati del centro vaccinale e
     * dei suoi dettagli di indirizzo.
     *
     * @param c Centro Vaccinale
     * @return boolean
     * @throws SQLException
     */
    public boolean registraCentroVaccinale(CentroVaccinale c) throws SQLException {
        final String NUOVO_CV = "INSERT INTO ? VALUES (?,?);";
        final String NUOVO_INDIRIZZO = "INSERT INTO ? VALUES (?,?,?,?,?,?);";
        System.out.println(c);
        DBHandler handler = new DBHandler();
        Connection dbConn = handler.getConnection();

        //prepara query per inserimento nuovo centro vaccinale
        PreparedStatement aggiungiCV = dbConn.prepareStatement(NUOVO_CV);
        aggiungiCV.setString(1,SWVar.TAB_CENTRIVACCINALI);
        aggiungiCV.setString(2,c.getNome());
        aggiungiCV.setString(3,c.getTipologia().name());

        //query per inserimento indirizzo
        PreparedStatement aggiungiIndirizzo = dbConn.prepareStatement(NUOVO_INDIRIZZO);
        Indirizzo i = c.getIndirizzo();
        aggiungiIndirizzo.setString(1,SWVar.TAB_INDIRIZZI);
        aggiungiIndirizzo.setString(2,c.getNome()); //nome del centro vaccinale
        aggiungiIndirizzo.setString(3,i.identificatore().name());
        aggiungiIndirizzo.setString(4,i.nome());
        aggiungiIndirizzo.setShort(5,i.numCivico());
        aggiungiIndirizzo.setString(6,i.provincia());
        aggiungiIndirizzo.setString(7,i.ZIP());

        return handler.insert(aggiungiCV,aggiungiIndirizzo);
    }

    /**
     * Questo metodo registraCittadino serve per registrare un oggetto Cittadino nel database.
     * Crea una connessione al database con l'aiuto di DBHandler e prepara una query per inserire
     * un nuovo cittadino con i dati forniti dall'oggetto Cittadino.
     *
     * @param c Cittadino
     * @return boolean
     * @throws SQLException
     */
    public boolean registraCittadino(Cittadino c) throws SQLException {
        final String NUOVO_CITTADINO = "INSERT INTO ? VALUES (?,?,?,?,?,?);";

        DBHandler handler = new DBHandler();
        Connection dbConn = handler.getConnection();

        PreparedStatement aggiungiPersona = dbConn.prepareStatement(NUOVO_CITTADINO);
        aggiungiPersona.setString(1,SWVar.TAB_CITTADINI);
        aggiungiPersona.setString(2,c.getNome());
        aggiungiPersona.setString(3,c.getCognome());
        aggiungiPersona.setString(4, c.getCf());
        aggiungiPersona.setString(5,c.getEmail());
        aggiungiPersona.setString(6,c.getPsw());
        aggiungiPersona.setString(7,c.getCentroVaccinale());

        return handler.insert(aggiungiPersona);
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
    public boolean registraVaccinazione(CentroVaccinale c, Vaccinazione v) throws SQLException {
        final String VERIFICA_CITTADINO_REGISTRATO = "SELECT cf, centrovaccinale FROM ? WHERE cf=? AND centrovaccinale=?;";
        final String NUOVO_VACCINAZIONE = "INSERT INTO ? VALUES (?,?,?);";
        //TODO: controllare query + nomi tabelle

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement verificaCittadino = conn.prepareStatement(VERIFICA_CITTADINO_REGISTRATO);
        verificaCittadino.setString(1,SWVar.TAB_CITTADINI);
        verificaCittadino.setString(2,"'" + v.getCodPrenotazione() + "'");
        verificaCittadino.setString(3,"'" + c.getNome() + "'");

        ResultSet rs = handler.select(verificaCittadino);

        //Bisognerebbe controllare che il cv esista, ma lo è per forza perchè il cittadino si è precedentemente registrato

        if(DBHandler.resultSetSize(rs) == 0) return false; //termina segnalando che l'operazione non è riuscita

        //riapro la connessione che viene chiusa nella chiamata al metodo select
        handler = new DBHandler();
        conn = handler.getConnection();

        PreparedStatement nuovaVaccinazione = conn.prepareStatement(NUOVO_VACCINAZIONE);
        nuovaVaccinazione.setString(1,SWVar.TAB_VACCINAZIONI);
        nuovaVaccinazione.setString(2,v.getCodPrenotazione());
        LocalDate dataVac = v.getData();
        nuovaVaccinazione.setDate(3,new Date(dataVac.getYear(),dataVac.getMonthValue(),dataVac.getDayOfMonth()));
        nuovaVaccinazione.setString(3,v.getVaccino().name());

        return handler.insert(nuovaVaccinazione);
    }

    /**
     * Questo codice registra un evento avverso in un database.
     * Utilizza la classe DBHandler per gestire la connessione al database
     * e l'esecuzione delle query.
     *
     * @param ea Evento Avverso
     * @return boolean
     * @throws SQLException
     */
    public boolean registraEventoAvverso(EventoAvverso ea) throws SQLException {
        final String NUOVO_EVENTOAVVERSO = "INSERT INTO ? VALUES (?,?,?,?);";

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement nuovoEvento = conn.prepareStatement(NUOVO_EVENTOAVVERSO);
        nuovoEvento.setString(1,SWVar.TAB_EVENTIAVVERSI);
        nuovoEvento.setInt(2,ea.getIdVaccinazione());
        nuovoEvento.setString(3,ea.getQualeEvento().name());
        nuovoEvento.setByte(4,ea.getSeverita());
        nuovoEvento.setString(5,ea.getNota());

        return handler.insert(nuovoEvento);
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
        final String GET_ELENCO_CV = "SELECT * FROM ? JOIN ? ON ? WHERE ? LIKE %?%;";

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement chiediLista = conn.prepareStatement(GET_ELENCO_CV);
        chiediLista.setString(1,SWVar.TAB_CENTRIVACCINALI);
        chiediLista.setString(2,SWVar.TAB_INDIRIZZI);
        chiediLista.setString(3,"nome = nomecentro");
        chiediLista.setString(4,"nome");
        chiediLista.setString(5,searchString);

        ResultSet rs = handler.select(chiediLista);

        List<CentroVaccinale> lsCV = new ArrayList<>(DBHandler.resultSetSize(rs));
        while(rs.next()) {
            //costruisco prima l'indirizzo
            Indirizzo i = new Indirizzo(
                    rs.getInt("id"),
                    Indirizzo.Identificatore.parse(rs.getString("identificatore")),
                    rs.getString("nome"),
                    rs.getShort("civico"),
                    rs.getString("citta"),
                    rs.getString("provincia"),
                    rs.getString("zip")
            );
            //costruisco l'oggetto centro vaccinale e lo aggiungo alla lista
            lsCV.add(
                    new CentroVaccinale(
                        rs.getString("nome"),
                        i,
                        CentroVaccinale.Tipologia.parse(rs.getString("tipologia"))
                    )
            );
        }
        //TODO: controllare i nomi delle colonne
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
    public List<CentroVaccinale> elencoCentriVaccinaliPerComune(String comune, String tipologia) throws SQLException {
        final String GET_ELENCO_TIPOLOGIA = "SELECT * FROM ? JOIN ? ON ? WHERE ? LIKE %?% AND ?";

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement chiediLista = conn.prepareStatement(GET_ELENCO_TIPOLOGIA);
        chiediLista.setString(1,SWVar.TAB_CENTRIVACCINALI);
        chiediLista.setString(2,SWVar.TAB_INDIRIZZI);
        chiediLista.setString(3,"nome = nomecentro");
        chiediLista.setString(4,"comune");
        chiediLista.setString(5, comune);
        chiediLista.setString(6, "tipologia = " + tipologia);


        ResultSet rs = handler.select(chiediLista);

        List<CentroVaccinale> lsCV = new ArrayList<>(DBHandler.resultSetSize(rs));
        while(rs.next()) {
            //costruisco prima l'indirizzo
            Indirizzo i = new Indirizzo(
                    rs.getInt("id"),
                    Indirizzo.Identificatore.parse(rs.getString("identificatore")),
                    rs.getString("nome"),
                    rs.getShort("civico"),
                    rs.getString("citta"),
                    rs.getString("provincia"),
                    rs.getString("zip")
            );

            //costruisco l'oggetto centro vaccinale e lo aggiungo alla lista
            lsCV.add(
                    new CentroVaccinale(
                            rs.getString("nome"),
                            i,
                            CentroVaccinale.Tipologia.parse(rs.getString("tipologia"))
                    )
            );
        }
        //TODO: controllare i nomi delle colonne
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
        final String NUOVO_INDIRIZZO = "INSERT INTO ? VALUES (?,?,?,?,?,?,?);";

        DBHandler handler = new DBHandler();
        Connection dbConn = handler.getConnection();

        PreparedStatement aggiungiIndirizzo = dbConn.prepareStatement(NUOVO_INDIRIZZO);
        aggiungiIndirizzo.setString(1, SWVar.TAB_INDIRIZZI);
        aggiungiIndirizzo.setInt(2, indirizzo.id());
        aggiungiIndirizzo.setString(3, indirizzo.identificatore().toString());
        aggiungiIndirizzo.setString(4, indirizzo.nome());
        aggiungiIndirizzo.setInt(5, indirizzo.numCivico());
        aggiungiIndirizzo.setString(6, indirizzo.comune());
        aggiungiIndirizzo.setString(7, indirizzo.provincia());

        return handler.insert(aggiungiIndirizzo);
    }
}
