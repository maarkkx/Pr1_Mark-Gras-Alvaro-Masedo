package dao.SQLite;
import dao.DBConnection;
import model.CRUD;
import model.Vies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            System.out.println("S'ha afegit correctament la via");

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    @Override
    public void actualitzar(Vies vies) {
        Scanner scan = new Scanner(System.in);
        String sql = "UPDATE vies SET sector_id = ?, ancoratge_id = ?, tipus_roca_id = ?, escalador_id = ?, via_num = ?, nom = ?, orientacio = ?, estat = ?, tipus= ?, llargada = ? WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID de la via que vols actualitzar: ");
            int via_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1, via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) > 0){
                stmt.setInt(1,vies.getIdSector());
                stmt.setInt(2,vies.getIdAncoratge());
                stmt.setInt(3,vies.getIdTipusRocas());
                stmt.setInt(4,vies.getIdEscalador());
                stmt.setInt(5,vies.getNumVia());
                stmt.setString(6,vies.getNom());
                stmt.setString(7,vies.getOrientacio());
                stmt.setString(8, vies.getEstat());
                stmt.setString(9, vies.getTipus());
                stmt.setInt(10, vies.getLlargada());
                stmt.setInt(11, via_id);
                stmt.executeUpdate();
                System.out.println("S'ha actualitzat la via correctament");
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al modificar dades a la base de dades");
        }
    }

    @Override
    public void eliminar(Vies vies) {
        Scanner scan = new Scanner(System.in);
        String sql = "DELETE FROM vies WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID de la via que vols eliminar: ");
            int via_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1, via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){
                stmt.setInt(1, via_id);
                stmt.executeUpdate();
                System.out.println("Via eliminada correctament.");
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar dades a la base de dades");
        }
    }

    @Override
    public void llegir() {
        Scanner scan = new Scanner(System.in);
        String sql = "SELECT * FROM vies WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID de la via que vols comprovar: ");
            int via_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1,via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){
                stmt.setInt(1,via_id);
                ResultSet rs2 = stmt.executeQuery();
                System.out.printf("Sector ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %s Orientació: %-5s Estat: %-10s Tipus: %-2s Llargada: %-3d\n",
                        rs2.getInt("sector_id"),
                        rs2.getInt("escalador_id"),
                        rs2.getInt("via_num"),
                        rs2.getString("nom"),
                        rs2.getString("orientacio"),
                        rs2.getString("estat"),
                        rs2.getString("tipus"),
                        rs2.getInt("llargada"));
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }

    public void llegirTot() {
        String sql = "SELECT * FROM vies";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.printf("Sector ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %-30s Orientació: %-5s Estat: %-18s Tipus: %-2s Llargada: %-3d\n",
                        rs.getInt("sector_id"),
                        rs.getInt("escalador_id"),
                        rs.getInt("via_num"),
                        rs.getString("nom"),
                        rs.getString("orientacio"),
                        rs.getString("estat"),
                        rs.getString("tipus"),
                        rs.getInt("llargada"));
            }
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }
}
