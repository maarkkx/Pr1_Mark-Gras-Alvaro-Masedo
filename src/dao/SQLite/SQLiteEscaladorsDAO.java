package dao.SQLite;

import dao.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLiteEscaladorsDAO implements CRUD<Escaladors> {

    @Override
    public void crear(Escaladors escalador) {
        String sql = "INSERT INTO escaladors (id_escalador, nom, alies, edat, nivell, nom_via, estil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = DBConnection.openCon();
    }


    @Override
    public void actualitzar(Escaladors escalador) {

    }

    @Override
    public void eliminar(Escaladors escalador) {

    }

    @Override
    public void llegir() {

    }
}
