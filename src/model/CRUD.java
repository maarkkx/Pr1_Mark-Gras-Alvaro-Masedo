package model;

public interface CRUD<Registre> {
    void crear(Registre obj);

    void actualitzar(Registre obj);

    void eliminar(Registre obj);

    void llegir();
}
