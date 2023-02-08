package com.example.common;

import java.io.Serializable;

/**
 *
 * Record che implementa Serilizable: memorizza le informazioni riguardante l'indirizzo del centro vaccinale
 * @param id int: identificativo dell'indirizzo del centro vaccinale
 * @param identificatore Identificatore: qualificatore dell'indirizzo del centro vaccinale
 * @param localizzazione String: nominativo dell'indirizzo del centro vaccinale
 * @param numCivico short: numero civico del centro vaccinale
 * @param comune String: comune del centro vaccinale
 * @param provincia String: provincia del centro vaccinale
 * @param ZIP String: CAP del centro vaccinale
 *
 * @see Identificatore
 * @see Serializable
 */
public record Indirizzo(int id, Identificatore identificatore, String localizzazione, short numCivico, String comune, String provincia, String ZIP) implements Serializable {
    private static final long serialVersionUID = 4L;

    /**
     * Tipo enumerativo che memorizza le tipologie di qualificatore.
     * Può assumere tre valori:
     * VIA|VIALE|PIAZZA
     *
     */
    public enum Identificatore implements Serializable {
        VIA, VIALE, PIAZZA;

        /**
         * @param i String: tipologia di qualificatore
         * @return VIA|VIALE|PIAZZA
         */
        public static Identificatore parse(String i) {
            i = i.toUpperCase();
            if(i.equals("VIA")) return Identificatore.VIA;
            else if(i.equals("VIALE")) return Identificatore.VIALE;
            else return Identificatore.PIAZZA;
        }

        /**
         * Metodo toString di Identificatore
         * @return String: VIA|VIALE|PIAZZA
         */
        public String toString() {
            switch(this) {
                case VIA:
                    return "Via";
                case VIALE:
                    return "Viale";
                case PIAZZA:
                    return "Piazza";
                default:
                    return "";
            }
        }
    }

    /**
     * Metodo costruttore di Indirizzo
     * @param ident String: qualificatore dell'indirizzo del centro vaccinale
     * @param localizzazione String: nominativo dell'indirizzo del centro vaccinale
     * @param numCivico short: numero civico del centro vaccinale
     * @param comune String: comune del centro vaccinale
     * @param provincia String: provincia del centro vaccinale
     * @param ZIP String: CAP del centro vaccinale
     *
     * @see Identificatore
     * @see Indirizzo
     */
    public Indirizzo(String ident, String localizzazione, short numCivico, String comune, String provincia, String ZIP) {
        this(0,Identificatore.parse(ident),localizzazione,numCivico,comune,provincia,ZIP);
    }
    /**
     * Metodo costruttore di Indirizzo
     * @param ident Identificatore: qualificatore dell'indirizzo del centro vaccinale
     * @param localizzazione String: nominativo dell'indirizzo del centro vaccinale
     * @param numCivico short: numero civico del centro vaccinale
     * @param comune String: comune del centro vaccinale
     * @param provincia String: provincia del centro vaccinale
     * @param ZIP String: CAP del centro vaccinale
     *
     * @see Identificatore
     * @see Indirizzo
     */
    public Indirizzo(Identificatore ident, String localizzazione, short numCivico, String comune, String provincia, String ZIP) {
        this(0,ident,localizzazione,numCivico,comune,provincia,ZIP);
    }

    /**
     * Metodo toString classe Indirizzo
     *
     * @return identificatore.toString() + " " +
     *                 localizzazione + " " +
     *                 numCivico + ", " +
     *                 comune + " (" + provincia + ") " +
     *                 ZIP;
     */
    public String toString() {
        return identificatore.toString() + " " +
                localizzazione + " " +
                numCivico + ", " +
                comune + " (" + provincia + ") " +
                ZIP;
    }

}
