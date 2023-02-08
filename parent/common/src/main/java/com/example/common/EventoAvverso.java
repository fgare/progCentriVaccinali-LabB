//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.common;

import java.io.Serializable;

/**
 * Classe che implementa l'interfaccia Serializable per memorizzare e gestire tutte le informazioni
 * riguardanti gli eventi avversi relativi al vaccino.
 *
 */


public class EventoAvverso implements Serializable {
    private static final long serialVersionUID = 3L;
    private int id;
    private int idVaccinazione;
    private QualeEvento evento;
    private byte severita;
    private String nota;
    public enum QualeEvento implements Serializable {
        MALDITESTA, FEBBRE, DOLORIMUSCOLARIARTICOLARI, LINFOADENOPATIA, TACHICARDIA, CRISIIPERTENSIVA;
    }

    /**
     * Metodo costruttore di EventoAvverso per lettura da file.
     * Alloca le variabili di EventoAvverso.
     *
     * @param id       int: id associato ad un evento avverso
     * @param severita int[]: array contenente le severità di ogni tipologia di evento avverso
     * @param nota     String[]: array contenente le note di ogni tipologia di evento avverso
     */
    public EventoAvverso(int id, int idV, QualeEvento e, byte severita, String nota) throws IllegalArgumentException {
        //lancia un'eccezione per segnalare che il valore inserito non rispetta i vincoli
        if(severita<0 || severita>5) throw new IllegalArgumentException("Severita deve essere compreso tra 0 e 5");
        this.id = id;
        idVaccinazione = idV;
        evento = e;
        this.severita = severita;
        this.nota = nota;
    }

    public EventoAvverso(int idV, QualeEvento e, byte severita, String nota) {
        this(-1,idV,e,severita,nota);
    }

    public QualeEvento getQualeEvento() {
        return evento;
    }

    public byte getSeverita() {
        return severita;
    }

    public void setSeverita(byte val) {
        this.severita = val;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String val) {
        nota = val;
    }

    /**
     * Metodo getId di EventoAvverso: restituisce l'id di un evento avverso
     *
     * @return int: id dell'evento avverso
     */
    public int getId() {
        return this.id;
    }

    /**
     * Metodo setId di EventoAvverso: setta l'id di un evento avverso
     *
     * @param id int: id dell'evento avverso
     */
    public void setId(int id) {
        this.id = id;
    }
    public int getIdVaccinazione() {
        return idVaccinazione;
    }

    public void setIdVaccinazione(int n) {
        idVaccinazione = n;
    }

}