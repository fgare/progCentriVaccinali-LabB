//Rossi Giorgio 746571 VA
//Garegnani Federico 746789 VA
//Canali Luca 744802 VA
//Invernizzi Daniele 746484 VA
//Callegari Pietro 746568 VA
package com.example.common;

import java.io.Serializable;

/**
 * Classe del package Data che implementa l'interfaccia Fop per memorizzare e gestire tutte le informazioni
 * riguardanti i centri vaccinali.
 *
 * @author Giorgio Rossi
 * <p>
 * /@see Fop
 * /@see DataManager
 * /@see SWVar
 * /@see Tipologia
 * @since 12/12/2021
 */

public class CentroVaccinale implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private Indirizzo indirizzo;
    private Tipologia tipologia;

    public enum Tipologia implements Serializable {
        OSPEDALE, AZIENDA, HUB;

        public String toString() {
            switch(this) {
                case OSPEDALE:
                    return "Ospedale";
                case AZIENDA:
                    return "Azienda";
                case HUB:
                    return "Hub";
                default:
                    return "";
            }
        }

        public static Tipologia parse(String s) {
            if(s.equals("OSPEDALIERO")) return Tipologia.OSPEDALE;
            if(s.equals("AZIENDALE")) return Tipologia.AZIENDA;
            return Tipologia.HUB;
        }
    }

    /**
     * Metodo costruttore di CentroVaccinale per lettura da file.
     * Alloca le variabili di CentroVaccinale compresa la lista di EventoAvverso.
     * <p>
     * /@param nome String: nome del centro vaccinale
     * /@param indirizzo String: indirizzo del centro vaccinale
     * /@param tipologia Tipologia: tipologia del centro vaccinale
     * /@param l ArrayList<EventoAvverso>: Lista di EventoAvverso associata alle vaccinazioni eseguite nel centro
     * vaccinale
     *
     * @see Tipologia
     * @see EventoAvverso
     */
    public CentroVaccinale(String nome, Indirizzo indirizzo, Tipologia tipologia) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.tipologia = tipologia;
    }

    /**
     * Metodo costruttore di CentroVaccinale vuoto.
     */
    public CentroVaccinale(String nome) {
        this.nome = nome;
        indirizzo = null;
        tipologia = null;
    }

    /**
     * Metodo getNome di CentroVaccinale: restituisce il nome del centro vaccinale.
     *
     * @return String: nome del centro vaccinale
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo setNome di CentroVaccinale: setta il nome del centro vaccinale.
     * /@param nome String: nome del centro vaccinale
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo getIndirizzo di CentroVaccinale: restituisce l'indirizzo del centro vaccinale.
     *
     * @return String: indirizzo del centro vaccinale
     */
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    /**
     * Metodo setIndirizzo di CentroVaccinale: setta l'indirizzo del centro vaccinale.
     * /@param indirizzo String: Indirizzo del centro vaccinale
     */
    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Metodo getTipologia di CentroVaccinale: restituisce la tipologia del centro vaccinale.
     *
     * @return Tipologia: tipologia del centro vaccinale
     * @see Tipologia
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * Metodo setTipologia di CentroVaccinale: setta la tipologia del centro vaccinale.
     * //@param tipologia Tipologia: nome del centro vaccinale
     *
     * @see Tipologia
     */
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * Metodo toString di CentroVaccinale: implementazione del metodo toString di {@link Object Object}
     * esegiuta da CentroVaccinale.
     *
     * @return String: "CentroVaccinale {" + "nome ='" + nome + '\'' + ", indirizzo='" + indirizzo +
     * '\'' + ", tipologia='" + tipologia.toString() + '\'' + '}';
     */
    @Override
    public String toString() {
        return "CentroVaccinale {" + "nome ='" + nome + '\'' + ", indirizzo='" + indirizzo +
                '\'' + ", tipologia='" + tipologia.toString() + '\'' + '}';
    }
}