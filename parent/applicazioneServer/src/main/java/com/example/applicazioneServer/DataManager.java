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
     * Utilizza una istanza di DBHandler per ottenere una connessione al database e
     * prepara le query necessarie per l'inserimento dei dati del centro vaccinale e
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

        return new DBHandler().insert(NUOVO_INDIRIZZO);
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
        final String NUOVO_CITTADINO =
                "INSERT INTO " + SWVar.TAB_CITTADINI + " VALUES('" + c.getNome() + "','" +
                        c.getCognome() + "','" +
                        c.getCf() + "','" +
                        c.getEmail() + "','" +
                        c.getPsw() + "','" +
                        c.getCentroVaccinale() + "');";

        return new DBHandler().insert(NUOVO_CITTADINO);
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
                " WHERE cf= " + "//TODO" + " AND centrovaccinale= " + c.getNome();
        final String NUOVO_VACCINAZIONE = "INSERT INTO " + SWVar.TAB_VACCINAZIONI + "(data, vaccino, CF_citt) " +
                " VALUES('" +
                v.getData() + "','" +
                v.getVaccino() + "','" +
                v.getCodPrenotazione() + "');";
        //TODO: controllare query + nomi tabelle

        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();

        PreparedStatement verificaCittadino = conn.prepareStatement(VERIFICA_CITTADINO_REGISTRATO);
        verificaCittadino.setString(1,SWVar.TAB_CITTADINI);
        verificaCittadino.setString(2,"'" + v.getCodPrenotazione() + "'");
        verificaCittadino.setString(3,"'" + c.getNome() + "'");

        ResultSet rs = new DBHandler().select(verificaCittadino);

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

        return handler.insert(VERIFICA_CITTADINO_REGISTRATO);
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
        final String NUOVO_EVENTOAVVERSO = "INSERT INTO " + SWVar.TAB_EVENTIAVVERSI +
                " VALUES('" +
                ea.getIdVaccinazione() + "','" +
                ea.getQualeEvento() + "','" +
                ea.getSeverita() + "','" +
                ea.getNota() + "');";

        return new DBHandler().insert(NUOVO_EVENTOAVVERSO);
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
                "SELECT * FROM " + SWVar.TAB_CENTRIVACCINALI + " JOIN  " + SWVar.TAB_INDIRIZZI + " ON " + SWVar.TAB_CENTRIVACCINALI + ".nome = " + SWVar.TAB_INDIRIZZI + ".centro_vaccinale " +
                        "WHERE " + SWVar.TAB_CENTRIVACCINALI + ".nome LIKE '%" + searchString + "%'";


        DBHandler handler = new DBHandler();
        Connection conn = handler.getConnection();
        PreparedStatement chiediLista = conn.prepareStatement(GET_ELENCO_CV);

        ResultSet rs = new DBHandler().select(chiediLista.toString());

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
        final String NUOVO_INDIRIZZO = "INSERT INTO " + SWVar.TAB_INDIRIZZI + "(identificatore, localizzazione, civico, provincia, centro_vaccinale) " +
                " VALUES('" +
                indirizzo.identificatore() + "','" +
                indirizzo.localizzazione() + "','" +
                indirizzo.numCivico() + "','" +
                indirizzo.comune() + "','" +
                indirizzo.provincia() + "');";

        return new DBHandler().insert(NUOVO_INDIRIZZO);
    }
}
