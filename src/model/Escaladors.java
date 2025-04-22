package model;

public class Escaladors {
    int id;
    String nom;
    String alies;
    int edat;
    String nivell;
    String nomVia;
    String estil;

    public Escaladors(int id, String nom, String alies, int edat, String nivell, String nomVia, String estil) {
        this.id = id;
        this.nom = nom;
        this.alies = alies;
        this.edat = edat;
        this.nivell = nivell;
        this.nomVia = nomVia;
        this.estil = estil;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAlies() {
        return alies;
    }

    public int getEdat() {
        return edat;
    }

    public String getNivell() {
        return nivell;
    }

    public String getNomVia() {
        return nomVia;
    }

    public String getEstil() {
        return estil;
    }
}
