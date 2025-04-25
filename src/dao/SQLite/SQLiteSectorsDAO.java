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
            stmt.setDate(8, new java.sql.Date(sectors.getRestriccio().getTime()));

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
                stmt.setDate(8, new java.sql.Date(sectors.getRestriccio().getTime()));
                stmt.setInt(9,sec_id);
                stmt.executeUpdate();
                System.out.println("S'ha actualitzat el sector correctament");
            } else {
                System.out.println("El sector no existeix, prova amb un altre id de sector");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
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
            System.out.println("Error al afegir dades a la base de dades");
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

                System.out.println("sector_id: " + rs.getInt("string_id"));
                System.out.println("escola_id: " + rs.getInt("escola_id"));
                System.out.println("sector_num: " + rs.getInt("sector_num"));
                System.out.println("nom: " + rs.getString("nom"));
                System.out.println("coordenades: " + rs.getString("coordenades"));
                System.out.println("aproximació: " + rs.getString("aproximacio"));
                System.out.println("vies_qt: " + rs.getInt("vies_qt"));
                System.out.println("popularitat: " + rs.getString("popularitat"));
                System.out.println("restricció: " + rs.getDate("restricio"));
            } else {
                System.out.println("El sector no existeix, prova amb un altre id de sector");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    public void llegirTot() {
        String sql = "SELECT * FROM sectors";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.printf("ID: %-3d escola_id: %-20s sector_num: %-15s Nom: %-3d Coordenades: %-4s Aproximacio: %-25s vies_qt: %s Popularitat: %-3d Restricció: %-3d\n");
                        rs.getInt("string_id");
                        rs.getInt("escola_id");
                        rs.getInt("sector_num");
                        rs.getString("nom");
                        rs.getString("coordenades");
                        rs.getString("aproximacio");
                        rs.getInt("vies_qt");
                        rs.getString("popularitat");
                        rs.getDate("restricio");
            }
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }
}
