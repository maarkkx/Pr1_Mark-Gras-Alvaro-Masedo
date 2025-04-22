package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Escoles {
    int id;
    int idpob;
    String nom;
    String aproximacio;
    int numVies;
    String popularitat;
    LocalDate restriccio;

    public Escoles(int id, int idpob, String nom, String aproximacio, int numVies, String popularitat, LocalDate restriccio) {
        this.id = id;
        this.idpob = idpob;
        this.nom = nom;
        this.aproximacio = aproximacio;
        this.numVies = numVies;
        this.popularitat = popularitat;
        this.restriccio = restriccio;
    }

    public int getId() {
        return id;
    }

    public int getIdpob() {
        return idpob;
    }

    public String getNom() {
        return nom;
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

    public LocalDate getRestriccio() {
        return restriccio;
    }
}
