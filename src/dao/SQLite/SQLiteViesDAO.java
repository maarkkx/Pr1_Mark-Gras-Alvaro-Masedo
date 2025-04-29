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
            scan.nextLine();

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
            scan.nextLine();

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
            scan.nextLine();
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

    public void totesViesEscola() {
        Connection con = DBConnection.openCon();
        try {
            Scanner scan = new Scanner(System.in);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vies WHERE sector_id = (SELECT sector_id FROM sectors WHERE escola_id = (SELECT escola_id FROM escoles WHERE nom = ?)) AND estat = \"Apta\";");

            SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
            dao.llegirTot();
            System.out.println("Escriu el nom de la escola: ");
            String nom = scan.nextLine();
            ps.setString(1, nom);

            ResultSet rs = ps.executeQuery();
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
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void viesRangDificultat() {
        Scanner scan = new Scanner(System.in);
        Connection con = DBConnection.openCon();
        String nivell = "";
        System.out.println("Escull la primera dificultat del rang, primer un numero del 4 al 9: ");
        int niv = scan.nextInt();
        scan.nextLine();
        while (niv < 4 && niv > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv = scan.nextInt();
            scan.nextLine();
        }

        if (niv == 4) {
            nivell += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell += scan.nextLine();
        } else if (niv == 5) {
            nivell += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell += scan.nextLine();
        } else if (niv == 6) {
            nivell += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 7) {
            nivell += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 8) {
            nivell += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 9) {
            nivell += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        }

        String nivell2 = "";
        System.out.println("Escull la segona dificultat del rang, primer un numero del 4 al 9: ");
        int niv2 = scan.nextInt();
        scan.nextLine();
        while (niv2 < 4 && niv2 > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv2 = scan.nextInt();
            scan.nextLine();
        }

        if (niv2 == 4) {
            nivell2 += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell2 += scan.nextLine();
        } else if (niv2 == 5) {
            nivell2 += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell2 += scan.nextLine();
        } else if (niv2 == 6) {
            nivell2 += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 7) {
            nivell2 += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 8) {
            nivell2 += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 9) {
            nivell2 += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        }
        try {
            PreparedStatement ps = con.prepareStatement("SELECT v.nom AS via_nom, d.nom AS grau, s.nom AS sector_nom, e.nom AS escola_nom FROM vies v JOIN trams t ON v.via_id = t.via_id JOIN dificultats d ON t.dificultat_id = d.dificultat_id JOIN sectors s ON v.sector_id = s.sector_id JOIN escoles e ON s.escola_id = e.escola_id WHERE d.nom BETWEEN ? AND ? ORDER BY d.nom;");
            ps.setString(1, nivell);
            ps.setString(2, nivell2);
            ResultSet rs = ps.executeQuery();
            System.out.printf("%-30s %-8s %-20s %-20s\n", "Nom Via", "Grau", "Sector", "Escola");
            while (rs.next()) {
                System.out.printf("%-30s %-8s %-25s %-25s\n",
                        rs.getString("via_nom"),
                        rs.getString("grau"),
                        rs.getString("sector_nom"),
                        rs.getString("escola_nom"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}
