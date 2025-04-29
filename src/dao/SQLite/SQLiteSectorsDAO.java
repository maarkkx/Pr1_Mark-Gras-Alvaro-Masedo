package dao.SQLite;
import dao.DBConnection;
import model.CRUD;
import model.Sectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
            stmt.setString(8,sectors.getRestriccio());

            stmt.executeUpdate();
            System.out.println("S'ha afegit correctament el sector");
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    @Override
    public void actualitzar(Sectors sectors) {
        Scanner scan = new Scanner(System.in);
        String sql = "UPDATE sectors SET escola_id = ?, sector_num = ?, nom = ?, coordenades = ?, aproximacio = ?, vies_qt = ?, popularitat = ?, restriccio = ? WHERE sector_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID del sector que vols actualitzar: ");
            int sec_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM sectors WHERE sector_id = ?");
            check.setInt(1,sec_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) > 0){
                stmt.setInt(1,sectors.getId_escola());
                stmt.setInt(2,sectors.getNumSector());
                stmt.setString(3,sectors.getNom());
                stmt.setString(4,sectors.getCoordenades());
                stmt.setString(5,sectors.getAproximacio());
                stmt.setInt(6,sectors.getNumVies());
                stmt.setString(7,sectors.getPopularitat());
                stmt.setString(8, sectors.getRestriccio());
                stmt.setInt(9,sec_id);
                stmt.executeUpdate();
                System.out.println("S'ha actualitzat el sector correctament");
            } else {
                System.out.println("El sector no existeix, prova amb un altre id de sector");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al modificar dades a la base de dades");
        }
    }

    @Override
    public void eliminar(Sectors sectors) {
        Scanner scan = new Scanner(System.in);
        String sql = "DELETE FROM sectors WHERE sector_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID del sector que vols eliminar: ");
            int sec_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM sectors WHERE sector_id = ?");
            check.setInt(1,sec_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){
                stmt.setInt(1,sec_id);
                stmt.executeUpdate();
                System.out.println("Sector eliminat correctament.");
            } else {
                System.out.println("El sector no existeix, prova amb un altre id de sector");
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
        String sql = "SELECT * FROM sectors WHERE sector_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            System.out.println("Escriu la ID del sector que vols comprovar: ");
            int sec_id = scan.nextInt();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM sectors WHERE sector_id = ?");
            check.setInt(1,sec_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){
                stmt.setInt(1,sec_id);
                ResultSet rs2 = stmt.executeQuery();
                System.out.printf("Sector ID: %-5d Escola ID: %-5d Sector Num: %-5d Nom: %-20s Coordenades: %-20s Aproximacio: %-20s Vies Qt: %-5d Popularitat: %-6s Restriccio: %-10s\n",
                        rs2.getInt("sector_id"),
                        rs2.getInt("escola_id"),
                        rs2.getInt("sector_num"),
                        rs2.getString("nom"),
                        rs2.getString("coordenades"),
                        rs2.getString("aproximacio"),
                        rs2.getInt("vies_qt"),
                        rs2.getString("popularitat"),
                        rs2.getString("restriccio")
                );
            } else {
                System.out.println("El sector no existeix, prova amb un altre id de sector");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }

    public void llegirTot() {
        String sql = "SELECT * FROM sectors";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.printf("Sector ID: %-5d Escola ID: %-5d Sector Num: %-5d Nom: %-20s Coordenades: %-20s Aproximacio: %-45s Vies Qt: %-5d Popularitat: %-6s Restriccio: %-10s\n",
                        rs.getInt("sector_id"),
                        rs.getInt("escola_id"),
                        rs.getInt("sector_num"),
                        rs.getString("nom"),
                        rs.getString("coordenades"),
                        rs.getString("aproximacio"),
                        rs.getInt("vies_qt"),
                        rs.getString("popularitat"),
                        rs.getString("restriccio")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }
}
