//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.common;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe che implementa l'interfaccia Serializable per memorizzare e gestire tutte le informazioni
 * riguardanti le vaccinazioni.
 *
 * @see Cittadino
 * @see Vaccino
 * @see EventoAvverso
 * @see Serializable
 */

public class Vaccinazione implements Serializable {
    private static final long serialVersionUID = 5L;
    private int id;
    private LocalDate data;
    private Vaccino v;
    private String cfCitt;

    /**
     * Tipo enumerativo che memorizza le tipologie di vaccino.
     * Può assumere quattro valori:
     * PFIZER, MODERNA, ASTRAZENECA,JJ
     *
     */
    public enum Vaccino implements Serializable {
        PFIZER, MODERNA, ASTRAZENECA, JJ;

        public String toString() {
            switch(this) {
                case PFIZER:
                    return "Pfizer";
                case MODERNA:
                    return "Moderna";
                case ASTRAZENECA:
                    return "AstraZeneca";
                case JJ:
                    return "Johnson & Johnson";
                default:
                    return "";
            }
        }

        /**
         * @param s String: tipologia di vaccino
         * @return PFIZER|MODERNA|ASTRAZENECA|JJ;
         */
        public static Vaccino parse(String s) {
            if(s.equals("PFIZER")) return Vaccino.PFIZER;
            if(s.equals("MODERNA")) return Vaccino.MODERNA;
            if(s.equals("ASTRAZENECA")) return Vaccino.ASTRAZENECA;
            return Vaccino.JJ;
        }
    }

    /**
     * Metodo costruttore di Vaccinazione per la lettura da file:
     * assegna la data della vaccinazione, l'id e crea un nuovo oggetto EventoAvverso.
     *
     * @param id   int: id della vaccinazione
     * @param vac  Vaccino: nome del vaccino somministrato
     * @param data LocalDate: data della vaccinazione
     */
    public Vaccinazione(int id, Vaccino vac, LocalDate data, String cf) {
        this.id = id;
        this.cfCitt = cf;
        this.data = data;
        this.v = vac;
    }

    public Vaccinazione(Vaccino vac, LocalDate data, String cod) {
        this(0,vac,data,cod);
    }

    /**
     * Metodo getId di Vaccinazione: restituisce l'id della vaccinazione.
     *
     * @return int: id della vaccinazione
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo setId di Vaccinazione: setta l'id della Vaccinazione.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo getData di Vaccinazione: restituisce la data della vaccinazione.
     *
     * @return LocalDate: data della vaccinazione
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Metodo setData di Vaccinazione: setta la data della Vaccinazione.
     *
     * @param s String: data passata come stringa
     */
    public void setData(String s) {
        String[] arr = s.split("-|/"); //TODO: controllare regex
        this.data = LocalDate.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
    }

    /**
     * Metodo getVaccino di Vaccinazione: restituisce il nome del vaccino somministrato.
     *
     * @return String: nome del vaccino somministrato
     * @see Vaccino
     */
    public Vaccino getVaccino() {
        return v;
    }

    public void setVaccino(Vaccino vaccino) {
        v = vaccino;
    }

    /**
     * Metodo getCfCitt di Vaccinato: restituisce il codice fiscale del cittadino.
     *
     * @return Cittadino: oggetto di tipo Cittadino
     * @see Cittadino
     */
    public String getCfCitt() {
        return this.cfCitt;
    }
}
