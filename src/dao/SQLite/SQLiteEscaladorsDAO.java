package dao.SQLite;

import dao.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class SQLiteEscaladorsDAO implements CRUD<Escaladors> {

    @Override
    public void crear(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO escaladors(nom, alies, edat, nivell, nom_via, estil) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, escalador.getNom());
            ps.setString(2, escalador.getAlies());
            ps.setInt(3, escalador.getEdat());
            ps.setString(4, escalador.getNivell());
            ps.setString(5, escalador.getNomVia());
            ps.setString(6, escalador.getEstil());

            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }

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
