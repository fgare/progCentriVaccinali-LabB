package com.example.common;

import java.io.Serializable;

public record Indirizzo(int id, Identificatore identificatore, String localizzazione, short numCivico, String comune, String provincia, String ZIP) implements Serializable {
    private static final long serialVersionUID = 4L;

    public enum Identificatore implements Serializable {
        VIA, VIALE, PIAZZA;

        public static Identificatore parse(String i) {
            i = i.toUpperCase();
            if(i.equals("VIA")) return Identificatore.VIA;
            else if(i.equals("VIALE")) return Identificatore.VIALE;
            else return Identificatore.PIAZZA;
        }

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

    public Indirizzo(String ident, String localizzazione, short numCivico, String comune, String provincia, String ZIP) {
        this(0,Identificatore.parse(ident),localizzazione,numCivico,comune,provincia,ZIP);
    }

    public String toString() {
        return identificatore.toString() + " " +
                localizzazione + " " +
                numCivico + ", " +
                comune + " (" + provincia + ") " +
                ZIP;
    }

}
