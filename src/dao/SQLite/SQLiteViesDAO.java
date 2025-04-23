package dao.SQLite;
import dao.DBConnection;
import model.CRUD;
import model.Vies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLiteViesDAO implements CRUD<Vies> {

    @Override
    public void crear(Vies vies) {
        String sql = "INSERT INTO vies (sector_id, ancoratge_id, tipus_roca_id, escalador_id, via_num, nom, orientacio, estat, tipus, llargada) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1,vies.getIdSector());
            stmt.setInt(2,vies.getIdAncoratge());
            stmt.setInt(3,vies.getIdTipusRocas());
            stmt.setInt(4,vies.getIdEscalador());
            stmt.setInt(5,vies.getNumVia());
            stmt.setString(6,vies.getNom());
            stmt.setString(7,vies.getOrientacio());
            stmt.setString(8,vies.getEstat());
            stmt.setString(9,vies.getTipus());
            stmt.setInt(10,vies.getLlargada());

            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    @Override
    public void actualitzar(Vies vies) {

    }

    @Override
    public void eliminar(Vies vies) {

    }

    @Override
    public void llegir() {

    }
}
