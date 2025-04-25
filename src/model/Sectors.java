package model;

import java.time.LocalDate;
import java.util.Date;

public class Sectors {
    int id;
    int id_escola;
    int numSector;
    String nom;
    String coordenades;
    String aproximacio;
    int numVies;
    String popularitat;
    String restriccio;

    public Sectors(int id, int id_escola, int numSector, String nom, String coordenades, String aproximacio, int numVies, String popularitat, String restriccio) {
        this.id = id;
        this.id_escola = id_escola;
        this.numSector = numSector;
        this.nom = nom;
        this.coordenades = coordenades;
        this.aproximacio = aproximacio;
        this.numVies = numVies;
        this.popularitat = popularitat;
        this.restriccio = restriccio;
    }

    public int getId() {
        return id;
    }

    public int getId_escola() {
        return id_escola;
    }

    public int getNumSector() {
        return numSector;
    }

    public String getNom() {
        return nom;
    }

    public String getCoordenades() {
        return coordenades;
    }

    public String getAproximacio() {
        return aproximacio;
    }

    public int getNumVies() {
        return numVies;
    }

    public String getPopularitat() {
        return popularitat;
    }

    public String getRestriccio() {
        return restriccio;
    }
}
