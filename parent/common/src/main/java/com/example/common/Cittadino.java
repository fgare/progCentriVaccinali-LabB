//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.common;

import java.io.Serializable;

/**
 * Classe del package Data che implementa l'interfaccia Fop per memorizzare e gestire tutte le informazioni
 * riguardanti i vaccinati
 *
 * @author Giorgio Rossi
 * <p>
 * /@see Fop
 * /@see DataManager
 * /@see SWVar
 * /@see Vaccinazione
 * /@see CentroVaccinale
 * @since 12/12/2021
 */

public class Cittadino implements Serializable {
    private static final long serialVersionUID = 2L;
    private int id;
    private String nome;
    private String cognome;
    private String cf;
    private String email;
    private String psw;
    private String nomeCentroVaccinale;

    /**
     * Metodo costruttore di Vaccinato per lettura da file.
     * Alloca le variabili di Vaccinato compresi l'id e la vaccinazione.
     *
     * @param id      int: id del cittadino
     * @param nome    String: nome del cittadino
     * @param cognome String: cognome del cittadino
     * @param cf      String: codice fiscale del cittadino
     * @param email   String: email del cittadino
     * @param psw     String: password del cittadino
     * @param cv      CentroVaccinale: centro vaccinale dove il cittadino vuole vaccinarsi
     */
    public Cittadino(int id, String nome, String cognome, String cf, String email, String psw, String cv) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.email = email;
        this.psw = psw;
        this.nomeCentroVaccinale = cv;
    }

    /**
     * Metodo costruttore di Vaccinato per creare nuovi cittadini
     * senza vaccinazione.
     * Inizializza l'id del cittadino
     * Alloca le variabili di Vaccinato.
     *
     * @param nome    String: nome del cittadino
     * @param cognome String: cognome del cittadino
     * @param cf      String: codice fiscale del cittadino
     * @param email   String: email del cittadino
     * @param psw     String: password del cittadino
     * @param cv      CentroVaccinale: centro vaccinale dove il cittadino vuole vaccinarsi
     * @see CentroVaccinale
     */
    public Cittadino(String nome, String cognome, String cf, String email, String psw, String cv) {
        this(-1,nome,cognome,cf,email,psw,cv);
    }

    public Cittadino() {
        this(-1,null, null, null, null, null, null);
    }

    /**
     * Metodo getId di Vaccinato: restituisce l'id del cittadino.
     *
     * @return int: id del cittadino
     */
    public int getId() {
        return this.id;
    }

    public void setId(int index) {
        this.id = index;
    }

    /**
     * Metodo getNome di Vaccinato: restituisce il nome del cittadino.
     *
     * @return String: nome del cittadino
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo setNome di Vaccinato: setta il nome del cittadino.
     *
     * @param n String: nome del cittadino
     */
    public void setNome(String n) {
        this.nome = n;
    }

    /**
     * Metodo getCognome di Vaccinato: restituisce il cognome del cittadino.
     *
     * @return String: cognome del cittadino
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo setCognome di Vaccinato: setta il cognome del cittadino.
     *
     * @param cn String: nome del cittadino
     */
    public void setCognome(String cn) {
        this.cognome = cn;
    }

    /**
     * Metodo getCf di Vaccinato: restituisce il codice fiscale del cittadino.
     *
     * @return String: codice fiscale del cittadino
     */
    public String getCf() {
        return cf;
    }

    /**
     * Metodo setCf di Vaccinato: setta il codice fiscale del cittadino.
     *
     * @param cf String: nome del cittadino
     */
    public void setCf(String cf) {
        this.cf = cf;
    }

    /**
     * Metodo getEmail di Vaccinato: restituisce l'email del cittadino.
     *
     * @return String: email del cittadino
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo setEmail di Vaccinato: setta l'email del cittadino.
     * @param e String: email del cittadino
     */
    public void setEmail(String e) {
        this.email = e;
    }

    /**
     * Metodo getPsw di Vaccinato: restituisce la password del cittadino.
     *
     * @return String: password del cittadino
     */
    public String getPsw() {
        return psw;
    }

    /**
     * Metodo setPsw di Vaccinato: setta la password del cittadino.
     *
     * @param p String: password del cittadino
     */
    public void setPsw(String p) {
        this.psw = p;
    }

    /**
     * Metodo getCentroVaccinale di Vaccinato: restituisce il centro vaccinale dove il cittadino si è registrato.
     *
     * @return CentroVaccinale: oggetto di tipo CentroVaccinale
     * @see CentroVaccinale
     */
    public String getCentroVaccinale() {
        return this.nomeCentroVaccinale;
    }

    @Override
    public String toString() {
        return "Vaccinato {ID = " + id + " nome= '" + nome + " " + cognome + "', Centro vaccinale= '" + nomeCentroVaccinale + "'}";
    }

}