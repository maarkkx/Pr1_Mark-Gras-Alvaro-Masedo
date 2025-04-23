package model;

public class Vies {
    int id;
    int idSector;
    int idAncoratge;
    int idTipusRocas;
    int idEscalador;
    int numVia;
    String nom;
    String orientacio;
    String estat;
    String tipus;
    int llargada;

    public Vies(int id, int idSector, int idAncoratge, int idTipusRocas, int idEscalador, int numVia, String nom, String orientacio, String estat, String tipus, int llargada) {
        this.id = id;
        this.idSector = idSector;
        this.idAncoratge = idAncoratge;
        this.idTipusRocas = idTipusRocas;
        this.idEscalador = idEscalador;
        this.numVia = numVia;
        this.nom = nom;
        this.orientacio = orientacio;
        this.estat = estat;
        this.tipus = tipus;
        this.llargada = llargada;
    }

    public int getId() {
        return id;
    }

    public int getIdSector() {
        return idSector;
    }

    public int getIdAncoratge() {
        return idAncoratge;
    }

    public int getIdTipusRocas() {
        return idTipusRocas;
    }

    public int getIdEscalador() {
        return idEscalador;
    }

    public int getNumVia() {
        return numVia;
    }

    public String getNom() {
        return nom;
    }

    public String getOrientacio() {
        return orientacio;
    }

    public String getEstat() {
        return estat;
    }

    public String getTipus() {
        return tipus;
    }

    public int getLlargada(){
        return llargada;
    }
}
