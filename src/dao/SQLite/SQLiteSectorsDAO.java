package dao.SQLite;
import dao.DBConnection;
import model.CRUD;
import model.Sectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteSectorsDAO implements CRUD<Sectors> {

    @Override
    public void crear(Sectors sectors) {
        String sql = "INSERT INTO sectors (escola_id, sector_num, nom, coordenades, aproximacio, vies_qt, popularitat, restriccio) VALUES (?,?,?,?,?,?,?,?)";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1,sectors.getId_escola());
            stmt.setInt(2,sectors.getNumSector());
            stmt.setString(3,sectors.getNom());
            stmt.setString(4,sectors.getCoordenades());
            stmt.setString(5,sectors.getAproximacio());
            stmt.setInt(6,sectors.getNumVies());
            stmt.setString(7,sectors.getPopularitat());
            stmt.setDate(8, new java.sql.Date(sectors.getRestriccio().getTime()));

            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    @Override
    public void actualitzar(Sectors sectors) {

    }

    @Override
    public void eliminar(Sectors sectors) {

    }

    @Override
    public void llegir() {

    }
}
